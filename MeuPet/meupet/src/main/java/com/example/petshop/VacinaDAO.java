package com.example.petshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class VacinaDAO {

    public void save(Vacina vacina) {
        String sql = "INSERT INTO vacina(nome, descricao, data_aplicacao, data_hora_cadastro, pet_id) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, vacina.getNome());
            pstmt.setString(2, vacina.getDescricao());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            pstmt.setString(3, dateFormat.format(vacina.getDataAplicacao()));
            pstmt.setString(4, dateTimeFormat.format(vacina.getDataHoraCadastro()));
            pstmt.setInt(5, vacina.getPetId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
