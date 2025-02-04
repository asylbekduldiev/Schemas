package com.myproject.dao;

import com.myproject.models.School;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchoolDAO {
    private Connection connection;

    public SchoolDAO(Connection connection) {
        this.connection = connection;
    }

    // Метод для получения всех школ
    public List<School> getAllSchools() throws SQLException {
        List<School> schools = new ArrayList<>();
        String sql = "SELECT * FROM schools";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                School school = new School();
                school.setId(rs.getInt("id"));
                school.setName(rs.getString("name"));
                schools.add(school);
            }
        }
        return schools;
    }

    // Метод для добавления новой школы
    public void addSchool(School school) throws SQLException {
        String sql = "INSERT INTO schools (name) VALUES (?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, school.getName());
            pstmt.executeUpdate();
        }
    }
}