package com.myproject.server;

import com.myproject.dao.UserDAO;
import com.myproject.dao.GroupDAO;
import com.myproject.dao.SchoolDAO;
import com.myproject.DatabaseConnection;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            // Подключение к базе данных
            Connection connection = DatabaseConnection.getConnection();

            // Создание DAO
            UserDAO userDAO = new UserDAO(connection);
            GroupDAO groupDAO = new GroupDAO(connection);
            SchoolDAO schoolDAO = new SchoolDAO(connection);

            // Создание HTTP-сервера на порту 8080
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            // Регистрация обработчиков
            server.createContext("/api/users", new UserHandler(userDAO));
            server.createContext("/api/groups", new GroupHandler(groupDAO));
            server.createContext("/api/schools", new SchoolHandler(schoolDAO));

            // Запуск сервера
            server.setExecutor(null);
            server.start();
            System.out.println("Server started on port 8080");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}