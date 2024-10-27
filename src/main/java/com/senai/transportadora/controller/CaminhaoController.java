package com.senai.transportadora.controller;

import com.senai.transportadora.entity.Caminhao;
import com.senai.transportadora.service.CaminhaoService;

import java.util.List;

/**
 * Controlador para gerenciar operações relacionadas a caminhões.
 * <p>
 * Esta classe fornece métodos para adicionar, listar, atualizar e remover caminhões
 * utilizando o serviço {@link CaminhaoService}.
 * </p>
 */
public class CaminhaoController {

    private final CaminhaoService caminhaoService;

    /**
     * Construtor para inicializar o controlador com o serviço de caminhão.
     *
     * @param caminhaoService o serviço de caminhão a ser utilizado
     */
    public CaminhaoController(CaminhaoService caminhaoService) {
        this.caminhaoService = caminhaoService;
    }

    /**
     * Adiciona um novo caminhão.
     *
     * @param caminhao o caminhão a ser adicionado
     */
    public void adicionarCaminhao(Caminhao caminhao) {
        caminhaoService.adicionarCaminhao(caminhao);
    }

    /**
     * Lista todos os caminhões disponíveis.
     *
     * @return uma lista de caminhões
     */
    public List<Caminhao> listarCaminhoes() {
        return caminhaoService.listarCaminhoes();
    }

    /**
     * Atualiza as informações de um caminhão existente.
     *
     * @param id                 o identificador do caminhão a ser atualizado
     * @param caminhaoAtualizado os novos dados do caminhão
     * @return {@code true} se a atualização foi bem-sucedida, caso contrário {@code false}
     */
    public boolean atualizarCaminhao(int id, Caminhao caminhaoAtualizado) {
        return caminhaoService.atualizarCaminhao(id, caminhaoAtualizado);
    }

    /**
     * Remove um caminhão pelo seu identificador.
     *
     * @param id o identificador do caminhão a ser removido
     * @return {@code true} se a remoção foi bem-sucedida, caso contrário {@code false}
     */
    public boolean removerCaminhao(int id) {
        return caminhaoService.removerCaminhao(id);
    }
}