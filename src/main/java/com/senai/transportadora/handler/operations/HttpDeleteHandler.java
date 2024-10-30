package com.senai.transportadora.handler.operations;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Interface para manipuladores de requisições HTTP DELETE.
 * <p>
 * Esta interface define o contrato para classes que desejam lidar
 * com requisições DELETE em um servidor HTTP.
 * </p>
 */
public interface HttpDeleteHandler {
    /**
     * Manipula uma requisição HTTP DELETE.
     *
     * @param exchange o objeto {@link HttpExchange} representando a requisição HTTP
     * @throws IOException se ocorrer um erro ao processar a requisição
     */
    void handleDelete(HttpExchange exchange) throws IOException;
}
