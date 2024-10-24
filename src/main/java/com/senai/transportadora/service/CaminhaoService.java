package com.senai.transportadora.service;

import com.senai.transportadora.entity.Caminhao;
import com.senai.transportadora.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CaminhaoService {

    private static final Logger LOGGER = Logger.getLogger(CaminhaoService.class.getName());

    public void adicionarCaminhao(Caminhao caminhao) {
        String sql = "INSERT INTO Caminhao (placa, modelo, cor, fabricante, numeroChassis, capacidadeCarga) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            preencherStatement(stmt, caminhao);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao adicionar caminhão", e);
        }
    }

    public List<Caminhao> listarCaminhoes() {
        List<Caminhao> caminhoes = new ArrayList<>();
        String sql = "SELECT * FROM Caminhao";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                caminhoes.add(mapearCaminhao(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao listar caminhões", e);
        }
        return caminhoes;
    }

    public Caminhao obterCaminhaoPorId(int id) {
        String sql = "SELECT * FROM Caminhao WHERE id = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCaminhao(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao obter caminhão com ID: " + id, e);
        }
        return null;
    }

    public boolean atualizarCaminhao(int id, Caminhao caminhaoAtualizado) {
        String sql = "UPDATE Caminhao SET placa = ?, modelo = ?, cor = ?, fabricante = ?, numeroChassis = ?, capacidadeCarga = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            preencherStatement(stmt, caminhaoAtualizado);
            stmt.setInt(7, id);  // ID na última posição
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao atualizar caminhão com ID: " + id, e);
        }
        return false;
    }

    public boolean removerCaminhao(int id) {
        String sql = "DELETE FROM Caminhao WHERE id = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao remover caminhão com ID: " + id, e);
        }
        return false;
    }

    private Caminhao mapearCaminhao(ResultSet rs) throws SQLException {
        return new Caminhao(
                rs.getInt("id"),
                rs.getString("placa"),
                rs.getString("modelo"),
                rs.getString("cor"),
                rs.getString("fabricante"),
                rs.getString("numeroChassis"),
                rs.getDouble("capacidadeCarga")
        );
    }

    private void preencherStatement(PreparedStatement stmt, Caminhao caminhao) throws SQLException {
        stmt.setString(1, caminhao.placa());
        stmt.setString(2, caminhao.modelo());
        stmt.setString(3, caminhao.cor());
        stmt.setString(4, caminhao.fabricante());
        stmt.setString(5, caminhao.numeroChassis());
        stmt.setDouble(6, caminhao.capacidadeCarga());
    }
}
