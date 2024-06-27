package com.example.petshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:petshop.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initialize() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String createClienteTable = "CREATE TABLE IF NOT EXISTS cliente (" +
                                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                        "nome TEXT NOT NULL," +
                                        "sexo TEXT NOT NULL CHECK (sexo IN ('M', 'F'))," +
                                        "cpf TEXT NOT NULL UNIQUE," +
                                        "email TEXT NOT NULL UNIQUE," +
                                        "celular TEXT NOT NULL UNIQUE," +
                                        "endereco TEXT NOT NULL)";
            stmt.execute(createClienteTable);

            String createPetTable = "CREATE TABLE IF NOT EXISTS pet (" +
                                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    "nome TEXT NOT NULL," +
                                    "proprietario_id INTEGER NOT NULL," +
                                    "peso DOUBLE NOT NULL," +
                                    "idade INTEGER NOT NULL," +
                                    "sexo TEXT NOT NULL CHECK (sexo IN ('M', 'F'))," +
                                    "especie TEXT NOT NULL," +
                                    "raca TEXT NOT NULL," +
                                    "FOREIGN KEY(proprietario_id) REFERENCES cliente(id))";
            stmt.execute(createPetTable);

            String createVacinaTable = "CREATE TABLE IF NOT EXISTS vacina (" +
                                       "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                       "pet_id INTEGER NOT NULL," +
                                       "nome TEXT NOT NULL," +
                                       "descricao TEXT," +
                                       "data_aplicacao DATE NOT NULL," +
                                       "data_hora_cadastro DATETIME NOT NULL," +
                                       "FOREIGN KEY(pet_id) REFERENCES pet(id))";
            stmt.execute(createVacinaTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
