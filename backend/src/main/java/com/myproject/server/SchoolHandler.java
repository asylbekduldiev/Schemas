package com.myproject.server;

import com.myproject.dao.SchoolDAO;
import com.myproject.models.School;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class SchoolHandler implements HttpHandler {
    private final SchoolDAO schoolDAO;
    private final Gson gson;

    public SchoolHandler(SchoolDAO schoolDAO) {
        this.schoolDAO = schoolDAO;
        this.gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            // Обработка GET-запроса (если понадобится)
        } else if ("POST".equals(exchange.getRequestMethod())) {
            try {
                // Чтение тела запроса
                InputStream requestBody = exchange.getRequestBody();
                String requestData = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);
                System.out.println("Received data: " + requestData); // Логируем полученные данные

                // Парсинг JSON
                School school = gson.fromJson(requestData, School.class);

                // Добавление школы
                schoolDAO.addSchool(school);

                // Отправка ответа
                String response = "School added successfully";
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }

            } catch (Exception e) {
                e.printStackTrace();
                exchange.sendResponseHeaders(500, 0); // 500 - Internal Server Error
                exchange.getResponseBody().close();
            }
        } else {
            exchange.sendResponseHeaders(405, 0); // 405 - Method Not Allowed
            exchange.getResponseBody().close();
        }
    }
}