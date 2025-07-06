package com.uniftec.loginexemplo.sql.eventos;

public class Evento {
    private long id;
    private String nome;
    private String descricao;
    private String dataInicio;
    private String dataFim;
    private String horaInicio;
    private String horaFim;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private int catSeguranca;
    private int catLimpeza;
    private int catInfraestrutura;
    private int catOutros;

    public Evento() {

    }

    public Evento(long id, String nome, String descricao, String dataInicio,
                  String dataFim, String horaInicio, String horaFim, String rua,
                  String numero, String bairro, String cidade, String uf,
                  int catSeguranca, int catLimpeza, int catInfraestrutura, int catOutros) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.catSeguranca = catSeguranca;
        this.catLimpeza = catLimpeza;
        this.catInfraestrutura = catInfraestrutura;
        this.catOutros = catOutros;
    }

    public Evento(String nome, String descricao, String dataInicio,
                  String dataFim, String horaInicio, String horaFim, String rua,
                  String numero, String bairro, String cidade, String uf,
                  int catSeguranca, int catLimpeza, int catInfraestrutura, int catOutros) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.catSeguranca = catSeguranca;
        this.catLimpeza = catLimpeza;
        this.catInfraestrutura = catInfraestrutura;
        this.catOutros = catOutros;
    }
    
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }
    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }
    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }
    public String getHoraFim() { return horaFim; }
    public void setHoraFim(String horaFim) { this.horaFim = horaFim; }
    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }
    public int getCatSeguranca() { return catSeguranca; }
    public void setCatSeguranca(int catSeguranca) { this.catSeguranca = catSeguranca; }
    public int getCatLimpeza() { return catLimpeza; }
    public void setCatLimpeza(int catLimpeza) { this.catLimpeza = catLimpeza; }
    public int getCatInfraestrutura() { return catInfraestrutura; }
    public void setCatInfraestrutura(int catInfraestrutura) { this.catInfraestrutura = catInfraestrutura; }
    public int getCatOutros() { return catOutros; }
    public void setCatOutros(int catOutros) { this.catOutros = catOutros; }
}
