package com.senai.transportadora.handler.operations;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Interface para manipuladores de requisições POST HTTP.
 * <p>
 * Esta interface define o contrato para classes que desejam lidar
 * com requisições POST em um servidor HTTP.
 * </p>
 */
public interface HttpPostHandler {
    /**
     * Manipula uma requisição POST HTTP.
     *
     * @param exchange o objeto {@link HttpExchange} representando a requisição HTTP
     * @throws IOException se ocorrer um erro ao processar a requisição
     */
    void handlePost(HttpExchange exchange) throws IOException;
}
