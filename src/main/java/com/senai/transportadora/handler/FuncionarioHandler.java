/**
 * Classe que gerencia as requisições HTTP relacionadas a funcionários.
 * Extends AbstractRequestHandler e delega as operações específicas para handlers especializados.
 */
package com.senai.transportadora.handler;

import com.google.gson.Gson;
import com.senai.transportadora.controller.FuncionarioController;
import com.senai.transportadora.handler.funcionario.*;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class FuncionarioHandler extends AbstractRequestHandler {
    /**
     * Handler responsável por processar requisições GET de funcionários.
     */
    private final FuncionarioGetHandler getHandler;

    /**
     * Handler responsável por processar requisições POST de funcionários.
     */
    private final FuncionarioPostHandler postHandler;

    /**
     * Handler responsável por processar requisições PUT de funcionários.
     */
    private final FuncionarioPutHandler putHandler;

    /**
     * Handler responsável por processar requisições DELETE de funcionários.
     */
    private final FuncionarioDeleteHandler deleteHandler;

    /**
     * Construtor que inicializa todos os handlers específicos para cada tipo de requisição.
     *
     * @param controller   O controlador que contém a lógica de negócio para operações com funcionários
     * @param gson         Objeto Gson para serialização/deserialização JSON
     * @param responseUtil Utilitário para gerenciar respostas HTTP
     */
    public FuncionarioHandler(FuncionarioController controller, Gson gson, HttpResponseUtil responseUtil) {
        super(responseUtil);
        this.getHandler = new FuncionarioGetHandler(controller, gson, responseUtil);
        this.postHandler = new FuncionarioPostHandler(controller, gson, responseUtil);
        this.putHandler = new FuncionarioPutHandler(controller, gson, responseUtil);
        this.deleteHandler = new FuncionarioDeleteHandler(controller, responseUtil);
    }

    /**
     * Processa requisições HTTP GET delegando para o handler específico.
     *
     * @param exchange O objeto HttpExchange contendo a requisição e resposta
     * @throws IOException Se ocorrer um erro durante o processamento da requisição
     */
    @Override
    protected void handleGet(HttpExchange exchange) throws IOException {
        getHandler.handleGet(exchange);
    }

    /**
     * Processa requisições HTTP POST delegando para o handler específico.
     *
     * @param exchange O objeto HttpExchange contendo a requisição e resposta
     * @throws IOException Se ocorrer um erro durante o processamento da requisição
     */
    @Override
    protected void handlePost(HttpExchange exchange) throws IOException {
        postHandler.handlePost(exchange);
    }

    /**
     * Processa requisições HTTP PUT delegando para o handler específico.
     *
     * @param exchange O objeto HttpExchange contendo a requisição e resposta
     * @throws IOException Se ocorrer um erro durante o processamento da requisição
     */
    @Override
    protected void handlePut(HttpExchange exchange) throws IOException {
        putHandler.handlePut(exchange);
    }

    /**
     * Processa requisições HTTP DELETE delegando para o handler específico.
     *
     * @param exchange O objeto HttpExchange contendo a requisição e resposta
     * @throws IOException Se ocorrer um erro durante o processamento da requisição
     */
    @Override
    protected void handleDelete(HttpExchange exchange) throws IOException {
        deleteHandler.handleDelete(exchange);
    }
}