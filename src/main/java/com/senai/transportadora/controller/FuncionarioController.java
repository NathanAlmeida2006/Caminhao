package com.senai.transportadora.controller;

import com.senai.transportadora.entity.Funcionario;
import com.senai.transportadora.service.FuncionarioService;

import java.util.List;

/**
 * Controlador para gerenciar operações relacionadas a funcionários.
 * <p>
 * Esta classe fornece métodos para adicionar, listar, atualizar e remover funcionários
 * utilizando o serviço {@link FuncionarioService}.
 * </p>
 */
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    /**
     * Construtor para inicializar o controlador com o serviço de funcionário.
     *
     * @param funcionarioService o serviço de funcionário a ser utilizado
     */
    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    /**
     * Adiciona um novo funcionário.
     *
     * @param funcionario o funcionário a ser adicionado
     */
    public void adicionarFuncionario(Funcionario funcionario) {
        funcionarioService.adicionarFuncionario(funcionario);
    }

    /**
     * Lista todos os funcionários disponíveis.
     *
     * @return uma lista de funcionários
     */
    public List<Funcionario> listarFuncionarios() {
        return funcionarioService.listarFuncionarios();
    }

    /**
     * Atualiza as informações de um funcionário existente.
     *
     * @param id                    o identificador do funcionário a ser atualizado
     * @param funcionarioAtualizado os novos dados do funcionário
     * @return {@code true} se a atualização foi bem-sucedida, caso contrário {@code false}
     */
    public boolean atualizarFuncionario(int id, Funcionario funcionarioAtualizado) {
        return funcionarioService.atualizarFuncionario(id, funcionarioAtualizado);
    }

    /**
     * Remove um funcionário pelo seu identificador.
     *
     * @param id o identificador do funcionário a ser removido
     * @return {@code true} se a remoção foi bem-sucedida, caso contrário {@code false}
     */
    public boolean removerFuncionario(int id) {
        return funcionarioService.removerFuncionario(id);
    }
}