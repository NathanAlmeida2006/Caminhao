package com.senai.transportadora;

import com.senai.transportadora.controller.CaminhaoController;
import com.senai.transportadora.service.CaminhaoService;
import com.senai.transportadora.view.CaminhaoView;

public class Main {
    public static void main(String[] args) {
        CaminhaoService caminhaoService = new CaminhaoService();
        CaminhaoController caminhaoController = new CaminhaoController(caminhaoService);
        CaminhaoView caminhaoView = new CaminhaoView(caminhaoController);
        caminhaoView.exibirMenu();
    }
}
