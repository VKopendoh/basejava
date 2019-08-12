package com.vkopendoh.webapp.sql;

import com.vkopendoh.webapp.Config;
import com.vkopendoh.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private ConnectionFactory connectionFactory;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;


    public SqlHelper() {
        this.dbUrl = Config.get().getDbUrl();
        this.dbUser = Config.get().getDbUser();
        this.dbPassword = Config.get().getDbPassword();
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T execute(String sql, SqlExecute sqlExecute) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            T returnValue = (T) sqlExecute.execute(ps);
            return returnValue;

        } catch (
                SQLException e) {
            throw new StorageException(e);
        }
    }
}
