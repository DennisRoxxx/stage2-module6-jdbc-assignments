package jdbc;

import lombok.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SimpleJDBCRepository {

    private Connection connection;
    private PreparedStatement ps = null;
    private Statement st = null;

    private static final String createUserSQL = "INSERT INTO myusers (firstname, lastname, age) VALUES (?, ?, ?)";
    private static final String updateUserSQL = "UPDATE myusers SET firstname = ?, lastname = ?, age = ? WHERE id = ?";
    private static final String deleteUser = "DELETE FROM myusers WHERE id = ?";
    private static final String findUserByIdSQL = "SELECT * FROM myusers WHERE id = ?";
    private static final String findUserByNameSQL = "SELECT * FROM myusers WHERE firstname = ?";
    private static final String findAllUserSQL = "SELECT * FROM myusers";
    @SneakyThrows
    public Long createUser(User user) throws SQLException {
        long id = 0;
        connection = CustomDataSource.getInstance().getConnection();
        ps = connection.prepareStatement(createUserSQL);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setInt(3, user.getAge());
        ps.execute();

        ResultSet resultSet = ps.getGeneratedKeys();
        if (resultSet.next()){
            id = resultSet.getLong(1);
        }
        return id;
    }
    @SneakyThrows
    public User findUserById(Long userId) throws SQLException {
        connection = CustomDataSource.getInstance().getConnection();
        ps = connection.prepareStatement(findUserByIdSQL);
        ps.setLong(1, userId);
        ResultSet resultSet = ps.executeQuery();

        User user = new User();
        if (resultSet.next()) {
            user.setId(resultSet.getLong("id"));
            user.setFirstName(resultSet.getString("firstname"));
            user.setLastName(resultSet.getString("lastname"));
            user.setAge(resultSet.getInt("age"));
        }
        return user;
    }
    @SneakyThrows
    public User findUserByName(String userName) throws SQLException {
        connection = CustomDataSource.getInstance().getConnection();
        ps = connection.prepareStatement(findUserByNameSQL);
        ps.setString(1, userName);
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("firstname"));
        user.setLastName(resultSet.getString("lastname"));
        user.setAge(resultSet.getInt("age"));
        return user;
    }
    @SneakyThrows
    public List<User> findAllUser() throws SQLException {
        List<User> list = new ArrayList<>();
        connection = CustomDataSource.getInstance().getConnection();
        st = connection.createStatement();
        ResultSet resultSet = st.executeQuery(findAllUserSQL);
        while (resultSet.next()) {
            User user = new User (resultSet.getLong(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4));
            list.add(user);
        }
        return list;
    }
    @SneakyThrows
    public User updateUser(User user) throws SQLException {
        connection = CustomDataSource.getInstance().getConnection();
        ps = connection.prepareStatement(updateUserSQL);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setInt(3, user.getAge());
        ps.setLong(4, user.getId());
        ps.executeUpdate();
        return user;
    }
    @SneakyThrows
    public void deleteUser(Long userId) throws SQLException {
        connection = CustomDataSource.getInstance().getConnection();
        ps = connection.prepareStatement(deleteUser);
        ps.setLong(1, userId);
        ps.executeUpdate();
    }
}
