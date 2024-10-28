package com.senai.transportadora;

import com.google.gson.Gson;
import com.senai.transportadora.controller.CaminhaoController;
import com.senai.transportadora.controller.FuncionarioController;
import com.senai.transportadora.handler.CaminhaoHandler;
import com.senai.transportadora.handler.FuncionarioHandler;
import com.senai.transportadora.service.CaminhaoService;
import com.senai.transportadora.service.FuncionarioService;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Classe principal responsável pela inicialização do servidor HTTP para a aplicação de transportadora.
 * Define as portas, configura os endpoints e inicializa os serviços e controladores de caminhões e funcionários.
 */
public class Server {
    private static final int PORT = 8081;

    /**
     * Metodo principal que inicializa o servidor HTTP.
     * Configura os serviços, controladores e handlers para as rotas de caminhões e funcionários.
     *
     * @param args Argumentos da linha de comando (não utilizados).
     * @throws IOException Se ocorrer um erro ao iniciar o servidor.
     */
    public static void main(String[] args) throws IOException {
        var gson = new Gson();
        var responseUtil = new HttpResponseUtil();

        var caminhaoService = new CaminhaoService();
        var caminhaoController = new CaminhaoController(caminhaoService);
        var caminhaoHandler = new CaminhaoHandler(caminhaoController, gson, responseUtil);

        var funcionarioService = new FuncionarioService();
        var funcionarioController = new FuncionarioController(funcionarioService);
        var funcionarioHandler = new FuncionarioHandler(funcionarioController, gson, responseUtil);

        var server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // Configura o contexto dos endpoints
        server.createContext("/api/caminhoes", caminhaoHandler);
        server.createContext("/api/funcionarios", funcionarioHandler);

        server.setExecutor(null); // Usa o executor padrão
        server.start();

        // Mensagens de inicialização e URLs disponíveis
        System.out.println("Servidor iniciado na porta " + PORT);
        System.out.println("Endpoints disponíveis:");
        System.out.println("- Caminhões: http://localhost:" + PORT + "/api/caminhoes");
        System.out.println("- Funcionários: http://localhost:" + PORT + "/api/funcionarios");
    }
}
