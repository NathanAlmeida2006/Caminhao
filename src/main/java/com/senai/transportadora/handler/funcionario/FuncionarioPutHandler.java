package com.senai.transportadora.handler.funcionario;

import com.google.gson.Gson;
import com.senai.transportadora.controller.FuncionarioController;
import com.senai.transportadora.entity.Funcionario;
import com.senai.transportadora.handler.operations.HttpPutHandler;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Manipulador para lidar com requisições HTTP PUT relacionadas a funcionários.
 * <p>
 * Esta classe utiliza o {@link FuncionarioController} para atualizar as informações de um funcionário
 * e o {@link HttpResponseUtil} para enviar as respostas apropriadas para as requisições.
 * </p>
 */
public class FuncionarioPutHandler implements HttpPutHandler {
    private final FuncionarioController controller;
    private final Gson gson;
    private final HttpResponseUtil responseUtil;

    /**
     * Construtor que inicializa o manipulador com o controlador de funcionários, o Gson para conversão de JSON
     * e o utilitário de resposta HTTP.
     *
     * @param controller   o controlador de funcionários para gerenciar operações de atualização
     * @param gson         a instância de Gson para conversão de objetos em JSON
     * @param responseUtil o utilitário de resposta HTTP para enviar respostas
     */
    public FuncionarioPutHandler(FuncionarioController controller, Gson gson, HttpResponseUtil responseUtil) {
        this.controller = controller;
        this.gson = gson;
        this.responseUtil = responseUtil;
    }

    /**
     * Lida com requisições PUT para atualizar as informações de um funcionário existente.
     * <p>
     * Extrai o ID do funcionário da URL, lê os dados do funcionário do corpo da requisição,
     * chama o método do controlador para atualizar o funcionário e envia a resposta com
     * status 200 (OK) junto com os dados atualizados do funcionário em formato JSON ou
     * status 404 (Não Encontrado) se o funcionário não for encontrado.
     * </p>
     *
     * @param exchange o objeto {@link HttpExchange} representando a requisição HTTP
     * @throws IOException se ocorrer um erro ao enviar a resposta
     */
    @Override
    public void handlePut(HttpExchange exchange) throws IOException {
        var path = exchange.getRequestURI().getPath();
        var id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
        var funcionario = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Funcionario.class);

        if (controller.atualizarFuncionario(id, funcionario)) {
            responseUtil.sendResponse(exchange, 200, gson.toJson(funcionario));
        } else {
            responseUtil.sendResponse(exchange, 404, "Funcionário não encontrado");
        }
    }
}
