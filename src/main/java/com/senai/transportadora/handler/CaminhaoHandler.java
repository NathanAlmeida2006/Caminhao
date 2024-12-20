/**
 * Classe que gerencia as requisições HTTP relacionadas a caminhões.
 * Extends AbstractRequestHandler e delega as operações específicas para handlers especializados.
 */
package com.senai.transportadora.handler;

import com.google.gson.Gson;
import com.senai.transportadora.controller.CaminhaoController;
import com.senai.transportadora.handler.caminhao.*;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class CaminhaoHandler extends AbstractRequestHandler {
    /**
     * Handler responsável por processar requisições GET de caminhões.
     */
    private final CaminhaoGetHandler getHandler;

    /**
     * Handler responsável por processar requisições POST de caminhões.
     */
    private final CaminhaoPostHandler postHandler;

    /**
     * Handler responsável por processar requisições PUT de caminhões.
     */
    private final CaminhaoPutHandler putHandler;

    /**
     * Handler responsável por processar requisições DELETE de caminhões.
     */
    private final CaminhaoDeleteHandler deleteHandler;

    /**
     * Construtor que inicializa todos os handlers específicos para cada tipo de requisição.
     *
     * @param controller   O controlador que contém a lógica de negócio para operações com caminhões
     * @param gson         Objeto Gson para serialização/deserialização JSON
     * @param responseUtil Utilitário para gerenciar respostas HTTP
     */
    public CaminhaoHandler(CaminhaoController controller, Gson gson, HttpResponseUtil responseUtil) {
        super(responseUtil);
        this.getHandler = new CaminhaoGetHandler(controller, gson, responseUtil);
        this.postHandler = new CaminhaoPostHandler(controller, gson, responseUtil);
        this.putHandler = new CaminhaoPutHandler(controller, gson, responseUtil);
        this.deleteHandler = new CaminhaoDeleteHandler(controller, responseUtil);
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