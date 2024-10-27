package com.senai.transportadora.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa um funcionário da transportadora.
 * <p>
 * Esta classe armazena informações sobre o funcionário, incluindo
 * seu nome, CPF, RG, data de nascimento, cargo, email, telefone e data de cadastro.
 * </p>
 *
 * @param id             Identificador único do funcionário.
 * @param nome           Nome completo do funcionário.
 * @param cpf            CPF do funcionário.
 * @param rg             RG do funcionário.
 * @param dataNascimento Data de nascimento do funcionário.
 * @param cargo          Cargo ocupado pelo funcionário.
 * @param email          Endereço de email do funcionário.
 * @param telefone       Número de telefone do funcionário.
 * @param dataCadastro   Data e hora em que o funcionário foi cadastrado no sistema.
 */
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
}
