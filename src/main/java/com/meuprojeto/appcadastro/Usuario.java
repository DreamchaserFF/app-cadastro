/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.meuprojeto.appcadastro;

/**
 *
 * @author eduol
 */
public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String senha;
    
    //Construtor sem ID(quando iserir, o banco gera o ID)
    public Usuario(String nome, String email, String telefone, String senha){
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }
    
    // Construtor com ID (para quando ler do bd - o banco já tem o ID)
    public Usuario(int id, String nome, String email, String telefone, String senha){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }
    
    // Getters
    public int getId()          { return id; }
    public String getNome()     { return nome; }
    public String getEmail()    { return email; }
    public String getTelefone() { return telefone; }
    public String getSenha()    { return senha; }
}
