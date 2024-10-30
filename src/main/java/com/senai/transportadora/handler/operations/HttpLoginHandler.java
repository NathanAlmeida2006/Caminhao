package com.senai.transportadora.handler.operations;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Interface para manipuladores de requisições de login HTTP.
 * <p>
 * Esta interface define o contrato para classes que desejam lidar
 * com requisições de login em um servidor HTTP.
 * </p>
 */
public interface HttpLoginHandler {
    /**
     * Manipula uma requisição de login HTTP.
     *
     * @param exchange o objeto {@link HttpExchange} representando a requisição HTTP
     * @throws IOException se ocorrer um erro ao processar a requisição
     */
    void handleLogin(HttpExchange exchange) throws IOException;
}
