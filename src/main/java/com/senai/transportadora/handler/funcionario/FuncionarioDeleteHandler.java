package com.senai.transportadora.handler.funcionario;

import com.senai.transportadora.controller.FuncionarioController;
import com.senai.transportadora.handler.operations.HttpDeleteHandler;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Manipulador para lidar com requisições HTTP DELETE relacionadas a funcionários.
 * <p>
 * Esta classe utiliza o {@link FuncionarioController} para remover funcionários com base em seus IDs
 * e o {@link HttpResponseUtil} para enviar respostas apropriadas para as requisições.
 * </p>
 */
public class FuncionarioDeleteHandler implements HttpDeleteHandler {
    private final FuncionarioController controller;
    private final HttpResponseUtil responseUtil;

    /**
     * Construtor que inicializa o manipulador com o controlador de funcionários e o utilitário de resposta HTTP.
     *
     * @param controller   o controlador de funcionários para gerenciar as operações de exclusão
     * @param responseUtil o utilitário de resposta HTTP para enviar respostas
     */
    public FuncionarioDeleteHandler(FuncionarioController controller, HttpResponseUtil responseUtil) {
        this.controller = controller;
        this.responseUtil = responseUtil;
    }

    /**
     * Lida com requisições DELETE para remover um funcionário específico.
     * <p>
     * Extrai o ID do funcionário a partir da URI da requisição e tenta removê-lo usando o controlador.
     * Envia uma resposta com status 204 (No Content) se a remoção for bem-sucedida, ou com status 404 (Not Found)
     * se o funcionário não for encontrado.
     * </p>
     *
     * @param exchange o objeto {@link HttpExchange} representando a requisição HTTP
     * @throws IOException se ocorrer um erro ao enviar a resposta
     */
    @Override
    public void handleDelete(HttpExchange exchange) throws IOException {
        var path = exchange.getRequestURI().getPath();
        var id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));

        if (controller.removerFuncionario(id)) {
            responseUtil.sendResponse(exchange, 204, "");
        } else {
            responseUtil.sendResponse(exchange, 404, "Funcionário não encontrado");
        }
    }
}
