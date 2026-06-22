package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Notificacion {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Jugador jugador;
    private String mensaje;
    private Boolean leida;


    public Notificacion() {}

    public Notificacion(Jugador jugador, String mensaje, Boolean leida) {
        this.jugador = jugador;
        this.mensaje = mensaje;
        this.leida = leida;
    }


    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getLeida() {
        return leida;
    }

    public void setLeida(Boolean leida) {
        this.leida = leida;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
