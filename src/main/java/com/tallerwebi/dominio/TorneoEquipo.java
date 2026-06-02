package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class TorneoEquipo {
//Entidad para sumar equipos al torneo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="torneo_id")
    private Torneo torneo;

    @ManyToOne
    @JoinColumn(name="equipo_id")
    private Equipo equipo;



    public TorneoEquipo() {

    }

    public TorneoEquipo(Torneo torneo, Equipo equipo) {
        this.torneo = torneo;
        this.equipo = equipo;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}
