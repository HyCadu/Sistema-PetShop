package com.example.petshop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DadosPetForm extends JFrame {
    private JComboBox<String> petComboBox;
    private JTextField pesoField;
    private JTextField alturaField;
    private JTable historicoTable;

    public DadosPetForm() {
        setTitle("Dados do Pet");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("Selecione o Pet:"));
        petComboBox = new JComboBox<>();
        carregarPets();
        panel.add(petComboBox);

        panel.add(new JLabel("Peso:"));
        pesoField = new JTextField();
        panel.add(pesoField);

        panel.add(new JLabel("Altura:"));
        alturaField = new JTextField();
        panel.add(alturaField);

        JButton adicionarButton = new JButton("Adicionar Dados");
        adicionarButton.addActionListener(e -> adicionarDados());
        panel.add(adicionarButton);

        historicoTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(historicoTable);
        panel.add(scrollPane);

        add(panel);
        setLocationRelativeTo(null);
    }

    private void carregarPets() {
        String sql = "SELECT id, nome FROM pet";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                petComboBox.addItem(nome + " (ID: " + id + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar pets.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarDados() {
        String selectedPet = (String) petComboBox.getSelectedItem();
        if (selectedPet != null) {
            int petId = Integer.parseInt(selectedPet.split("ID: ")[1].replace(")", ""));
            double peso = Double.parseDouble(pesoField.getText());
            double altura = Double.parseDouble(alturaField.getText());
            LocalDate dataInsercao = LocalDate.now();

            String sql = "INSERT INTO dados_pet (pet_id, peso, altura, data_insercao) VALUES (?, ?, ?, ?)";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, petId);
                stmt.setDouble(2, peso);
                stmt.setDouble(3, altura);
                stmt.setString(4, dataInsercao.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Dados adicionados com sucesso!");
                carregarHistorico(petId);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao adicionar dados do pet.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarHistorico(int petId) {
        String sql = "SELECT peso, altura, data_insercao FROM dados_pet WHERE pet_id = ?";
        List<Object[]> historico = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, petId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    double peso = rs.getDouble("peso");
                    double altura = rs.getDouble("altura");
                    LocalDate dataInsercao = LocalDate.parse(rs.getString("data_insercao"));
                    Object[] row = {peso, altura, dataInsercao};
                    historico.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar histórico do pet.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        // Atualiza a tabela com o histórico
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Peso");
        model.addColumn("Altura");
        model.addColumn("Data de Inserção");

        for (Object[] row : historico) {
            model.addRow(row);
        }

        historicoTable.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Database.initialize(); // Inicializa o banco de dados
            DadosPetForm dadosPetForm = new DadosPetForm();
            dadosPetForm.setVisible(true);
        });
    }
}
