package com.example.petshop;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ListarDadosForm extends JFrame {

    private JTree tree;
    private DefaultTreeModel treeModel;

    public ListarDadosForm() {
        setTitle("Listar Dados");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tree = new JTree();
        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane, BorderLayout.CENTER);

        loadTreeData();
    }

    private void loadTreeData() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Clientes");
        treeModel = new DefaultTreeModel(root);
        tree.setModel(treeModel);

        String sql = "SELECT c.id AS cliente_id, c.nome AS cliente_nome, " +
                     "p.id AS pet_id, p.nome AS pet_nome, p.peso, p.idade, p.sexo, p.especie, p.raca, " +
                     "v.id AS vacina_id, v.nome AS vacina_nome, v.descricao, v.data_aplicacao " +
                     "FROM cliente c " +
                     "LEFT JOIN pet p ON c.id = p.proprietario_id " +
                     "LEFT JOIN vacina v ON p.id = v.pet_id " +
                     "ORDER BY c.nome, p.nome, v.data_aplicacao";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            DefaultMutableTreeNode clienteNode = null;
            DefaultMutableTreeNode petNode = null;
            int lastClienteId = -1;
            int lastPetId = -1;

            while (rs.next()) {
                int clienteId = rs.getInt("cliente_id");
                String clienteNome = rs.getString("cliente_nome");

                if (clienteId != lastClienteId) {
                    clienteNode = new DefaultMutableTreeNode(clienteNome);
                    root.add(clienteNode);
                    lastClienteId = clienteId;
                    lastPetId = -1;
                }

                int petId = rs.getInt("pet_id");
                String petNome = rs.getString("pet_nome");

                if (petId != lastPetId && petNome != null) {
                    petNode = new DefaultMutableTreeNode(petNome +
                            ", Idade: " + rs.getInt("idade") +
                            ", Sexo: " + rs.getString("sexo") +
                            ", Espécie: " + rs.getString("especie") +
                            ", Raça: " + rs.getString("raca") + ")");
                    clienteNode.add(petNode);
                    lastPetId = petId;
                }

                String vacinaNome = rs.getString("vacina_nome");

                if (vacinaNome != null) {
                    DefaultMutableTreeNode vacinaNode = new DefaultMutableTreeNode(vacinaNome +
                            " Descrição: " + rs.getString("descricao"));
                    if (petNode != null) {
                        petNode.add(vacinaNode);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tree.expandRow(0); // Expandir o nó raiz (Clientes) por padrão
    }
}
