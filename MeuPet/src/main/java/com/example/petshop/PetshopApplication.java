package com.example.petshop;

import javax.swing.*;
import java.awt.*;

public class PetshopApplication {
    public static void start() {

        // Configuração da janela principal
        JFrame frame = new JFrame("Petshop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Botões para abrir os formulários
        JButton clienteButton = new JButton("Cadastrar Cliente");
        clienteButton.addActionListener(e -> new ClienteForm().setVisible(true));

        JButton petButton = new JButton("Cadastrar Pet");
        petButton.addActionListener(e -> new PetForm().setVisible(true));

        JButton vacinaButton = new JButton("Cadastrar Vacina");
        vacinaButton.addActionListener(e -> new VacinaForm().setVisible(true));

        JButton listarDadosButton = new JButton("Listar Dados");
        listarDadosButton.addActionListener(e -> new ListarDadosForm().setVisible(true));

        JButton atualizarDadosButton = new JButton("Atualizar Dados");
        atualizarDadosButton.addActionListener(e -> new AtualizarDadosForm().setVisible(true));

        JButton dadosPetButton = new JButton("Historico dos pets");
        dadosPetButton.addActionListener(e -> new DadosPetForm().setVisible(true));
        

        // Painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.add(clienteButton);
        panel.add(petButton);
        panel.add(vacinaButton);
        panel.add(listarDadosButton);
        panel.add(atualizarDadosButton);
        panel.add(dadosPetButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
