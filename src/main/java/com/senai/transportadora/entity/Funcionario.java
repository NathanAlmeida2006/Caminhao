package com.senai.transportadora.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Funcionario(
        int id,
        String nome,
        String cpf,
        String rg,
        LocalDate dataNascimento,
        String cargo,
        String email,
        String telefone,
        LocalDateTime dataCadastro
) {

    @Override
    public String toString() {
        return "-------------------------------------\n" +
                "ID: " + id + "\n" +
                "Nome: " + nome + "\n" +
                "CPF: " + cpf + "\n" +
                "RG: " + rg + "\n" +
                "Data de Nascimento: " + dataNascimento + "\n" +
                "Cargo: " + cargo + "\n" +
                "Email: " + email + "\n" +
                "Telefone: " + telefone + "\n" +
                "Data de Cadastro: " + dataCadastro + "\n" +
                "-------------------------------------";
    }
}
