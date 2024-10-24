package com.senai.transportadora.controller;

import com.senai.transportadora.entity.Funcionario;
import com.senai.transportadora.service.FuncionarioService;

import java.util.List;

public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    public void adicionarFuncionario(Funcionario funcionario) {
        funcionarioService.adicionarFuncionario(funcionario);
    }

    public List<Funcionario> listarFuncionarios() {
        return funcionarioService.listarFuncionarios();
    }

    public Funcionario obterFuncionarioPorId(int id) {
        Funcionario funcionario = funcionarioService.obterFuncionarioPorId(id);
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário com ID " + id + " não encontrado.");
        }
        return funcionario;
    }

    public boolean atualizarFuncionario(int id, Funcionario funcionarioAtualizado) {
        return funcionarioService.atualizarFuncionario(id, funcionarioAtualizado);
    }

    public boolean removerFuncionario(int id) {
        return funcionarioService.removerFuncionario(id);
    }
}
