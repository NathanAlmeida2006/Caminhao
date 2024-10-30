package com.senai.transportadora.handler.caminhao;

import com.google.gson.Gson;
import com.senai.transportadora.controller.CaminhaoController;
import com.senai.transportadora.entity.Caminhao;
import com.senai.transportadora.handler.operations.HttpPostHandler;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Manipulador para lidar com requisições HTTP POST relacionadas a caminhões.
 * <p>
 * Esta classe utiliza o {@link CaminhaoController} para adicionar novos caminhões e o
 * {@link HttpResponseUtil} para enviar respostas apropriadas para as requisições.
 * </p>
 */
public class CaminhaoPostHandler implements HttpPostHandler {
    private final CaminhaoController controller;
    private final Gson gson;
    private final HttpResponseUtil responseUtil;

    /**
     * Construtor que inicializa o manipulador com o controlador de caminhão, o utilitário de serialização Gson,
     * e o utilitário de resposta HTTP.
     *
     * @param controller   o controlador de caminhão para gerenciamento das operações de caminhão
     * @param gson         o utilitário Gson para serialização de objetos em JSON
     * @param responseUtil o utilitário de resposta HTTP para enviar respostas
     */
    public CaminhaoPostHandler(CaminhaoController controller, Gson gson, HttpResponseUtil responseUtil) {
        this.controller = controller;
        this.gson = gson;
        this.responseUtil = responseUtil;
    }

    /**
     * Lida com requisições POST para adicionar um novo caminhão.
     * <p>
     * Deserializa os dados do caminhão a partir do corpo da requisição, adiciona-o usando o controlador,
     * e envia uma resposta com status 201 (Created) e o objeto caminhão em formato JSON.
     * </p>
     *
     * @param exchange o objeto {@link HttpExchange} representando a requisição HTTP
     * @throws IOException se ocorrer um erro ao enviar a resposta
     */
    @Override
    public void handlePost(HttpExchange exchange) throws IOException {
        var caminhao = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Caminhao.class);
        controller.adicionarCaminhao(caminhao);
        responseUtil.sendResponse(exchange, 201, gson.toJson(caminhao));
    }
}
