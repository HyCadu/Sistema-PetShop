package com.example.petshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {

    public void save(Cliente cliente) throws Exception {
        if (exists(cliente.getCpf())) {
            throw new Exception("Cliente com CPF já cadastrado!");
        }

        String sql = "INSERT INTO cliente(nome, sexo, cpf, email, celular, endereco) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getSexo());
            pstmt.setString(3, cliente.getCpf());
            pstmt.setString(4, cliente.getEmail());
            pstmt.setString(5, cliente.getCelular());
            pstmt.setString(6, cliente.getEndereco());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(String cpf) {
        String sql = "SELECT count(*) FROM cliente WHERE cpf = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cpf);
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
