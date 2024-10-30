package com.senai.transportadora.handler.funcionario;

import com.google.gson.Gson;
import com.senai.transportadora.controller.FuncionarioController;
import com.senai.transportadora.handler.operations.HttpGetHandler;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Manipulador para lidar com requisições HTTP GET relacionadas a funcionários.
 * <p>
 * Esta classe utiliza o {@link FuncionarioController} para listar todos os funcionários
 * e o {@link HttpResponseUtil} para enviar as respostas apropriadas para as requisições.
 * </p>
 */
public class FuncionarioGetHandler implements HttpGetHandler {
    private final FuncionarioController controller;
    private final Gson gson;
    private final HttpResponseUtil responseUtil;

    /**
     * Construtor que inicializa o manipulador com o controlador de funcionários, o Gson para conversão de JSON
     * e o utilitário de resposta HTTP.
     *
     * @param controller   o controlador de funcionários para gerenciar operações de listagem
     * @param gson         a instância de Gson para conversão de objetos em JSON
     * @param responseUtil o utilitário de resposta HTTP para enviar respostas
     */
    public FuncionarioGetHandler(FuncionarioController controller, Gson gson, HttpResponseUtil responseUtil) {
        this.controller = controller;
        this.gson = gson;
        this.responseUtil = responseUtil;
    }

    /**
     * Lida com requisições GET para listar todos os funcionários.
     * <p>
     * Chama o metodo do controlador para obter a lista de funcionários e envia a resposta
     * com status 200 (OK) junto com os dados dos funcionários em formato JSON.
     * </p>
     *
     * @param exchange o objeto {@link HttpExchange} representando a requisição HTTP
     * @throws IOException se ocorrer um erro ao enviar a resposta
     */
    @Override
    public void handleGet(HttpExchange exchange) throws IOException {
        var funcionarios = controller.listarFuncionarios();
        responseUtil.sendResponse(exchange, 200, gson.toJson(funcionarios));
    }
}
