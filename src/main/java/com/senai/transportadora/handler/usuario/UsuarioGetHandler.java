package com.senai.transportadora.handler.usuario;

import com.google.gson.Gson;
import com.senai.transportadora.controller.UsuarioController;
import com.senai.transportadora.handler.operations.HttpGetHandler;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Manipulador de requisições GET para usuários.
 * <p>
 * Esta classe é responsável por processar as requisições HTTP GET
 * para a entidade Usuário, retornando a lista de usuários cadastrados.
 * </p>
 */
public class UsuarioGetHandler implements HttpGetHandler {
    private final UsuarioController controller;
    private final Gson gson;
    private final HttpResponseUtil responseUtil;

    /**
     * Construtor do UsuarioGetHandler.
     *
     * @param controller   o controlador de usuários
     * @param gson         a instância do Gson para conversão de objetos
     * @param responseUtil utilitário para enviar respostas HTTP
     */
    public UsuarioGetHandler(UsuarioController controller, Gson gson, HttpResponseUtil responseUtil) {
        this.controller = controller;
        this.gson = gson;
        this.responseUtil = responseUtil;
    }

    /**
     * Manipula uma requisição GET para listar usuários.
     *
     * @param exchange o objeto {@link HttpExchange} representando a requisição HTTP
     * @throws IOException se ocorrer um erro ao processar a requisição
     */
    @Override
    public void handleGet(HttpExchange exchange) throws IOException {
        var usuarios = controller.listarUsuarios();
        responseUtil.sendResponse(exchange, 200, gson.toJson(usuarios));
    }
}
