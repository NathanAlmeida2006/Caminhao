package com.senai.transportadora.handler.usuario;

import com.google.gson.Gson;
import com.senai.transportadora.controller.UsuarioController;
import com.senai.transportadora.entity.Usuario;
import com.senai.transportadora.handler.operations.HttpPutHandler;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Manipulador de requisições PUT para usuários.
 * <p>
 * Esta classe é responsável por processar as requisições HTTP PUT
 * para a entidade Usuário, permitindo a atualização de informações
 * de usuários existentes.
 * </p>
 */
public class UsuarioPutHandler implements HttpPutHandler {
    private final UsuarioController controller;
    private final Gson gson;
    private final HttpResponseUtil responseUtil;

    /**
     * Construtor do UsuarioPutHandler.
     *
     * @param controller   o controlador de usuários
     * @param gson         a instância do Gson para conversão de objetos
     * @param responseUtil utilitário para enviar respostas HTTP
     */
    public UsuarioPutHandler(UsuarioController controller, Gson gson, HttpResponseUtil responseUtil) {
        this.controller = controller;
        this.gson = gson;
        this.responseUtil = responseUtil;
    }

    /**
     * Manipula uma requisição PUT para atualizar um usuário existente.
     *
     * @param exchange o objeto {@link HttpExchange} representando a requisição HTTP
     * @throws IOException se ocorrer um erro ao processar a requisição
     */
    @Override
    public void handlePut(HttpExchange exchange) throws IOException {
        var path = exchange.getRequestURI().getPath();
        var id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
        var usuario = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Usuario.class);

        if (controller.atualizarUsuario(id, usuario)) {
            responseUtil.sendResponse(exchange, 200, gson.toJson(usuario));
        } else {
            responseUtil.sendResponse(exchange, 404, "Usuário não encontrado");
        }
    }
}
