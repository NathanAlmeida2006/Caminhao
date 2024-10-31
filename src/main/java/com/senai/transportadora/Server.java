package com.senai.transportadora;

import com.google.gson.Gson;
import com.senai.transportadora.controller.CaminhaoController;
import com.senai.transportadora.controller.FuncionarioController;
import com.senai.transportadora.controller.UsuarioController;
import com.senai.transportadora.handler.CaminhaoHandler;
import com.senai.transportadora.handler.FuncionarioHandler;
import com.senai.transportadora.handler.UsuarioHandler;
import com.senai.transportadora.service.CaminhaoService;
import com.senai.transportadora.service.FuncionarioService;
import com.senai.transportadora.service.UsuarioService;
import com.senai.transportadora.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.BasicAuthenticator;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Classe principal responsável pela inicialização do servidor HTTP para a aplicação de transportadora.
 * Define as portas, configura os endpoints e inicializa os serviços e controladores.
 * Implementa autenticação básica para rotas protegidas.
 */
public class Server {
    private static final int PORT = 8081;

    /**
     * Metodo principal que inicializa o servidor HTTP.
     * Configura os serviços, controladores, handlers e autenticação para as rotas.
     *
     * @param args Argumentos da linha de comando (não utilizados).
     * @throws IOException Se ocorrer um erro ao iniciar o servidor.
     */
    public static void main(String[] args) throws IOException {
        var gson = new Gson();
        var responseUtil = new HttpResponseUtil();

        // Inicialização dos serviços
        var caminhaoService = new CaminhaoService();
        var funcionarioService = new FuncionarioService();
        var usuarioService = new UsuarioService();

        // Inicialização dos controllers
        var caminhaoController = new CaminhaoController(caminhaoService);
        var funcionarioController = new FuncionarioController(funcionarioService);
        var usuarioController = new UsuarioController(usuarioService);

        // Inicialização dos handlers
        var caminhaoHandler = new CaminhaoHandler(caminhaoController, gson, responseUtil);
        var funcionarioHandler = new FuncionarioHandler(funcionarioController, gson, responseUtil);
        var usuarioHandler = new UsuarioHandler(usuarioController, gson, responseUtil);

        // Criação do servidor
        var server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // Configuração do autenticador para rotas protegidas
        Authenticator authenticator = new BasicAuthenticator("transportadora") {
            @Override
            public boolean checkCredentials(String username, String password) {
                return usuarioController.autenticar(username, password);
            }
        };

        // Configuração dos endpoints públicos
        server.createContext("/api/usuarios/login", usuarioHandler);
        server.createContext("/api/usuarios/register", usuarioHandler);

        // Configuração dos endpoints protegidos com autenticação
        HttpContext caminhoesContext = server.createContext("/api/caminhoes", caminhaoHandler);
        HttpContext funcionariosContext = server.createContext("/api/funcionarios", funcionarioHandler);
        HttpContext usuariosContext = server.createContext("/api/usuarios", usuarioHandler);

        caminhoesContext.setAuthenticator(authenticator);
        funcionariosContext.setAuthenticator(authenticator);
        usuariosContext.setAuthenticator(authenticator);

        server.setExecutor(null); // Usa o executor padrão
        server.start();

        // Mensagens de inicialização e URLs disponíveis
        System.out.println("Servidor iniciado na porta " + PORT);
        System.out.println("Endpoints disponíveis:");
        System.out.println("Endpoints públicos:");
        System.out.println("- Login: http://localhost:" + PORT + "/api/usuarios/login");
        System.out.println("- Registro: http://localhost:" + PORT + "/api/usuarios/register");
        System.out.println("\nEndpoints protegidos (requerem autenticação):");
        System.out.println("- Caminhões: http://localhost:" + PORT + "/api/caminhoes");
        System.out.println("- Funcionários: http://localhost:" + PORT + "/api/funcionarios");
        System.out.println("- Usuários: http://localhost:" + PORT + "/api/usuarios");
    }
}