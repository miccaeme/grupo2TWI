package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.EstadoPartido;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Torneo torneo;

    @ManyToOne
    private Equipo equipoLocal;

    @ManyToOne
    private Equipo equipoVisitante;

    private LocalDate fecha;

    private Integer nroFecha;

    @Enumerated(EnumType.STRING)
    private EstadoPartido estado;

    public Partido() {
    }

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

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getNroFecha() {
        return nroFecha;
    }

    public void setNroFecha(Integer nroFecha) {
        this.nroFecha = nroFecha;
    }

    public EstadoPartido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPartido estado) {
        this.estado = estado;
    }
}
