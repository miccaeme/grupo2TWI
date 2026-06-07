package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.Deporte;

import javax.persistence.*;

@Entity
public class TorneoEquipo {
//Entidad para sumar equipos al torneo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Deporte deporte;
    @ManyToOne
    private Torneo torneo;

    @ManyToOne
    private Equipo equipo;


// fixture idlocal , visitante, fecha , etc
    //estadistica relacionada con fixture


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
