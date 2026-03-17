/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.meuprojeto.appcadastro;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
/**
 *
 * @author eduol
 */
public class FormListagem extends JFrame {
    
    private JTable tabela;
    private DefaultTableModel modelo;
    
    public FormListagem() {
        setTitle("Listagem de Usuários");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Colunas da tabela - sem a senha
        String[] colunas = {"ID", "Nome", "Email", "Telefone"};
        modelo = new DefaultTableModel(colunas, 0){
            public boolean isCellEditable(int row, int col) {
                return false; // impede a edição direta das celulas
            }
        };
        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        
        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnEditar = new JButton("Editar");
        JButton btnApagar = new JButton("Apagar");
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnApagar);
        add(painelBotoes, BorderLayout.SOUTH);
        
        // Carregar dados
        carregarDados();
        
        // Ação do botão Editar
        btnEditar.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um usuário para editar.");
                return;
            }
            
            int id          = (int) modelo.getValueAt(linhaSelecionada, 0);
            String nome     = (String) modelo.getValueAt(linhaSelecionada, 1);
            String email    = (String) modelo.getValueAt(linhaSelecionada, 2);
            String telefone = (String) modelo.getValueAt(linhaSelecionada, 3);
            
            // Pede os novos valores via dialogo
            String novoNome     = JOptionPane.showInputDialog(this, "Nome:", nome);
            String novoEmail    = JOptionPane.showInputDialog(this, "Email:", email);
            String novoTelefone = JOptionPane.showInputDialog(this, "Telefone:", telefone);
            String novaSenha    = JOptionPane.showInputDialog(this, "Senha (deixe em branco para não alterar):", "");
        
            if (novoNome == null) return; // usuário cancelou
            
            // Se senha em branco, busca a senha atual no banco
            if (novaSenha == null || novaSenha.isBlank()) {
                novaSenha = ""; //Mantenha o que já está no banco
            }
            
            try {
                Usuario u = new Usuario(id, novoNome, novoEmail, novoTelefone, novaSenha);
                MySQLRepository.getInstance().atualizarUsuario(u);
                JOptionPane.showMessageDialog(this, "Usuário Atualizado.");
                carregarDados(); //Recarrega a tabela
            }catch(Exception ex) {
                JOptionPane.showMessageDialog(this,"Erro: " + ex.getMessage());
            }
        });
        
        // Ação do botão apagar
        btnApagar.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um usuário para apagar");
                return;
            }
            
            int id = (int)          modelo.getValueAt(linhaSelecionada, 0);
            String nome = (String)  modelo.getValueAt(linhaSelecionada, 1);
        
            int confirma = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja apagar o usuário \"" + nome + "\"?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirma == JOptionPane.YES_OPTION) {
                try {
                    MySQLRepository.getInstance().apagarUsuario(id);
                    JOptionPane.showMessageDialog(this, "Usuário apagado");
                    carregarDados(); // Recarrega a tabela
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
                }
            }        
        });      
    }
    
    // Metodo separado para carregar/recarregar os dados na tabela
    private void carregarDados() {
        modelo.setRowCount(0); // limpa as linhas atuais
        try {
            List<Usuario> usuarios = MySQLRepository.getInstance().listarUsuarios();
            for (Usuario u : usuarios) {
                modelo.addRow(new Object[]{
                    u.getId(), u.getNome(), u.getEmail(), u.getTelefone()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar: " + ex.getMessage());
        }
    }
}
