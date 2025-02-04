package com.myproject.dao;

import com.myproject.models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    // Метод для получения всех пользователей
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, age, group_id FROM users";
        System.out.println("Executing SQL: " + sql); // Логируем SQL-запрос
    
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
                user.setGroupId(rs.getInt("group_id"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage()); // Логируем ошибки
            throw e;
        }
    
        System.out.println("Retrieved users: " + users); // Логируем результат
        return users;
    }
    // Метод для добавления нового пользователя
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, age, group_id) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setInt(2, user.getAge());
            pstmt.setInt(3, user.getGroupId());
            pstmt.executeUpdate();
        }
    }
}