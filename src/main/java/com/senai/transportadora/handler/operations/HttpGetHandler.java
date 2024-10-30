package com.senai.transportadora.handler.operations;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Interface para manipuladores de requisições HTTP GET.
 * <p>
 * Esta interface define o contrato para classes que desejam lidar
 * com requisições GET em um servidor HTTP.
 * </p>
 */
public interface HttpGetHandler {
    /**
     * Manipula uma requisição HTTP GET.
     *
     * @param exchange o objeto {@link HttpExchange} representando a requisição HTTP
     * @throws IOException se ocorrer um erro ao processar a requisição
     */
    void handleGet(HttpExchange exchange) throws IOException;
}
