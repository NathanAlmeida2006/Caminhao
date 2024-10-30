package com.senai.transportadora.handler.usuario;

import com.senai.transportadora.controller.UsuarioController;
import com.senai.transportadora.handler.operations.HttpDeleteHandler;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Manipulador de requisições DELETE para usuários.
 * <p>
 * Esta classe é responsável por processar as requisições HTTP DELETE
 * para a entidade Usuário, removendo um usuário com base no ID fornecido na URL.
 * </p>
 */
public class UsuarioDeleteHandler implements HttpDeleteHandler {
    private final UsuarioController controller;
    private final HttpResponseUtil responseUtil;

    /**
     * Construtor do UsuarioDeleteHandler.
     *
     * @param controller   o controlador de usuários
     * @param responseUtil utilitário para enviar respostas HTTP
     */
    public UsuarioDeleteHandler(UsuarioController controller, HttpResponseUtil responseUtil) {
        this.controller = controller;
        this.responseUtil = responseUtil;
    }

    /**
     * Manipula uma requisição DELETE para remover um usuário.
     *
     * @param exchange o objeto {@link HttpExchange} representando a requisição HTTP
     * @throws IOException se ocorrer um erro ao processar a requisição
     */
    @Override
    public void handleDelete(HttpExchange exchange) throws IOException {
        var path = exchange.getRequestURI().getPath();
        var id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));

        if (controller.removerUsuario(id)) {
            responseUtil.sendResponse(exchange, 204, "");
        } else {
            responseUtil.sendResponse(exchange, 404, "Usuário não encontrado");
        }
    }
}
