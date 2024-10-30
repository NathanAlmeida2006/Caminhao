package com.senai.transportadora.handler.operations;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Interface para manipuladores de requisições PUT HTTP.
 * <p>
 * Esta interface define o contrato para classes que desejam lidar
 * com requisições PUT em um servidor HTTP.
 * </p>
 */
public interface HttpPutHandler {
    /**
     * Manipula uma requisição PUT HTTP.
     *
     * @param exchange o objeto {@link HttpExchange} representando a requisição HTTP
     * @throws IOException se ocorrer um erro ao processar a requisição
     */
    void handlePut(HttpExchange exchange) throws IOException;
}
