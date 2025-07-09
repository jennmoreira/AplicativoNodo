package com.uniftec.loginexemplo.sql.candidatos;

public class CandidaturaDetalhes {
    private long candidaturaId;
    private String nomeUsuario;
    private String nomeEvento;

    public CandidaturaDetalhes(long candidaturaId, String nomeUsuario, String nomeEvento) {
        this.candidaturaId = candidaturaId;
        this.nomeUsuario = nomeUsuario;
        this.nomeEvento = nomeEvento;
    }

    public long getCandidaturaId() {
        return candidaturaId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }
}