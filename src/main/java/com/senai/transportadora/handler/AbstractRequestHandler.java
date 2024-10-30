package com.senai.transportadora.handler;

import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Uma classe abstrata que implementa a interface RequestHandler para lidar com requisições HTTP.
 * Fornece um framework básico para manipulação de requisições HTTP com suporte a CORS
 * e tratamento de diferentes métodos HTTP (GET, POST, PUT, DELETE).
 */
public abstract class AbstractRequestHandler implements RequestHandler {
    /**
     * Utilitário para enviar respostas HTTP.
     */
    private final HttpResponseUtil responseUtil;

    /**
     * Construtor para AbstractRequestHandler.
     *
     * @param responseUtil Utilitário para gerenciar respostas HTTP
     */
    public AbstractRequestHandler(HttpResponseUtil responseUtil) {
        this.responseUtil = responseUtil;
    }

    /**
     * Manipula a requisição HTTP recebida.
     * Configura headers CORS, processa requisições OPTIONS e roteia para o método apropriado
     * baseado no tipo de requisição HTTP.
     *
     * @param exchange O objeto HttpExchange contendo a requisição e resposta
     * @throws IOException Se ocorrer um erro durante o processamento da requisição
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
     * Método abstrato para manipular requisições GET.
     *
     * @param exchange O objeto HttpExchange contendo a requisição e resposta
     * @throws IOException Se ocorrer um erro durante o processamento da requisição
     */
    protected abstract void handleGet(HttpExchange exchange) throws IOException;

    /**
     * Método abstrato para manipular requisições POST.
     *
     * @param exchange O objeto HttpExchange contendo a requisição e resposta
     * @throws IOException Se ocorrer um erro durante o processamento da requisição
     */
    protected abstract void handlePost(HttpExchange exchange) throws IOException;

    /**
     * Método abstrato para manipular requisições PUT.
     *
     * @param exchange O objeto HttpExchange contendo a requisição e resposta
     * @throws IOException Se ocorrer um erro durante o processamento da requisição
     */
    protected abstract void handlePut(HttpExchange exchange) throws IOException;

    /**
     * Método abstrato para manipular requisições DELETE.
     *
     * @param exchange O objeto HttpExchange contendo a requisição e resposta
     * @throws IOException Se ocorrer um erro durante o processamento da requisição
     */
    protected abstract void handleDelete(HttpExchange exchange) throws IOException;
}