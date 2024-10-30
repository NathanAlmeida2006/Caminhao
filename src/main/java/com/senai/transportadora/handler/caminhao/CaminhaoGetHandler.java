package com.senai.transportadora.handler.caminhao;

import com.google.gson.Gson;
import com.senai.transportadora.controller.CaminhaoController;
import com.senai.transportadora.handler.operations.HttpGetHandler;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Manipulador para lidar com requisições HTTP GET relacionadas a caminhões.
 * <p>
 * Esta classe utiliza o {@link CaminhaoController} para obter a lista de caminhões e o
 * {@link HttpResponseUtil} para enviar respostas apropriadas para as requisições.
 * </p>
 */
public class CaminhaoGetHandler implements HttpGetHandler {
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
    public CaminhaoGetHandler(CaminhaoController controller, Gson gson, HttpResponseUtil responseUtil) {
        this.controller = controller;
        this.gson = gson;
        this.responseUtil = responseUtil;
    }

    /**
     * Lida com requisições GET para obter a lista de caminhões disponíveis.
     * <p>
     * Serializa a lista de caminhões em JSON e envia uma resposta com status 200 (OK).
     * </p>
     *
     * @param exchange o objeto {@link HttpExchange} representando a requisição HTTP
     * @throws IOException se ocorrer um erro ao enviar a resposta
     */
    @Override
    public void handleGet(HttpExchange exchange) throws IOException {
        var caminhoes = controller.listarCaminhoes();
        responseUtil.sendResponse(exchange, 200, gson.toJson(caminhoes));
    }
}
