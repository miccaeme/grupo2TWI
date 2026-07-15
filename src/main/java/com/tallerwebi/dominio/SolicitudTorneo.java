package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.EstadoSolicitud;

import javax.persistence.*;

@Entity
public class SolicitudTorneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Torneo torneo;

    @ManyToOne
    private Equipo equipo;

    @Enumerated
    private EstadoSolicitud estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }
}
