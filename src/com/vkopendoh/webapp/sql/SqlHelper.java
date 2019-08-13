package com.vkopendoh.webapp.sql;

import com.vkopendoh.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String sql, SqlExecute sqlExecute) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            return (T) sqlExecute.execute(ps);

        } catch (
                SQLException e) {
            throw new StorageException(e);
        }
    }
}
