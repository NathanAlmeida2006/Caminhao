/**
 * Classe responsável por gerenciar as operações de banco de dados relacionadas aos usuários.
 * Fornece métodos para criar, ler, atualizar e deletar registros de usuários,
 * além de funcionalidades de autenticação.
 */
package com.senai.transportadora.service;

import com.senai.transportadora.entity.Usuario;
import com.senai.transportadora.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioService {
    /**
     * Logger para registrar operações e erros da classe.
     */
    private static final Logger LOGGER = Logger.getLogger(UsuarioService.class.getName());

    /**
     * Adiciona um novo usuário ao banco de dados.
     *
     * @param usuario O objeto Usuario contendo os dados do novo usuário
     */
    public void adicionarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, email, senha, cargo) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            preencherStatement(stmt, usuario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao adicionar usuário", e);
        }
    }

    /**
     * Recupera todos os usuários do banco de dados.
     *
     * @return Lista contendo todos os usuários cadastrados
     */
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao listar usuários", e);
        }
        return usuarios;
    }

    /**
     * Atualiza os dados de um usuário existente.
     *
     * @param id                ID do usuário a ser atualizado
     * @param usuarioAtualizado Objeto Usuario contendo os novos dados
     * @return true se a atualização foi bem-sucedida, false caso contrário
     */
    public boolean atualizarUsuario(int id, Usuario usuarioAtualizado) {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ?, cargo = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            preencherStatement(stmt, usuarioAtualizado);
            stmt.setInt(5, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao atualizar usuário com ID: " + id, e);
        }
        return false;
    }

    /**
     * Remove um usuário do banco de dados.
     *
     * @param id ID do usuário a ser removido
     * @return true se a remoção foi bem-sucedida, false caso contrário
     */
    public boolean removerUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao remover usuário com ID: " + id, e);
        }
        return false;
    }

    /**
     * Verifica as credenciais do usuário para autenticação.
     *
     * @param email Email do usuário
     * @param senha Senha do usuário
     * @return true se as credenciais são válidas, false caso contrário
     */
    public boolean autenticar(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao autenticar usuário", e);
        }
        return false;
    }

    /**
     * Converte um ResultSet em um objeto Usuario.
     *
     * @param rs ResultSet contendo os dados do usuário
     * @return Objeto Usuario preenchido com os dados do ResultSet
     * @throws SQLException se houver erro ao acessar os dados do ResultSet
     */
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("senha"),
                rs.getString("cargo"));
    }

    /**
     * Preenche um PreparedStatement com os dados de um usuário.
     *
     * @param stmt    PreparedStatement a ser preenchido
     * @param usuario Objeto Usuario contendo os dados
     * @throws SQLException se houver erro ao definir os parâmetros
     */
    private void preencherStatement(PreparedStatement stmt, Usuario usuario) throws SQLException {
        stmt.setString(1, usuario.nome());
        stmt.setString(2, usuario.email());
        stmt.setString(3, usuario.senha());
        stmt.setString(4, usuario.cargo());
    }
}