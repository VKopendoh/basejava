package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.NotExistStorageException;
import com.vkopendoh.webapp.model.*;
import com.vkopendoh.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    resume.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
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
        return sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    sortedMap.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }

            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    sortedMap.get(uuid).addContact(ContactType.valueOf(rs.getString("type")),
                            rs.getString("value"));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    SectionType type = SectionType.valueOf(rs.getString("type"));
                    sortedMap.get(uuid).addSection(type,
                            getSection(type, rs));
                }
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
                        ps.setString(3, String.join("\n", textListSection.getContent()));
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
