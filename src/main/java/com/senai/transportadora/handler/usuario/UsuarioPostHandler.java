package com.senai.transportadora.handler.usuario;

import com.google.gson.Gson;
import com.senai.transportadora.controller.UsuarioController;
import com.senai.transportadora.entity.Usuario;
import com.senai.transportadora.handler.operations.HttpPostHandler;
import com.senai.transportadora.handler.operations.HttpLoginHandler;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Manipulador de requisições POST para usuários.
 * <p>
 * Esta classe é responsável por processar as requisições HTTP POST
 * para a entidade Usuário, incluindo a adição de novos usuários
 * e autenticação de usuários existentes.
 * </p>
 */
public class UsuarioPostHandler implements HttpPostHandler, HttpLoginHandler {
    private final UsuarioController controller;
    private final Gson gson;
    private final HttpResponseUtil responseUtil;

    /**
     * Construtor do UsuarioPostHandler.
     *
     * @param controller   o controlador de usuários
     * @param gson         a instância do Gson para conversão de objetos
     * @param responseUtil utilitário para enviar respostas HTTP
     */
    public UsuarioPostHandler(UsuarioController controller, Gson gson, HttpResponseUtil responseUtil) {
        this.controller = controller;
        this.gson = gson;
        this.responseUtil = responseUtil;
    }

    /**
     * Manipula uma requisição POST para adicionar usuários ou autenticar.
     *
     * @param exchange o objeto {@link HttpExchange} representando a requisição HTTP
     * @throws IOException se ocorrer um erro ao processar a requisição
     */
    @Override
    public void handlePost(HttpExchange exchange) throws IOException {
        var path = exchange.getRequestURI().getPath();
        if (path.endsWith("/login")) {
            handleLogin(exchange);
            return;
        }

        var usuario = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Usuario.class);
        controller.adicionarUsuario(usuario);
        responseUtil.sendResponse(exchange, 201, gson.toJson(usuario));
    }

    /**
     * Manipula uma requisição para autenticar um usuário.
     *
     * @param exchange o objeto {@link HttpExchange} representando a requisição HTTP
     * @throws IOException se ocorrer um erro ao processar a requisição
     */
    @Override
    public void handleLogin(HttpExchange exchange) throws IOException {
        var credentials = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Map.class);
        var email = (String) credentials.get("email");
        var senha = (String) credentials.get("senha");

        if (controller.autenticar(email, senha)) {
            responseUtil.sendResponse(exchange, 200, "{\"token\": \"usuario-autenticado\"}");
        } else {
            responseUtil.sendResponse(exchange, 401, "Credenciais inválidas");
        }
    }
}
