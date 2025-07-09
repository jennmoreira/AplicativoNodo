package com.uniftec.loginexemplo.sql.candidatos;

public class Candidato {
    private long canId;
    private long eveId;
    private long usuId;

    public Candidato(long canId, long eveId, long usuId) {
        this.canId = canId;
        this.eveId = eveId;
        this.usuId = usuId;
    }

    public long getCanId() { return canId; }
    public void setCanId(long canId) { this.canId = canId; }
    public long getEveId() { return eveId; }
    public void setEveId(long eveId) { this.eveId = eveId; }
    public long getUsuId() { return usuId; }
    public void setUsuId(long usuId) { this.usuId = usuId; }
}