/**
 * Classe responsável por manipular as requisições HTTP relacionadas a Funcionários.
 * Implementa a interface RequestHandler e define os métodos para lidar com operações de CRUD.
 */
package com.senai.transportadora.handler;

import com.google.gson.Gson;
import com.senai.transportadora.controller.FuncionarioController;
import com.senai.transportadora.entity.Funcionario;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStreamReader;

public class FuncionarioHandler implements RequestHandler {
    private final FuncionarioController controller;
    private final Gson gson;
    private final HttpResponseUtil responseUtil;

    /**
     * Construtor para inicializar o handler de funcionários com o controlador, utilitário de resposta e a instância do Gson.
     *
     * @param controller O controlador responsável pelas operações de funcionários.
     * @param gson A instância do Gson para serializar e desserializar JSON.
     * @param responseUtil O utilitário de resposta HTTP para facilitar o envio de respostas.
     */
    public FuncionarioHandler(FuncionarioController controller, Gson gson, HttpResponseUtil responseUtil) {
        this.controller = controller;
        this.gson = gson;
        this.responseUtil = responseUtil;
    }

    /**
     * Metodo para manipular requisições HTTP recebidas. Determina o me todo HTTP (GET, POST, PUT, DELETE)
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
                default -> responseUtil.sendResponse(exchange, 405, "Método não permitido");
            }
        } catch (Exception e) {
            responseUtil.sendResponse(exchange, 500, "Erro no servidor: " + e.getMessage());
        }
    }

    /**
     * Manipula requisições HTTP GET para listar todos os funcionários.
     *
     * @param exchange O objeto HttpExchange para manipular a resposta HTTP.
     * @throws IOException Se ocorrer um erro ao enviar a resposta.
     */
    private void handleGet(HttpExchange exchange) throws IOException {
        var funcionarios = controller.listarFuncionarios();
        responseUtil.sendResponse(exchange, 200, gson.toJson(funcionarios));
    }

    /**
     * Manipula requisições HTTP POST para adicionar um novo funcionário.
     *
     * @param exchange O objeto HttpExchange para manipular a resposta HTTP.
     * @throws IOException Se ocorrer um erro ao enviar a resposta.
     */
    private void handlePost(HttpExchange exchange) throws IOException {
        var funcionario = gson.fromJson(
                new InputStreamReader(exchange.getRequestBody()),
                Funcionario.class
        );
        controller.adicionarFuncionario(funcionario);
        responseUtil.sendResponse(exchange, 201, gson.toJson(funcionario));
    }

    /**
     * Manipula requisições HTTP PUT para atualizar um funcionário existente.
     *
     * @param exchange O objeto HttpExchange para manipular a resposta HTTP.
     * @throws IOException Se ocorrer um erro ao enviar a resposta.
     */
    private void handlePut(HttpExchange exchange) throws IOException {
        var path = exchange.getRequestURI().getPath();
        var id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
        var funcionario = gson.fromJson(
                new InputStreamReader(exchange.getRequestBody()),
                Funcionario.class
        );

        if (controller.atualizarFuncionario(id, funcionario)) {
            responseUtil.sendResponse(exchange, 200, gson.toJson(funcionario));
        } else {
            responseUtil.sendResponse(exchange, 404, "Funcionário não encontrado");
        }
    }

    /**
     * Manipula requisições HTTP DELETE para remover um funcionário pelo ID.
     *
     * @param exchange O objeto HttpExchange para manipular a resposta HTTP.
     * @throws IOException Se ocorrer um erro ao enviar a resposta.
     */
    private void handleDelete(HttpExchange exchange) throws IOException {
        var path = exchange.getRequestURI().getPath();
        var id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));

        if (controller.removerFuncionario(id)) {
            responseUtil.sendResponse(exchange, 204, "");
        } else {
            responseUtil.sendResponse(exchange, 404, "Funcionário não encontrado");
        }
    }
}
