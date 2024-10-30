package com.senai.transportadora.controller;

import com.senai.transportadora.entity.Usuario;
import com.senai.transportadora.service.UsuarioService;

import java.util.List;

/**
 * Controlador para gerenciar operações relacionadas a usuários.
 * <p>
 * Esta classe fornece métodos para adicionar, listar, atualizar, remover e autenticar usuários
 * utilizando o serviço {@link UsuarioService}.
 * </p>
 */
public class UsuarioController {
    private final UsuarioService service;

    /**
     * Construtor para inicializar o controlador com o serviço de usuário.
     *
     * @param service o serviço de usuário a ser utilizado
     */
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    /**
     * Lista todos os usuários disponíveis.
     *
     * @return uma lista de usuários
     */
    public List<Usuario> listarUsuarios() {
        return service.listarUsuarios();
    }

    /**
     * Adiciona um novo usuário.
     *
     * @param usuario o usuário a ser adicionado
     */
    public void adicionarUsuario(Usuario usuario) {
        service.adicionarUsuario(usuario);
    }

    /**
     * Atualiza as informações de um usuário existente.
     *
     * @param id      o identificador do usuário a ser atualizado
     * @param usuario os novos dados do usuário
     * @return {@code true} se a atualização foi bem-sucedida, caso contrário {@code false}
     */
    public boolean atualizarUsuario(Integer id, Usuario usuario) {
        return service.atualizarUsuario(id, usuario);
    }

    /**
     * Remove um usuário pelo seu identificador.
     *
     * @param id o identificador do usuário a ser removido
     * @return {@code true} se a remoção foi bem-sucedida, caso contrário {@code false}
     */
    public boolean removerUsuario(Integer id) {
        return service.removerUsuario(id);
    }

    /**
     * Autentica um usuário com base em seu email e senha.
     *
     * @param email o email do usuário
     * @param senha a senha do usuário
     * @return {@code true} se a autenticação foi bem-sucedida, caso contrário {@code false}
     */
    public boolean autenticar(String email, String senha) {
        return service.autenticar(email, senha);
    }
}
