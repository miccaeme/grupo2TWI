package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.List;

@Entity
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @OneToMany(mappedBy = "equipo")
    private List<EquipoJugador> jugadores;




    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}