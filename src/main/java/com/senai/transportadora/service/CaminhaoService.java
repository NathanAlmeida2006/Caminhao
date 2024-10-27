package com.senai.transportadora.service;

import com.senai.transportadora.entity.Caminhao;
import com.senai.transportadora.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class CaminhaoService {

    /**
     * Logger para registrar mensagens e erros da classe CaminhaoService.
     */
    private static final Logger LOGGER = Logger.getLogger(CaminhaoService.class.getName());

    /**
     * Adiciona um novo caminhão ao banco de dados.
     *
     * @param caminhao Objeto {@link Caminhao} contendo os dados do caminhão a ser adicionado.
     */
    public void adicionarCaminhao(Caminhao caminhao) {
        String sql = "INSERT INTO Caminhao (placa, modelo, cor, fabricante, numeroChassis, capacidadeCarga) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            preencherStatement(stmt, caminhao);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao adicionar caminhão", e);
        }
    }

    /**
     * Retorna uma lista de todos os caminhões do banco de dados.
     *
     * @return Lista de objetos {@link Caminhao} representando os caminhões no banco de dados.
     */
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

    /**
     * Atualiza os dados de um caminhão no banco de dados.
     *
     * @param id                 ID do caminhão a ser atualizado.
     * @param caminhaoAtualizado Objeto {@link Caminhao} contendo os dados atualizados.
     * @return true se o caminhão foi atualizado com sucesso; false caso contrário.
     */
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

    /**
     * Remove um caminhão do banco de dados a partir de seu ID.
     *
     * @param id ID do caminhão a ser removido.
     * @return true se o caminhão foi removido com sucesso; false caso contrário.
     */
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

    /**
     * Mapeia os dados de um {@link ResultSet} para um objeto {@link Caminhao}.
     *
     * @param rs {@link ResultSet} contendo os dados de um caminhão.
     * @return Objeto {@link Caminhao} mapeado a partir do ResultSet.
     * @throws SQLException Se ocorrer um erro ao acessar os dados do ResultSet.
     */
    private Caminhao mapearCaminhao(ResultSet rs) throws SQLException {
        return new Caminhao(
                rs.getInt("id"),
                rs.getString("placa"),
                rs.getString("modelo"),
                rs.getString("cor"),
                rs.getString("fabricante"),
                rs.getString("numeroChassis"),
                rs.getDouble("capacidadeCarga"));
    }

    /**
     * Preenche um {@link PreparedStatement} com os dados de um caminhão.
     *
     * @param stmt     {@link PreparedStatement} a ser preenchido.
     * @param caminhao Objeto {@link Caminhao} com os dados a serem inseridos.
     * @throws SQLException Se ocorrer um erro ao preencher o PreparedStatement.
     */
    private void preencherStatement(PreparedStatement stmt, Caminhao caminhao) throws SQLException {
        stmt.setString(1, caminhao.placa());
        stmt.setString(2, caminhao.modelo());
        stmt.setString(3, caminhao.cor());
        stmt.setString(4, caminhao.fabricante());
        stmt.setString(5, caminhao.numeroChassis());
        stmt.setDouble(6, caminhao.capacidadeCarga());
    }
}
