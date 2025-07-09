package com.uniftec.loginexemplo.sql.usuarios;

public class Usuario {
    private long id;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private String estado;
    private String cidade;
    private String tipoUsuario;
    private String senha;

    public Usuario(long id, String nome, String email, String telefone, String endereco, String estado, String cidade, String tipoUsuario, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.estado = estado;
        this.cidade = cidade;
        this.tipoUsuario = tipoUsuario;
        this.senha = senha;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}