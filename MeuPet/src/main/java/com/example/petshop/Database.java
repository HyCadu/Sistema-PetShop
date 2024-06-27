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
            // Criação da tabela cliente
            String createClienteTable = "CREATE TABLE IF NOT EXISTS cliente (" +
                                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                        "nome TEXT NOT NULL," +
                                        "sexo NOT NULL CHECK (sexo IN ('M', 'F'))," +
                                        "cpf TEXT NOT NULL UNIQUE," +
                                        "email TEXT NOT NULL UNIQUE," +
                                        "celular TEXT NOT NULL UNIQUE," +
                                        "endereco TEXT NOT NULL)";
            stmt.execute(createClienteTable);

            // Criação da tabela pet
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

            // Criação da tabela vacina
            String createVacinaTable = "CREATE TABLE IF NOT EXISTS vacina (" +
                                       "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                       "pet_id INTEGER NOT NULL," +
                                       "nome TEXT NOT NULL," +
                                       "descricao TEXT," +
                                       "data_aplicacao DATE NOT NULL," +
                                       "data_hora_cadastro DATETIME NOT NULL," +
                                       "FOREIGN KEY(pet_id) REFERENCES pet(id))";
            stmt.execute(createVacinaTable);

            // Criação da tabela usuario
            String createUsuarioTable = "CREATE TABLE IF NOT EXISTS usuario (" +
                                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                        "nome TEXT NOT NULL," +
                                        "sexo TEXT NOT NULL CHECK (sexo IN ('M', 'F'))," +
                                        "email TEXT NOT NULL UNIQUE," +
                                        "senha TEXT NOT NULL)";
            stmt.execute(createUsuarioTable);

            // Criação da tabela dados_pet
            String createDadosPetTable = "CREATE TABLE IF NOT EXISTS dados_pet (" +
                                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                         "pet_id INTEGER NOT NULL," +
                                         "peso DOUBLE NOT NULL," +
                                         "altura DOUBLE NOT NULL," +
                                         "data_insercao DATE NOT NULL," +
                                         "FOREIGN KEY(pet_id) REFERENCES pet(id))";
            stmt.execute(createDadosPetTable);
                // Inserção de usuário padrão para fins de teste (senha fixa '123')
            String insertUsuario = "INSERT OR IGNORE INTO usuario (nome, sexo, email, senha) " +
                                   "VALUES ('Admin', 'M', 'admin', '123')";
            stmt.execute(insertUsuario);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
