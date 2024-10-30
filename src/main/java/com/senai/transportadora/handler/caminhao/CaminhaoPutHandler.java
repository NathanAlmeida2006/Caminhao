package com.senai.transportadora.handler.caminhao;

import com.google.gson.Gson;
import com.senai.transportadora.controller.CaminhaoController;
import com.senai.transportadora.entity.Caminhao;
import com.senai.transportadora.handler.operations.HttpPutHandler;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Manipulador para lidar com requisições HTTP PUT relacionadas a caminhões.
 * <p>
 * Esta classe utiliza o {@link CaminhaoController} para atualizar informações de caminhões e o
 * {@link HttpResponseUtil} para enviar respostas apropriadas para as requisições.
 * </p>
 */
public class CaminhaoPutHandler implements HttpPutHandler {
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
    public CaminhaoPutHandler(CaminhaoController controller, Gson gson, HttpResponseUtil responseUtil) {
        this.controller = controller;
        this.gson = gson;
        this.responseUtil = responseUtil;
    }

    /**
     * Lida com requisições PUT para atualizar um caminhão existente.
     * <p>
     * Deserializa os dados do caminhão a partir do corpo da requisição, identifica o caminhão pelo ID na URI,
     * e tenta atualizá-lo usando o controlador. Envia uma resposta com status 200 (OK) e o objeto caminhão
     * atualizado em JSON se a atualização for bem-sucedida. Se o caminhão não for encontrado, envia uma
     * resposta com status 404 (Not Found).
     * </p>
     *
     * @param exchange o objeto {@link HttpExchange} representando a requisição HTTP
     * @throws IOException se ocorrer um erro ao enviar a resposta
     */
    @Override
    public void handlePut(HttpExchange exchange) throws IOException {
        var path = exchange.getRequestURI().getPath();
        var id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
        var caminhao = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Caminhao.class);

        if (controller.atualizarCaminhao(id, caminhao)) {
            responseUtil.sendResponse(exchange, 200, gson.toJson(caminhao));
        } else {
            responseUtil.sendResponse(exchange, 404, "Caminhão não encontrado");
        }
    }
}
