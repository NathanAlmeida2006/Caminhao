package com.senai.transportadora.util;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Classe utilitária para enviar respostas HTTP em um servidor HTTP.
 * <p>
 * Esta classe fornece um metodo para configurar e enviar respostas HTTP com um código de status
 * e corpo de resposta opcionais.
 * </p>
 */
public class HttpResponseUtil {

    /**
     * Envia uma resposta HTTP com o código de status e conteúdo especificados.
     * <p>
     * Define o cabeçalho "Content-Type" como "application/json". Para respostas com o status 204
     * (No Content), o corpo da resposta é fechado sem conteúdo.
     * </p>
     *
     * @param exchange   Objeto {@link HttpExchange} que representa o contexto da troca de informações HTTP.
     * @param statusCode Código de status HTTP a ser enviado na resposta.
     * @param response   Conteúdo da resposta em formato {@link String}.
     * @throws IOException Se ocorrer um erro de E/S ao enviar a resposta.
     */
    public void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");

        if (statusCode == 204) {
            exchange.sendResponseHeaders(statusCode, -1);
            exchange.getResponseBody().close();
            return;
        }

        byte[] responseBytes = response.getBytes();
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
