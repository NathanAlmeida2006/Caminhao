package com.senai.transportadora.handler.caminhao;

import com.senai.transportadora.controller.CaminhaoController;
import com.senai.transportadora.handler.operations.HttpDeleteHandler;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Manipulador para lidar com requisições HTTP DELETE relacionadas a caminhões.
 * <p>
 * Esta classe utiliza o {@link CaminhaoController} para remover caminhões e o
 * {@link HttpResponseUtil} para enviar respostas apropriadas para as requisições.
 * </p>
 */
public class CaminhaoDeleteHandler implements HttpDeleteHandler {
    private final CaminhaoController controller;
    private final HttpResponseUtil responseUtil;

    /**
     * Construtor que inicializa o manipulador com o controlador de caminhão e utilitário de resposta HTTP.
     *
     * @param controller   o controlador de caminhão para gerenciamento das operações de caminhão
     * @param responseUtil o utilitário de resposta HTTP para enviar respostas
     */
    public CaminhaoDeleteHandler(CaminhaoController controller, HttpResponseUtil responseUtil) {
        this.controller = controller;
        this.responseUtil = responseUtil;
    }

    /**
     * Lida com requisições DELETE para remover um caminhão com base no identificador especificado na URL.
     * <p>
     * Se o caminhão for removido com sucesso, uma resposta de status 204 (No Content) é enviada.
     * Caso contrário, uma resposta de status 404 (Not Found) é enviada indicando que o caminhão não foi encontrado.
     * </p>
     *
     * @param exchange o objeto {@link HttpExchange} representando a requisição HTTP
     * @throws IOException se ocorrer um erro ao enviar a resposta
     */
    @Override
    public void handleDelete(HttpExchange exchange) throws IOException {
        var path = exchange.getRequestURI().getPath();
        var id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));

        if (controller.removerCaminhao(id)) {
            responseUtil.sendResponse(exchange, 204, "");
        } else {
            responseUtil.sendResponse(exchange, 404, "Caminhão não encontrado");
        }
    }
}
