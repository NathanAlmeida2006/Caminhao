package com.senai.transportadora.handler.usuario;

import com.google.gson.Gson;
import com.senai.transportadora.controller.UsuarioController;
import com.senai.transportadora.handler.operations.HttpLoginHandler;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class UsuarioLoginHandler implements HttpLoginHandler {
    private final UsuarioController controller;
    private final Gson gson;
    private final HttpResponseUtil responseUtil;

    public UsuarioLoginHandler(UsuarioController controller, Gson gson, HttpResponseUtil responseUtil) {
        this.controller = controller;
        this.gson = gson;
        this.responseUtil = responseUtil;
    }

    @Override
    public void handleLogin(HttpExchange exchange) throws IOException {
        var credentials = gson.fromJson(
                new InputStreamReader(exchange.getRequestBody()),
                Map.class
        );

        String email = (String) credentials.get("email");
        String senha = (String) credentials.get("senha");

        if (controller.autenticar(email, senha)) {
            var response = Map.of("token", "usuario-autenticado");
            responseUtil.sendResponse(exchange, 200, gson.toJson(response));
        } else {
            responseUtil.sendResponse(exchange, 401, "Credenciais inválidas");
        }
    }
}