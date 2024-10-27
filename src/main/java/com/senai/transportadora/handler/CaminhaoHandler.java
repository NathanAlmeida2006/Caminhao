package com.senai.transportadora.handler;

import com.google.gson.Gson;
import com.senai.transportadora.controller.CaminhaoController;
import com.senai.transportadora.entity.Caminhao;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;

public class CaminhaoHandler implements HttpHandler {
    private final CaminhaoController controller;
    private final Gson gson;
    private final HttpResponseUtil responseUtil;

    public CaminhaoHandler(CaminhaoController controller, Gson gson, HttpResponseUtil responseUtil) {
        this.controller = controller;
        this.gson = gson;
        this.responseUtil = responseUtil;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (exchange.getRequestMethod().equals("OPTIONS")) {
            responseUtil.sendResponse(exchange, 204, "");
            return;
        }

        try {
            switch (exchange.getRequestMethod()) {
                case "GET" -> handleGet(exchange);
                case "POST" -> handlePost(exchange);
                case "PUT" -> handlePut(exchange);
                case "DELETE" -> handleDelete(exchange);
                default -> responseUtil.sendResponse(exchange, 405, "Method not allowed");
            }
        } catch (Exception e) {
            responseUtil.sendResponse(exchange, 500, "Server error: " + e.getMessage());
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        var caminhoes = controller.listarCaminhoes();
        responseUtil.sendResponse(exchange, 200, gson.toJson(caminhoes));
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        var caminhao = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Caminhao.class);
        controller.adicionarCaminhao(caminhao);
        responseUtil.sendResponse(exchange, 201, gson.toJson(caminhao));
    }

    private void handlePut(HttpExchange exchange) throws IOException {
        var path = exchange.getRequestURI().getPath();
        var id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
        var caminhao = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Caminhao.class);

        if (controller.atualizarCaminhao(id, caminhao)) {
            responseUtil.sendResponse(exchange, 200, gson.toJson(caminhao));
        } else {
            responseUtil.sendResponse(exchange, 404, "Caminhão não encontrado");
        }
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        var path = exchange.getRequestURI().getPath();
        var id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));

        if (controller.removerCaminhao(id)) {
            responseUtil.sendResponse(exchange, 204, "");
        } else {
            responseUtil.sendResponse(exchange, 404, "Caminhão não encontrado");
        }
    }
}
