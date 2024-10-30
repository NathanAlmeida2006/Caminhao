package com.senai.transportadora.handler.funcionario;

import com.google.gson.Gson;
import com.senai.transportadora.controller.FuncionarioController;
import com.senai.transportadora.entity.Funcionario;
import com.senai.transportadora.handler.operations.HttpPostHandler;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Manipulador para lidar com requisições HTTP POST relacionadas a funcionários.
 * <p>
 * Esta classe utiliza o {@link FuncionarioController} para adicionar novos funcionários
 * e o {@link HttpResponseUtil} para enviar as respostas apropriadas para as requisições.
 * </p>
 */
public class FuncionarioPostHandler implements HttpPostHandler {
    private final FuncionarioController controller;
    private final Gson gson;
    private final HttpResponseUtil responseUtil;

    /**
     * Construtor que inicializa o manipulador com o controlador de funcionários, o Gson para conversão de JSON
     * e o utilitário de resposta HTTP.
     *
     * @param controller   o controlador de funcionários para gerenciar operações de adição
     * @param gson         a instância de Gson para conversão de objetos em JSON
     * @param responseUtil o utilitário de resposta HTTP para enviar respostas
     */
    public FuncionarioPostHandler(FuncionarioController controller, Gson gson, HttpResponseUtil responseUtil) {
        this.controller = controller;
        this.gson = gson;
        this.responseUtil = responseUtil;
    }

    /**
     * Lida com requisições POST para adicionar um novo funcionário.
     * <p>
     * Lê os dados do funcionário do corpo da requisição, chama o metodo do controlador
     * para adicionar o funcionário e envia a resposta com status 201 (Criado) junto com
     * os dados do funcionário em formato JSON.
     * </p>
     *
     * @param exchange o objeto {@link HttpExchange} representando a requisição HTTP
     * @throws IOException se ocorrer um erro ao enviar a resposta
     */
    @Override
    public void handlePost(HttpExchange exchange) throws IOException {
        var funcionario = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Funcionario.class);
        controller.adicionarFuncionario(funcionario);
        responseUtil.sendResponse(exchange, 201, gson.toJson(funcionario));
    }
}
