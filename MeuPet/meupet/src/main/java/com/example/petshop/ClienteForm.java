package com.example.petshop;

import javax.swing.*;
import java.awt.*;

public class ClienteForm extends JFrame {
    private JTextField nomeField;
    private JComboBox<String> sexoField;
    private JTextField cpfField;
    private JTextField emailField;
    private JTextField celularField;
    private JTextField enderecoField;

    public ClienteForm() {
        setTitle("Cadastrar Cliente");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(7, 2));

        panel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        panel.add(nomeField);

        panel.add(new JLabel("Sexo (M/F):"));
        sexoField = new JComboBox<>(new String[]{"M", "F"});
        panel.add(sexoField);

        panel.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        panel.add(cpfField);

        panel.add(new JLabel("E-mail:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Celular:"));
        celularField = new JTextField();
        panel.add(celularField);

        panel.add(new JLabel("Endereço:"));
        enderecoField = new JTextField();
        panel.add(enderecoField);

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> saveCliente());
        panel.add(saveButton);

        add(panel);
    }

    private void saveCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome(nomeField.getText());
        cliente.setSexo((String) sexoField.getSelectedItem());
        cliente.setCpf(cpfField.getText());
        cliente.setEmail(emailField.getText());
        cliente.setCelular(celularField.getText());
        cliente.setEndereco(enderecoField.getText());

        ClienteDAO dao = new ClienteDAO();
        try {
            dao.save(cliente);
            JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
