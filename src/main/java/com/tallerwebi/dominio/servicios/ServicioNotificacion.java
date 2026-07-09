package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Enums.Posicion;
import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.Notificacion;

import java.util.List;

public interface ServicioNotificacion {
    void crearAvisoInscripcionDirecta(Jugador jugador, Equipo equipo, Posicion posicion);
    List<Notificacion>obtenerNotificacionesPorJugador(String nickname);
    void crearAvisoSolicitudAceptada(Jugador jugador, Equipo equipo);
    void crearAvisoSolicitudRechazada(Jugador jugador , Equipo equipo);
    void marcarComoLeida(Long id);
}
