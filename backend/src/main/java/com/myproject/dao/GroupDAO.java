package com.myproject.dao;

import com.myproject.models.Group;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO {
    private Connection connection;

    public GroupDAO(Connection connection) {
        this.connection = connection;
    }

    // Метод для получения всех групп
    public List<Group> getAllGroups() throws SQLException {
        List<Group> groups = new ArrayList<>();
        String sql = "SELECT * FROM groups";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Group group = new Group();
                group.setId(rs.getInt("id"));
                group.setName(rs.getString("name"));
                group.setSchoolId(rs.getInt("school_id"));
                groups.add(group);
            }
        }
        return groups;
    }

    // Метод для добавления новой группы
    public void addGroup(Group group) throws SQLException {
        String sql = "INSERT INTO groups (name, school_id) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, group.getName());
            pstmt.setInt(2, group.getSchoolId());
            pstmt.executeUpdate();
        }
    }
}