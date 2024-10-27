package com.senai.transportadora.util;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class HttpResponseUtil {
    public void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");

        if (statusCode == 204) {
            exchange.sendResponseHeaders(statusCode, -1);
            exchange.getResponseBody().close();
            return;
        }

        byte[] responseBytes = response.getBytes();
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
