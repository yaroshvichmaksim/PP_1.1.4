package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Util.getConnection();
        UserService userDao = new UserServiceImpl();
        userDao.createUsersTable();
        userDao.saveUser("Anton", "Makarchuk", (byte) 22);
        userDao.saveUser("Maksim", "Yaroshevich", (byte) 24);
        userDao.saveUser("Sasha", "Shimanchuk", (byte) 32);
        userDao.saveUser("Yana", "Milevskaya", (byte) 42);
        List<User> users = userDao.getAllUsers();
        for (var user : users) {
            System.out.println(user.toString());
        }
        userDao.removeUserById(2);
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
        Util.closeSession();
    }
}
