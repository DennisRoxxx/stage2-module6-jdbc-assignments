package jdbc;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CustomConnector {
    @SneakyThrows
    public Connection getConnection(String url) throws SQLException {
        return DriverManager.getConnection(url);
    }
    @SneakyThrows

    public Connection getConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
