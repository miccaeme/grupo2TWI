package com.tallerwebi.presentacion;

public class EstadisticasJugadorDTO {

    private String nickname;
    private String deporte;

    private int goles;
    private int asistencias;
    private int faltas;

    private int triples;
    private int dobles;
    private int simples;

    private int puntosVoley;
    private int aces;


    public EstadisticasJugadorDTO() {}


    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getDeporte() { return deporte; }
    public void setDeporte(String deporte) { this.deporte = deporte; }
    public int getGoles() { return goles; }
    public void setGoles(int goles) { this.goles = goles; }
    public int getAsistencias() { return asistencias; }
    public void setAsistencias(int asistencias) { this.asistencias = asistencias; }
    public int getFaltas() { return faltas; }
    public void setFaltas(int faltas) { this.faltas = faltas; }
    public int getTriples() { return triples; }
    public void setTriples(int triples) { this.triples = triples; }
    public int getDobles() { return dobles; }
    public void setDobles(int dobles) { this.dobles = dobles; }
    public int getSimples() { return simples; }
    public void setSimples(int simples) { this.simples = simples; }
    public int getPuntosVoley() { return puntosVoley; }
    public void setPuntosVoley(int puntosVoley) { this.puntosVoley = puntosVoley; }
    public int getAces() { return aces; }
    public void setAces(int aces) { this.aces = aces; }
}
