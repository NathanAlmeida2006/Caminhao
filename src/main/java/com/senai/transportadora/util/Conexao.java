package com.senai.transportadora.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitária para gerenciamento de conexão com o banco de dados.
 * <p>
 * Esta classe fornece um metodo estático para estabelecer uma conexão com o banco de dados MySQL,
 * utilizando as configurações fornecidas para URL, usuário e senha.
 * </p>
 */
public class Conexao {

    /**
     * URL do banco de dados para conexão.
     */
    private static final String URL = "jdbc:mysql://localhost:3306/transportadora";

    /**
     * Nome de usuário para conexão com o banco de dados.
     */
    private static final String USER = "root";

    /**
     * Senha para conexão com o banco de dados.
     */
    private static final String PASSWORD = "";

    /**
     * Estabelece uma conexão com o banco de dados MySQL.
     *
     * @return Objeto {@link Connection} que representa a conexão com o banco de dados.
     * @throws SQLException Se ocorrer um erro ao tentar estabelecer a conexão.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
