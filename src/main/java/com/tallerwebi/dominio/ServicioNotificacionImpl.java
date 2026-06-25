package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.Posicion;
import com.tallerwebi.dominio.contratos.RepositorioNotificacion;
import com.tallerwebi.dominio.servicios.ServicioNotificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ServicioNotificacionImpl implements ServicioNotificacion {
    private RepositorioNotificacion repositorioNotificacion;

    @Autowired
    public ServicioNotificacionImpl (RepositorioNotificacion repositorioNotificacion){
        this.repositorioNotificacion = repositorioNotificacion;
    }

    @Override
    public void crearAvisoInscripcionDirecta(Jugador jugador, Equipo equipo, Posicion posicion) {
        Notificacion aviso = new Notificacion();
        aviso.setJugador(jugador);
        aviso.setMensaje("¡Fuiste asignado al equipo '" + equipo.getNombre() + "' como " + posicion.name() + "!");
        aviso.setLeida(false);

        repositorioNotificacion.guardar(aviso);
    }


    @Override
    public List<Notificacion> obtenerNotificacionesPorJugador(String nickname) {
        return repositorioNotificacion.obtenerNotificacionesPorJugador(nickname);
    }

    @Override
    public void crearAvisoSolicitudAceptada(Jugador jugador, Equipo equipo) {
        Notificacion aviso = new Notificacion();
        aviso.setJugador(jugador);
        aviso.setMensaje("¡Tu solicitud fue aprobada! Ya formás parte del equipo '" + equipo.getNombre() + "'.");
        aviso.setLeida(false);

        repositorioNotificacion.guardar(aviso);

    }

    @Override
    public void crearAvisoSolicitudRechazada(Jugador jugador, Equipo equipo) {
        Notificacion aviso = new Notificacion();
        aviso.setJugador(jugador);
        aviso.setMensaje("¡Tu solicitud para unirte al equipo '" + equipo.getNombre() + "'fue rechazada.");
        aviso.setLeida(false);

        repositorioNotificacion.guardar(aviso);
    }
}
