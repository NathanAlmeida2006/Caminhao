package com.senai.transportadora.service;

import com.senai.transportadora.entity.Funcionario;
import com.senai.transportadora.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FuncionarioService {
    private static final Logger LOGGER = Logger.getLogger(FuncionarioService.class.getName());

    public void adicionarFuncionario(Funcionario funcionario) {
        String sql = "INSERT INTO funcionarios (nome, cpf, rg, data_nascimento, cargo, email, telefone) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            preencherStatement(stmt, funcionario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao adicionar funcionário", e);
        }
    }

    public List<Funcionario> listarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                funcionarios.add(mapearFuncionario(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao listar funcionários", e);
        }
        return funcionarios;
    }

    public Funcionario obterFuncionarioPorId(int id) {
        String sql = "SELECT * FROM funcionarios WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearFuncionario(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao obter funcionário com ID: " + id, e);
        }
        return null;
    }

    public boolean atualizarFuncionario(int id, Funcionario funcionarioAtualizado) {
        String sql = "UPDATE funcionarios SET nome = ?, cpf = ?, rg = ?, data_nascimento = ?, " +
                "cargo = ?, email = ?, telefone = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            preencherStatement(stmt, funcionarioAtualizado);
            stmt.setInt(8, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao atualizar funcionário com ID: " + id, e);
        }
        return false;
    }

    public boolean removerFuncionario(int id) {
        String sql = "DELETE FROM funcionarios WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao remover funcionário com ID: " + id, e);
        }
        return false;
    }

    private Funcionario mapearFuncionario(ResultSet rs) throws SQLException {
        return new Funcionario(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("rg"),
                rs.getDate("data_nascimento").toLocalDate(),
                rs.getString("cargo"),
                rs.getString("email"),
                rs.getString("telefone"),
                rs.getTimestamp("data_cadastro").toLocalDateTime()
        );
    }

    private void preencherStatement(PreparedStatement stmt, Funcionario funcionario) throws SQLException {
        stmt.setString(1, funcionario.nome());
        stmt.setString(2, funcionario.cpf());
        stmt.setString(3, funcionario.rg());
        stmt.setDate(4, Date.valueOf(funcionario.dataNascimento()));
        stmt.setString(5, funcionario.cargo());
        stmt.setString(6, funcionario.email());
        stmt.setString(7, funcionario.telefone());
    }
}
