/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.meuprojeto.appcadastro;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author eduol
 */
public class FormCadastro extends JFrame{
    
    private JTextField txtNome, txtEmail, txtTelefone;
    private JPasswordField txtSenha;
    
    public FormCadastro() {
        setTitle("Cadastro de Usuario");
        setSize(350, 250);
        setLocationRelativeTo(null); // Centraliza na tela
        setLayout(new GridLayout(5,2,10,10));
        
        add(new JLabel(" Nome:"));
        txtNome = new JTextField(); add(txtNome);
        
        add(new JLabel(" Email:"));
        txtEmail = new JTextField(); add(txtEmail);
        
        add(new JLabel(" Telefone:"));
        txtTelefone = new JTextField(); add(txtTelefone);
        
        add(new JLabel(" Senha:"));
        txtSenha = new JPasswordField(); add(txtSenha);
        
        JButton btnSalvar = new JButton("Salvar");
        add(new JLabel()); // Célula vazia para alinhar o botão
        add(btnSalvar);
        
        //Ação do botão salvar
        btnSalvar.addActionListener(e -> {
            try {
                Usuario u = new Usuario(
                        txtNome.getText(),
                        txtEmail.getText(),
                        txtTelefone.getText(),
                        new String(txtSenha.getPassword())
                );
                MySQLRepository.getInstance().inserirUsuario(u);
                JOptionPane.showMessageDialog(this, "Usuario salvo com sucesso.");
                dispose(); // Fecha o formulario
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            } 
        });
    }
}
