package com.example.petshop;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VacinaForm extends JFrame {
    private JTextField nomeField;
    private JTextArea descricaoField;
    private JFormattedTextField dataAplicacaoField;
    private JComboBox<String> petComboBox;
    private Map<String, Integer> petMap;

    public VacinaForm() {
        setTitle("Cadastrar Vacina");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        panel.add(nomeField);

        panel.add(new JLabel("Descrição:"));
        descricaoField = new JTextArea();
        panel.add(new JScrollPane(descricaoField));

        panel.add(new JLabel("Data de Aplicação (yyyy-MM-dd):"));
        try {
            MaskFormatter dateFormatter = new MaskFormatter("####-##-##");
            dateFormatter.setPlaceholderCharacter('_');
            dataAplicacaoField = new JFormattedTextField(dateFormatter);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        panel.add(dataAplicacaoField);

        panel.add(new JLabel("Pet:"));
        petComboBox = new JComboBox<>();
        petMap = new HashMap<>();
        loadPets();
        panel.add(petComboBox);

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> saveVacina());
        panel.add(saveButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    private void loadPets() {
        String sql = "SELECT id, nome FROM pet";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                petComboBox.addItem(nome);
                petMap.put(nome, id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveVacina() {
        Vacina vacina = new Vacina();
        vacina.setNome(nomeField.getText());
        vacina.setDescricao(descricaoField.getText());
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            vacina.setDataAplicacao(dateFormat.parse(dataAplicacaoField.getText()));
            vacina.setDataHoraCadastro(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Data de aplicação inválida!");
            return;
        }
        vacina.setPetId(petMap.get((String) petComboBox.getSelectedItem()));

        VacinaDAO dao = new VacinaDAO();
        dao.save(vacina);

        JOptionPane.showMessageDialog(this, "Vacina salva com sucesso!");
        dispose();
    }
}
