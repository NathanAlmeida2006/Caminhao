package com.senai.transportadora.handler;

import com.google.gson.Gson;
import com.senai.transportadora.controller.CaminhaoController;
import com.senai.transportadora.entity.Caminhao;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Classe responsável por manipular as requisições HTTP relacionadas a Caminhões.
 * Implementa a interface RequestHandler e define os métodos para lidar com operações de CRUD.
 */
public class CaminhaoHandler implements RequestHandler {
    private final CaminhaoController controller;
    private final Gson gson;
    private final HttpResponseUtil responseUtil;

    /**
     * Construtor para inicializar o handler de caminhões com o controlador, utilitário de resposta e a instância do Gson.
     *
     * @param controller O controlador responsável pelas operações de caminhões.
     * @param gson A instância do Gson para serializar e desserializar JSON.
     * @param responseUtil O utilitário de resposta HTTP para facilitar o envio de respostas.
     */
    public CaminhaoHandler(CaminhaoController controller, Gson gson, HttpResponseUtil responseUtil) {
        this.controller = controller;
        this.gson = gson;
        this.responseUtil = responseUtil;
    }

    /**
     * Metodo para manipular requisições HTTP recebidas. Determina o metodo HTTP (GET, POST, PUT, DELETE)
     * e chama o metodo correspondente. Também configura os cabeçalhos CORS.
     *
     * @param exchange O objeto HttpExchange que encapsula a requisição e a resposta.
     * @throws IOException Se ocorrer um erro ao manipular a requisição.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        setupCorsHeaders(exchange);

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

    /**
     * Manipula requisições HTTP GET para listar todos os caminhões.
     *
     * @param exchange O objeto HttpExchange para manipular a resposta HTTP.
     * @throws IOException Se ocorrer um erro ao enviar a resposta.
     */
    private void handleGet(HttpExchange exchange) throws IOException {
        var caminhoes = controller.listarCaminhoes();
        responseUtil.sendResponse(exchange, 200, gson.toJson(caminhoes));
    }

    /**
     * Manipula requisições HTTP POST para adicionar um novo caminhão.
     *
     * @param exchange O objeto HttpExchange para manipular a resposta HTTP.
     * @throws IOException Se ocorrer um erro ao enviar a resposta.
     */
    private void handlePost(HttpExchange exchange) throws IOException {
        var caminhao = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Caminhao.class);
        controller.adicionarCaminhao(caminhao);
        responseUtil.sendResponse(exchange, 201, gson.toJson(caminhao));
    }

    /**
     * Manipula requisições HTTP PUT para atualizar um caminhão existente.
     *
     * @param exchange O objeto HttpExchange para manipular a resposta HTTP.
     * @throws IOException Se ocorrer um erro ao enviar a resposta.
     */
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

    /**
     * Manipula requisições HTTP DELETE para remover um caminhão pelo ID.
     *
     * @param exchange O objeto HttpExchange para manipular a resposta HTTP.
     * @throws IOException Se ocorrer um erro ao enviar a resposta.
     */
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
