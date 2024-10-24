package com.senai.transportadora;

import com.senai.transportadora.controller.CaminhaoController;
import com.senai.transportadora.controller.FuncionarioController;
import com.senai.transportadora.service.CaminhaoService;
import com.senai.transportadora.service.FuncionarioService;
import com.senai.transportadora.view.MenuView;

public class Main {
    public static void main(String[] args) {
        CaminhaoService caminhaoService = new CaminhaoService();
        FuncionarioService funcionarioService = new FuncionarioService();

        CaminhaoController caminhaoController = new CaminhaoController(caminhaoService);
        FuncionarioController funcionarioController = new FuncionarioController(funcionarioService);

        MenuView menuView = new MenuView(caminhaoController, funcionarioController);
        menuView.exibirMenuPrincipal();
    }
}