package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.NotExistStorageException;
import com.vkopendoh.webapp.model.*;
import com.vkopendoh.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (full_name, uuid) VALUES (?,?)")) {
                        ps.setString(1, resume.getFullName());
                        ps.setString(2, resume.getUuid());
                        ps.execute();
                    }
                    setContactsToDb(conn, resume);
                    setSectionsToDb(conn, resume);
                    return null;
                }
        );
    }


    @Override
    public Resume get(String uuid) {
        Resume resume = sqlHelper.execute("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    if (rs.getString("type") != null) {
                        do {
                            ContactType type = ContactType.valueOf(rs.getString("type"));
                            String value = rs.getString("value");
                            r.addContact(type, value);
                        } while (rs.next());
                    }
                    return r;
                });

        return sqlHelper.execute("" +
                "    SELECT * FROM resume r " +
                " LEFT JOIN section s " +
                "        ON r.uuid = s.resume_uuid " +
                "     WHERE r.uuid =? ", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString("type") != null) {
                    SectionType type = SectionType.valueOf(rs.getString("type"));
                    resume.addSection(type, getSection(type, rs));
                }
            }
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name =? WHERE uuid =?")) {
                        ps.setString(1, resume.getFullName());
                        ps.setString(2, resume.getUuid());
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(resume.getUuid());
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid =?")) {
                        ps.setString(1, resume.getUuid());
                        ps.execute();
                    }
                    try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid =?")) {
                        ps.setString(1, resume.getUuid());
                        ps.execute();
                    }
                    setContactsToDb(conn, resume);
                    setSectionsToDb(conn, resume);
                    return null;
                }
        );
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> sortedMap = new LinkedHashMap<>();
        sqlHelper.execute("SELECT * FROM resume r ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                sortedMap.put(uuid, new Resume(uuid, rs.getString("full_name")));
            }
            return null;
        });

        sqlHelper.execute("SELECT * FROM contact c", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sortedMap.compute(rs.getString("resume_uuid"), (s, resume) -> {
                    try {
                        resume.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return resume;
                });
            }
            return null;
        });

        return sqlHelper.execute("SELECT * FROM section c", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sortedMap.compute(rs.getString("resume_uuid"), (s, resume) -> {
                    try {
                        SectionType type = SectionType.valueOf(rs.getString("type"));
                        resume.addSection(type, getSection(type, rs));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return resume;
                });
            }
            return new ArrayList<>(sortedMap.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT (*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return 0;
            }
            return rs.getInt(1);
        });
    }

    private void setContactsToDb(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?) ")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void setSectionsToDb(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid,type,content) VALUES (?,?,?) ")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                SectionType type = e.getKey();
                ps.setString(2, type.name());
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        TextSection textSection = (TextSection) e.getValue();
                        ps.setString(3, textSection.getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        TextListSection textListSection = (TextListSection) e.getValue();
                        StringBuilder fullContent = new StringBuilder();
                        for (String textLine : textListSection.getContent()) {
                            fullContent.append(textLine + "\n");
                        }
                        ps.setString(3, fullContent.toString());
                        break;
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private Section getSection(SectionType type, ResultSet rs) throws SQLException {
        Section section = null;
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                section = new TextSection(rs.getString("content"));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                String[] strings = rs.getString("content").split("\n");
                section = new TextListSection(Arrays.asList(strings));
                break;
        }
        return section;
    }
}
