package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.NotExistStorageException;
import com.vkopendoh.webapp.exception.StorageException;
import com.vkopendoh.webapp.model.ContactType;
import com.vkopendoh.webapp.model.Resume;
import com.vkopendoh.webapp.sql.ConnectionFactory;
import com.vkopendoh.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
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
                    setResumeToDb("INSERT INTO resume (full_name, uuid) VALUES (?,?)", conn, resume);
                    setContactsToDb("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?) ", conn, resume);
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
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
        Resume oldResume = this.get(resume.getUuid());
        sqlHelper.transactionalExecute(conn -> {
                    setResumeToDb("UPDATE resume SET full_name =? WHERE uuid =?", conn, resume);
                    setContactsToDb("" +
                                    "    INSERT INTO contact (resume_uuid, type, value) " +
                                    "        VALUES (?,?,?) " +
                                    "   ON CONFLICT (resume_uuid, type)" +
                                    " DO UPDATE SET value = ?;", conn, resume);
                    Set<String> existedContacts = new HashSet<>();
                    Set<String> contactsToDelete = new HashSet<>();
                    resume.getContacts().entrySet().forEach(e -> existedContacts.add(e.getKey().name()));
                    oldResume.getContacts().entrySet().forEach(e -> contactsToDelete.add(e.getKey().name()));
                    contactsToDelete.removeAll(existedContacts);
                    for (String contact : contactsToDelete) {
                        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE type =?")) {
                            ps.setString(1, contact);
                            ps.execute();
                        }

                    }
                    return null;
                }
        );
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = new ArrayList<>();
        return sqlHelper.execute("SELECT * FROM resume r " +
                        "                 LEFT JOIN contact c " +
                        "                        ON r.uuid = c.resume_uuid " +
                        "                  ORDER BY full_name,uuid",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        String fullName = rs.getString("full_name");
                        Resume resume = new Resume(uuid, fullName);
                        if (rs.getString("type") != null) {
                            do {
                                String value = rs.getString("value");
                                ContactType type = ContactType.valueOf(rs.getString("type"));
                                resume.addContact(type, value);
                            } while (rs.next() && uuid.equals(rs.getString("uuid")));
                            rs.previous();
                        }
                        sortedList.add(resume);
                    }
                    return sortedList;
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

    private void setResumeToDb(String sql, Connection conn, Resume resume) throws SQLException, StorageException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
        }
    }

    private void setContactsToDb(String sql, Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                if (ps.getParameterMetaData().getParameterCount() > 3) {
                    ps.setString(4, e.getValue());
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
