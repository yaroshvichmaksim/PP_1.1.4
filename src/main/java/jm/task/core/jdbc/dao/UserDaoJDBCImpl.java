package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = null;

    public UserDaoJDBCImpl()
    {

    }

    public void createUsersTable() {
        try {
            connection = Util.getConnection();
            Statement statement = connection.createStatement();
            String query = "create table if not exists user(id integer not null primary key auto_increment, name varchar(30) not null, lastName varchar(30), age integer not null)";
            statement.executeUpdate(query);
            statement.close();
            Util.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        Statement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            String query = "drop table if exists user";
            statement.executeUpdate(query);
            statement.close();
            Util.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement statement = null;
        try {
            connection = Util.getConnection();
            String query = "insert into user(name, lastName, age) values(?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2,lastName);
            statement.setByte(3,age);
            statement.executeUpdate();
            statement.close();
            Util.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        PreparedStatement statement = null;
        try {
            connection = Util.getConnection();
            String query = "delete from user where id=?";
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            statement.close();
            Util.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Statement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            String query = "select * from user";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
            }
            statement.close();
            resultSet.close();
            Util.closeConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        Statement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            String query = "truncate user";
            statement.executeUpdate(query);
            statement.close();
            Util.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
