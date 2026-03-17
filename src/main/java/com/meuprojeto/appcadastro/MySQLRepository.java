/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.meuprojeto.appcadastro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author eduol
 */
public class MySQLRepository {
    
    // Unico atributo estático que guarda a instancia
    private static MySQLRepository instancia = null;
    
    // O objeto de conexão com o banco
    private Connection conexao;
    
    // Construtor PRIVADO - Ninguem de fora pode dar "new MySQLRepository()"
    private MySQLRepository() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/app_db";
        String user = "root";
        String pass = "minhasenhasecreta";
        this.conexao = DriverManager.getConnection(url, user, pass);
        criarTabelaSeNaoExistir();
    }
    
    // Metodo publico estatico - a unica porta de entrada
    public static MySQLRepository getInstance() throws SQLException {
        if (instancia == null) {
            instancia = new MySQLRepository(); // Só executa uma vez
        }
        return instancia;
    }
    
    private void criarTabelaSeNaoExistir() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS usuarios (
                id        INT AUTO_INCREMENT PRIMARY KEY,
                nome      VARCHAR(100) NOT NULL,
                email     VARCHAR(100),
                telefone  VARCHAR(20),
                senha     VARCHAR(100)
            )
            """;
        conexao.createStatement().execute(sql);
    }
    
    public void inserirUsuario(Usuario u) throws SQLException {
        // PreparedStatement evita SQL Injection — nunca concatene strings com dados do usuário!
        String sql = "INSERT INTO usuarios (nome, email, telefone, senha) VALUES (?,?,?,?)";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setString(1, u.getNome());
        ps.setString(2, u.getEmail());
        ps.setString(3, u.getTelefone());
        ps.setString(4, u.getSenha());
        ps.executeUpdate();
    }
    
    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nome, email, telefone FROM usuarios"; // A senha não pode ser listada por segurança
        ResultSet rs = conexao.createStatement().executeQuery(sql);
        
        while(rs.next()) {
            lista.add(new Usuario(
            rs.getInt("id"),
            rs.getString("nome"),
            rs.getString("email"),
            rs.getString("telefone"),
            "" // A senha fica em branco na listagem - pratica de segurança
            ));
        }
        return lista;
    }
    
    public void atualizarUsuario(Usuario u) throws SQLException {
        String sql = "UPDATE usuarios SET nome=?, email=?, telefone=?, senha=? WHERE id=?";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setString(1, u.getNome());
        ps.setString(2, u.getEmail());
        ps.setString(3, u.getTelefone());
        ps.setString(4, u.getSenha());
        ps.setInt(5, u.getId()); // o WHERE - Identifica qual linha atualizar
        ps.executeUpdate();
    }
    
    public void apagarUsuario(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id=?";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
