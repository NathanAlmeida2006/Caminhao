package com.senai.transportadora.controller;

import com.senai.transportadora.entity.Caminhao;
import com.senai.transportadora.service.CaminhaoService;

import java.util.List;

public class CaminhaoController {

    private final CaminhaoService caminhaoService;

    public CaminhaoController(CaminhaoService caminhaoService) {
        this.caminhaoService = caminhaoService;
    }

    public void adicionarCaminhao(Caminhao caminhao) {
        caminhaoService.adicionarCaminhao(caminhao);
    }

    public List<Caminhao> listarCaminhoes() {
        return caminhaoService.listarCaminhoes();
    }

    public Caminhao obterCaminhaoPorId(int id) {
        Caminhao caminhao = caminhaoService.obterCaminhaoPorId(id);
        if (caminhao == null) {
            throw new IllegalArgumentException("Caminhão com ID " + id + " não encontrado.");
        }
        return caminhao;
    }

    public boolean atualizarCaminhao(int id, Caminhao caminhaoAtualizado) {
        return caminhaoService.atualizarCaminhao(id, caminhaoAtualizado);
    }

    public boolean removerCaminhao(int id) {
        return caminhaoService.removerCaminhao(id);
    }
}
