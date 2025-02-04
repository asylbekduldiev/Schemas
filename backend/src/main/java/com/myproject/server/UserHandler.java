package com.myproject.server;

import com.myproject.dao.UserDAO;
import com.myproject.models.User;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

public class UserHandler implements HttpHandler {
    private UserDAO userDAO;
    private Gson gson;

    public UserHandler(UserDAO userDAO) {
        this.userDAO = userDAO;
        this.gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                // Получение всех пользователей
                List<User> users = userDAO.getAllUsers();
                String response = gson.toJson(users);

                // Отправка ответа
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

            } catch (SQLException e) {
                e.printStackTrace();
                exchange.sendResponseHeaders(500, 0);
                exchange.getResponseBody().close();
            }
        } else if ("POST".equals(exchange.getRequestMethod())) {
            // Обработка добавления пользователя (пока не реализовано)
            exchange.sendResponseHeaders(501, 0); // 501 - Not Implemented
            exchange.getResponseBody().close();
        } else {
            exchange.sendResponseHeaders(405, 0); // 405 - Method Not Allowed
            exchange.getResponseBody().close();
        }
    }
}