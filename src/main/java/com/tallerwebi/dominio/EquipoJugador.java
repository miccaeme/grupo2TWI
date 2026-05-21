package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.Posicion;

import javax.persistence.*;

@Entity
public class EquipoJugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Equipo equipo;

    @ManyToOne
    private Jugador jugador;

    private Boolean capitan;

    @Enumerated(EnumType.STRING)
    private Posicion posicion;

    public Long getId() {
        return id;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Boolean getCapitan() {
        return capitan;
    }

    public void setCapitan(Boolean capitan) {
        this.capitan = capitan;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }
}
