package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.ExistStorageException;
import com.vkopendoh.webapp.exception.NotExistStorageException;
import com.vkopendoh.webapp.model.ContactType;
import com.vkopendoh.webapp.model.Resume;
import com.vkopendoh.webapp.sql.ConnectionFactory;
import com.vkopendoh.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;
    private static final int SAVE = 1;
    private static final int UPDATE = 0;

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
        sqlHelper.transactionalExecute(conn -> setDataToDB(conn, "INSERT INTO resume (uuid, full_name) VALUES (?,?) ON CONFLICT DO NOTHING",
                "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)",
                resume, SAVE, 1, 2, 1, 2, 3));
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                "SELECT * FROM resume r " +
                "  LEFT JOIN contact c " +
                "    ON r.uuid = c.resume_uuid " +
                " WHERE r.uuid =? ", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            do {
                String value = rs.getString("value");
                ContactType type = ContactType.valueOf(rs.getString("type"));
                r.addContact(type, value);
            } while (rs.next());
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
        sqlHelper.transactionalExecute(conn -> setDataToDB(conn, "UPDATE resume SET full_name =? WHERE uuid =?",
                "UPDATE contact SET value =? WHERE resume_uuid =? AND type =?",
                resume, UPDATE, 2, 1, 2, 3, 1));
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = new ArrayList<>();
        return sqlHelper.execute("SELECT * FROM resume r left join contact c on r.uuid = c.resume_uuid ORDER BY full_name,uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String fullName = rs.getString("full_name");
                Resume resume = new Resume(uuid, fullName);
                do {
                    String value = rs.getString("value");
                    ContactType type = ContactType.valueOf(rs.getString("type"));
                    resume.addContact(type, value);
                } while (rs.next() && uuid.equals(rs.getString("uuid")));
                rs.previous();
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

    private Object setDataToDB(Connection conn, String sql1, String sql2, Resume resume, int operation, int... indexes) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql1)) {
            ps.setString(indexes[0], resume.getUuid());
            ps.setString(indexes[1], resume.getFullName());
            if (ps.executeUpdate() == 0 && operation == SAVE) {
                throw new ExistStorageException(resume.getUuid());
            } else if (ps.executeUpdate() == 0 && operation == UPDATE) {
                throw new NotExistStorageException(resume.getUuid());
            }
        }
        try (PreparedStatement ps = conn.prepareStatement(sql2)) {
            for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                ps.setString(indexes[2], resume.getUuid());
                ps.setString(indexes[3], contact.getKey().name());
                ps.setString(indexes[4], contact.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
        return null;
    }
}
