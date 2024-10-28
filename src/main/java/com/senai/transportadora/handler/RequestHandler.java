package com.senai.transportadora.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Interface que define um manipulador de requisições HTTP com suporte a CORS (Cross-Origin Resource Sharing).
 * Estende a interface HttpHandler para manipulação de requisições e inclui configuração padrão de cabeçalhos CORS.
 */
public interface RequestHandler extends HttpHandler {

    /**
     * Metodo para manipular requisições HTTP.
     *
     * @param exchange O objeto HttpExchange que encapsula a requisição e resposta.
     * @throws IOException Se ocorrer um erro durante o processamento da requisição.
     */
    void handle(HttpExchange exchange) throws IOException;

    /**
     * Metodo padrão que configura os cabeçalhos CORS para permitir o compartilhamento de recursos entre origens diferentes.
     * Define os métodos permitidos (GET, POST, PUT, DELETE, OPTIONS) e os cabeçalhos permitidos (Content-Type).
     *
     * @param exchange O objeto HttpExchange para configurar os cabeçalhos da resposta.
     */
    default void setupCorsHeaders(HttpExchange exchange) {
        var headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
    }
}
