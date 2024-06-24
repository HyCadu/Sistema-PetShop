package com.example.petshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PetDAO {

    public void save(Pet pet) {
        String sql = "INSERT INTO pet(nome, proprietario_id, peso, idade, sexo, especie, raca) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pet.getNome());
            pstmt.setInt(2, pet.getProprietarioId());
            pstmt.setDouble(3, pet.getPeso());
            pstmt.setInt(4, pet.getIdade());
            pstmt.setString(5, pet.getSexo());
            pstmt.setString(6, pet.getEspecie());
            pstmt.setString(7, pet.getRaca());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
