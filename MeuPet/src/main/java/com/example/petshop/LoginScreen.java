package com.example.petshop;

import javax.swing.*;
import java.awt.*;


public class LoginScreen extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    

    public LoginScreen() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        panel.add(new JLabel("Email:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> fazerLogin());
        panel.add(loginButton);

        JButton criarUsuarioButton = new JButton("Criar Usuário");
        criarUsuarioButton.addActionListener(e -> criarUsuario());
        panel.add(criarUsuarioButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    private void fazerLogin() {
        String username = usernameField.getText();
        String senha = new String(passwordField.getPassword());

        UsuarioDAO dao = new UsuarioDAO();
        if (dao.verificarLogin(username, senha)) {
            JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
            PetshopApplication.start();
            
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void criarUsuario() {
        String nome = JOptionPane.showInputDialog(this, "Nome:");
        String sexo = JOptionPane.showInputDialog(this, "Sexo:");
        String email = JOptionPane.showInputDialog(this, "Email:");
        String senha = JOptionPane.showInputDialog(this, "senha");
        String admcode = JOptionPane.showInputDialog(this, "AdminPass (123):");

        UsuarioDAO dao = new UsuarioDAO();
        try {
            dao.criarUsuario(nome, sexo, email, admcode, senha);;
            JOptionPane.showMessageDialog(this, "Usuário criado com sucesso!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Database.initialize();
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
    }
}
