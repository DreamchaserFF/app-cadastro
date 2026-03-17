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
public class MenuPrincipal extends JFrame {
    
    public MenuPrincipal() {
        setTitle("Sistema de Gestão - Java + MySQL Docker");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 50));
        
        JButton btnCadastro = new JButton("Cadastrar Novo Usuario");
        JButton btnListagem = new JButton("Listar Usuários");
        
        btnCadastro.addActionListener(e -> new FormCadastro().setVisible(true));
        btnListagem.addActionListener(e -> new FormListagem().setVisible(true));
    
        add(btnCadastro);
        add(btnListagem);
    }
    
    public static void main(String[] args) {
        // Sempre inicializa interfaces Swing dentro do invokeLater
        // Isso garante que a UI rode na thread correta (EDT - Event Dispatch Thrad)
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}
