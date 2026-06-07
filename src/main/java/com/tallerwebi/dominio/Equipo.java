package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.Deporte;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    private Deporte deporte;

    @OneToMany(mappedBy = "equipo")
    private List<EquipoJugador> jugadores;

    @OneToMany(mappedBy = "equipo" ,cascade = CascadeType.PERSIST)
    private List<TorneoEquipo> torneos = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    public List<EquipoJugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<EquipoJugador> jugadores) {
        this.jugadores = jugadores;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TorneoEquipo> getTorneos() {
        return torneos;
    }

    public void setTorneos(List<TorneoEquipo> torneos) {
        this.torneos = torneos;
    }
}