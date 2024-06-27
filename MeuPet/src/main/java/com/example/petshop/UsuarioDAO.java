package com.example.petshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    private static final String URL = "jdbc:sqlite:petshop.db";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public void criarUsuario(String nome, String sexo, String email, String admcode, String senha) throws Exception {
        if (!admcode.equals("123")) {
            throw new Exception("Senha de administrador inválida para criar usuário.");
        }

        String sql = "INSERT INTO usuario (nome, sexo, email, senha) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, sexo);
            pstmt.setString(3, email);
            pstmt.setString(4, senha);
            pstmt.executeUpdate();
        }
    }

    public boolean verificarLogin(String username, String senha) {
        String sql = "SELECT count(*) FROM usuario WHERE email = ? AND senha = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, senha);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
