package com.myproject.server;

import com.myproject.dao.SchoolDAO;
import com.myproject.models.School;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

public class SchoolHandler implements HttpHandler {
    private SchoolDAO schoolDAO;
    private Gson gson;

    public SchoolHandler(SchoolDAO schoolDAO) {
        this.schoolDAO = schoolDAO;
        this.gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                // Получение всех школ
                List<School> schools = schoolDAO.getAllSchools();
                String response = gson.toJson(schools);

                // Отправка ответа
                exchange.sendResponseHeaders(200, response.getBytes().length);
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