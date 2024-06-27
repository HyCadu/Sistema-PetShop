package com.example.petshop;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AtualizarDadosForm extends JFrame {
    private JComboBox<String> clienteComboBox;
    private JComboBox<String> petComboBox;
    private JTextField clienteNomeField;
    private JTextField clienteSexoField;
    private JTextField clienteCpfField;
    private JTextField clienteEmailField;
    private JTextField clienteCelularField;
    private JTextField clienteEnderecoField;
    private JTextField petNomeField;
    private JTextField petEspecieField;
    private JTextField petRacaField;
    private JTextField petIdadeField;
    private JTextField petPesoField; // Novo campo para o peso do pet
    private int clienteId;
    private int petId;

    public AtualizarDadosForm() {
        setTitle("Atualizar Dados");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(15, 20)); // Aumentamos para 17 linhas para acomodar o novo campo

        // Seção Cliente
        panel.add(new JLabel("Selecione o Cliente:"));
        clienteComboBox = new JComboBox<>();
        carregarClientes();
        clienteComboBox.addActionListener(e -> carregarDadosCliente());
        panel.add(clienteComboBox);

        panel.add(new JLabel("Nome do Cliente:"));
        clienteNomeField = new JTextField();
        panel.add(clienteNomeField);

        panel.add(new JLabel("Sexo do Cliente:"));
        clienteSexoField = new JTextField();
        panel.add(clienteSexoField);

        panel.add(new JLabel("CPF do Cliente:"));
        clienteCpfField = new JTextField();
        panel.add(clienteCpfField);

        panel.add(new JLabel("E-mail do Cliente:"));
        clienteEmailField = new JTextField();
        panel.add(clienteEmailField);

        panel.add(new JLabel("Celular do Cliente:"));
        clienteCelularField = new JTextField();
        panel.add(clienteCelularField);

        panel.add(new JLabel("Endereço do Cliente:"));
        clienteEnderecoField = new JTextField();
        panel.add(clienteEnderecoField);

        

        // Seção Pet
        panel.add(new JLabel("Selecione o Pet:"));
        petComboBox = new JComboBox<>();
        carregarPets();
        petComboBox.addActionListener(e -> carregarDadosPet());
        panel.add(petComboBox);

        panel.add(new JLabel("Nome do Pet:"));
        petNomeField = new JTextField();
        panel.add(petNomeField);

        panel.add(new JLabel("Espécie do Pet:"));
        petEspecieField = new JTextField();
        panel.add(petEspecieField);

        panel.add(new JLabel("Raça do Pet:"));
        petRacaField = new JTextField();
        panel.add(petRacaField);

        panel.add(new JLabel("Idade do Pet:"));
        petIdadeField = new JTextField();
        panel.add(petIdadeField);

        panel.add(new JLabel("Peso do Pet:")); // Novo campo para o peso do pet
        petPesoField = new JTextField();
        panel.add(petPesoField);

        // Botão para excluir pet
        JButton excluirPetButton = new JButton("Excluir Pet");
        excluirPetButton.addActionListener(e -> excluirPet());
        panel.add(excluirPetButton);

        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(e -> atualizarDados());
        panel.add(atualizarButton);

        // Botão para excluir cliente
        JButton excluirClienteButton = new JButton("Excluir Cliente");
        excluirClienteButton.addActionListener(e -> excluirCliente());
        
        panel.add(excluirClienteButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    private void carregarClientes() {
        String sql = "SELECT id, nome FROM cliente";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            clienteComboBox.removeAllItems(); // Limpa itens antigos
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                clienteComboBox.addItem(nome + " (ID: " + id + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarPets() {
        String sql = "SELECT id, nome FROM pet";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            petComboBox.removeAllItems(); // Limpa itens antigos
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

    private void carregarDadosCliente() {
        String selectedCliente = (String) clienteComboBox.getSelectedItem();
        if (selectedCliente != null) {
            clienteId = Integer.parseInt(selectedCliente.split("ID: ")[1].replace(")", ""));
            String sql = "SELECT nome, sexo, cpf, email, celular, endereco FROM cliente WHERE id = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, clienteId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        clienteNomeField.setText(rs.getString("nome"));
                        clienteSexoField.setText(rs.getString("sexo"));
                        clienteCpfField.setText(rs.getString("cpf"));
                        clienteEmailField.setText(rs.getString("email"));
                        clienteCelularField.setText(rs.getString("celular"));
                        clienteEnderecoField.setText(rs.getString("endereco"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao carregar dados do cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarDadosPet() {
        String selectedPet = (String) petComboBox.getSelectedItem();
        if (selectedPet != null) {
            petId = Integer.parseInt(selectedPet.split("ID: ")[1].replace(")", ""));
            String sql = "SELECT nome, especie, raca, idade, peso FROM pet WHERE id = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, petId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        petNomeField.setText(rs.getString("nome"));
                        petEspecieField.setText(rs.getString("especie"));
                        petRacaField.setText(rs.getString("raca"));
                        petIdadeField.setText(rs.getString("idade"));
                        petPesoField.setText(rs.getString("peso")); // Preenche o campo de peso do pet
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao carregar dados do pet.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void excluirCliente() {
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este cliente?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM cliente WHERE id = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, clienteId);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
                    carregarClientes(); // Atualiza a lista de clientes após a exclusão
                    limparCamposCliente();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao excluir cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void excluirPet() {
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este pet?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM pet WHERE id = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, petId);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Pet excluído com sucesso!");
                    carregarPets(); // Atualiza a lista de pets após a exclusão
                    limparCamposPet();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao excluir pet.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void atualizarDados() {
        String sqlCliente = "UPDATE cliente SET nome = ?, sexo = ?, cpf = ?, email = ?, celular = ?, endereco = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente)) {
                stmtCliente.setString(1, clienteNomeField.getText());
                stmtCliente.setString(2,clienteSexoField.getText());
                stmtCliente.setString(3, clienteCpfField.getText());
                stmtCliente.setString(4, clienteEmailField.getText());
                stmtCliente.setString(5, clienteCelularField.getText());
                stmtCliente.setString(6, clienteEnderecoField.getText());
                stmtCliente.setInt(7, clienteId);
                stmtCliente.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao atualizar dados do cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
    
            String sqlPet = "UPDATE pet SET nome = ?, especie = ?, raca = ?, idade = ?, peso = ? WHERE id = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmtPet = conn.prepareStatement(sqlPet)) {
                stmtPet.setString(1, petNomeField.getText());
                stmtPet.setString(2, petEspecieField.getText());
                stmtPet.setString(3, petRacaField.getText());
                stmtPet.setString(4, petIdadeField.getText());
                stmtPet.setString(5, petPesoField.getText()); // Atualiza o campo de peso do pet
                stmtPet.setInt(6, petId);
                stmtPet.executeUpdate();
                JOptionPane.showMessageDialog(this, "Dados atualizados com sucesso!");
                dispose();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao atualizar dados do pet.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    
        private void limparCamposCliente() {
            clienteNomeField.setText("");
            clienteSexoField.setText("");
            clienteCpfField.setText("");
            clienteEmailField.setText("");
            clienteCelularField.setText("");
            clienteEnderecoField.setText("");
        }
    
        private void limparCamposPet() {
            petNomeField.setText("");
            petEspecieField.setText("");
            petRacaField.setText("");
            petIdadeField.setText("");
            petPesoField.setText("");
        }
    }
    
