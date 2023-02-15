package jdbc;

import javax.sql.DataSource;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

@Getter
@Setter
public class CustomDataSource implements DataSource {
    private static volatile CustomDataSource instance;
    private final String driver;
    private final String url;
    private final String name;
    private final String password;


    private CustomDataSource(String driver, String url, String password, String name) {

        this.driver = driver;
        this.name = name;
        this.password = password;
        this.url = url;
    }

    public static CustomDataSource getInstance() {
        if (instance == null){
            if (instance == null){
                try{
                    Properties properties = new Properties();
                    properties.load(CustomDataSource.class.getClassLoader().getResourceAsStream("app.properties"));
                    instance = new CustomDataSource(
                            properties.getProperty("postgres.driver"),
                            properties.getProperty("postgres.url"),
                            properties.getProperty("postgres.password"),
                            properties.getProperty("postgres.name")
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return instance;
    }
    @SneakyThrows
    @Override
    public Connection getConnection() throws SQLException {
        return new CustomConnector().getConnection(url, name, password);
    }
    @SneakyThrows
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return new CustomConnector().getConnection(url, username, password);
    }
    @SneakyThrows
    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }
    @SneakyThrows
    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }
    @SneakyThrows
    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }
    @SneakyThrows
    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }
    @SneakyThrows
    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
    @SneakyThrows
    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }
    @SneakyThrows
    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

}
