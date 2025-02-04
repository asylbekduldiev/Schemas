package com.myproject.server;

import com.myproject.dao.GroupDAO;
import com.myproject.models.Group;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

public class GroupHandler implements HttpHandler {
    private GroupDAO groupDAO;
    private Gson gson;

    public GroupHandler(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
        this.gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                // Получение всех групп
                List<Group> groups = groupDAO.getAllGroups();
                String response = gson.toJson(groups);

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
        } else {
            exchange.sendResponseHeaders(405, 0); // 405 - Method Not Allowed
            exchange.getResponseBody().close();
        }
    }
}