package com.senai.transportadora;

import com.google.gson.Gson;
import com.senai.transportadora.controller.CaminhaoController;
import com.senai.transportadora.handler.CaminhaoHandler;
import com.senai.transportadora.service.CaminhaoService;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Servidor HTTP para gerenciar operações de caminhões.
 * <p>
 * Esta classe configura e inicia um servidor HTTP na porta especificada,
 * utilizando os manipuladores de requisições necessários.
 * </p>
 */
public class Server {
    private static final int PORT = 8081;

    /**
     * Metodo principal para iniciar o servidor.
     *
     * @param args argumentos da linha de comando
     * @throws IOException se ocorrer um erro de entrada/saída
     */
    public static void main(String[] args) throws IOException {
        var gson = new Gson();
        var responseUtil = new HttpResponseUtil();
        var service = new CaminhaoService();
        var controller = new CaminhaoController(service);
        var handler = new CaminhaoHandler(controller, gson, responseUtil);

        var server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/api", handler);
        server.setExecutor(null);
        server.start();

        System.out.println("Server started on port " + PORT);
    }
}