/**
 * Classe que gerencia as requisições HTTP relacionadas a usuários.
 * Extends AbstractRequestHandler e delega as operações específicas para handlers especializados.
 */
package com.senai.transportadora.handler;

import com.google.gson.Gson;
import com.senai.transportadora.controller.UsuarioController;
import com.senai.transportadora.handler.usuario.*;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class UsuarioHandler extends AbstractRequestHandler {
    /**
     * Handler responsável por processar requisições GET de usuários.
     */
    private final UsuarioGetHandler getHandler;

    /**
     * Handler responsável por processar requisições POST de usuários.
     */
    private final UsuarioPostHandler postHandler;

    /**
     * Handler responsável por processar requisições PUT de usuários.
     */
    private final UsuarioPutHandler putHandler;

    /**
     * Handler responsável por processar requisições DELETE de usuários.
     */
    private final UsuarioDeleteHandler deleteHandler;

    /**
     * Handler responsável por processar requisições de login.
     */
    private final UsuarioLoginHandler loginHandler;

    /**
     * Construtor que inicializa todos os handlers específicos para cada tipo de requisição.
     *
     * @param controller   O controlador que contém a lógica de negócio para operações com usuários
     * @param gson         Objeto Gson para serialização/deserialização JSON
     * @param responseUtil Utilitário para gerenciar respostas HTTP
     */
    public UsuarioHandler(UsuarioController controller, Gson gson, HttpResponseUtil responseUtil) {
        super(responseUtil);
        this.getHandler = new UsuarioGetHandler(controller, gson, responseUtil);
        this.postHandler = new UsuarioPostHandler(controller, gson, responseUtil);
        this.putHandler = new UsuarioPutHandler(controller, gson, responseUtil);
        this.deleteHandler = new UsuarioDeleteHandler(controller, responseUtil);
        this.loginHandler = new UsuarioLoginHandler(controller, gson, responseUtil);
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
     * Se o caminho terminar com "/login", delega para o loginHandler.
     *
     * @param exchange O objeto HttpExchange contendo a requisição e resposta
     * @throws IOException Se ocorrer um erro durante o processamento da requisição
     */
    @Override
    protected void handlePost(HttpExchange exchange) throws IOException {
        var path = exchange.getRequestURI().getPath();
        if (path.endsWith("/login")) {
            loginHandler.handleLogin(exchange);
            return;
        }
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