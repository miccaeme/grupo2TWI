package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.Bando;
import com.tallerwebi.dominio.Enums.TipoEstadistica;

import javax.persistence.*;

@Entity
public class Estadistica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private Partido partido;

    @ManyToOne
    private Jugador jugador;

    //local visitante enum.

    @Enumerated(EnumType.STRING)
    private TipoEstadistica tipo;

    @Enumerated(EnumType.STRING)
    private Bando bando;

    private Integer tiempo;

    public Estadistica() {
    }

    // --- GETTERS Y SETTERS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public TipoEstadistica getTipo() {
        return tipo;
    }

    public void setTipo(TipoEstadistica tipo) {
        this.tipo = tipo;
    }

    public Bando getBando() {
        return bando;
    }

    public void setBando(Bando bando) {
        this.bando = bando;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

}
