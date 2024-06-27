package com.example.petshop;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class PetForm extends JFrame {
    private JTextField nomeField;
    private JComboBox<String> proprietarioComboBox;
    private JTextField pesoField;
    private JTextField idadeField;
    private JComboBox<String> sexoField;
    private JTextField especieField;
    private JTextField racaField;
    private Map<String, Integer> proprietarioMap;

    public PetForm() {
        setTitle("Cadastrar Pet");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(8, 2));

        panel.add(new JLabel("Nome do Animal:"));
        nomeField = new JTextField();
        panel.add(nomeField);

        panel.add(new JLabel("Proprietário:"));
        proprietarioComboBox = new JComboBox<>();
        proprietarioMap = new HashMap<>();
        loadProprietarios();
        panel.add(proprietarioComboBox);

        panel.add(new JLabel("Peso:"));
        pesoField = new JTextField();
        panel.add(pesoField);

        panel.add(new JLabel("Idade:"));
        idadeField = new JTextField();
        panel.add(idadeField);

        panel.add(new JLabel("Sexo (M/F):"));
        sexoField = new JComboBox<>(new String[]{"M", "F"});
        panel.add(sexoField);

        panel.add(new JLabel("Espécie:"));
        especieField = new JTextField();
        panel.add(especieField);

        panel.add(new JLabel("Raça:"));
        racaField = new JTextField();
        panel.add(racaField);

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> savePet());
        panel.add(saveButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    private void loadProprietarios() {
        String sql = "SELECT id, nome FROM cliente";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                proprietarioComboBox.addItem(nome);
                proprietarioMap.put(nome, id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void savePet() {
        Pet pet = new Pet();
        pet.setNome(nomeField.getText());
        pet.setProprietarioId(proprietarioMap.get((String) proprietarioComboBox.getSelectedItem()));
        pet.setPeso(Double.parseDouble(pesoField.getText()));
        pet.setIdade(Integer.parseInt(idadeField.getText()));
        pet.setSexo((String) sexoField.getSelectedItem());
        pet.setEspecie(especieField.getText());
        pet.setRaca(racaField.getText());

        PetDAO dao = new PetDAO();
        dao.save(pet);

        JOptionPane.showMessageDialog(this, "Pet salvo com sucesso!");
        dispose();
    }
}
