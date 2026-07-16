package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.Posicion;
import com.tallerwebi.dominio.contratos.RepositorioNotificacion;
import com.tallerwebi.dominio.servicios.ServicioLogin;
import com.tallerwebi.dominio.servicios.ServicioNotificacion;
import com.tallerwebi.presentacion.NotificacionesHeaderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ServicioNotificacionImpl implements ServicioNotificacion {
    private RepositorioNotificacion repositorioNotificacion;
    private ServicioLogin servicioLogin;

    @Autowired
    public ServicioNotificacionImpl (RepositorioNotificacion repositorioNotificacion, ServicioLogin servicioLogin) {
        this.repositorioNotificacion = repositorioNotificacion;
        this.servicioLogin = servicioLogin;
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

    @Override
    public void marcarComoLeida(Long id) {
        Notificacion noti = repositorioNotificacion.buscarPorId(id);

        if (noti != null) {
            noti.setLeida(true);
            repositorioNotificacion.guardar(noti);
        }
    }


    public NotificacionesHeaderDTO obtenerDatosHeader(Long idUsuario) {
        if (idUsuario == null) {
            return new NotificacionesHeaderDTO();
        }

        try {
            Usuario usuario = servicioLogin.buscarUsuarioPorId(idUsuario);
            if (usuario != null && usuario.getJugador() != null) {
                String nick = usuario.getJugador().getNickname();
                List<Notificacion> novedades = obtenerNotificacionesPorJugador(nick);

                long noLeidas = novedades.stream()
                        .filter(n -> n.getLeida() == null || !n.getLeida())
                        .count();

                return new NotificacionesHeaderDTO(novedades, (int) noLeidas, nick);
            }
        } catch (Exception e) {
            // Manejo silencioso en caso de error de BD
        }
        return new NotificacionesHeaderDTO();
    }
}
