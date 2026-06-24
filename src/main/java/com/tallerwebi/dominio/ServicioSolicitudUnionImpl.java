package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.EstadoSolicitud;
import com.tallerwebi.dominio.Enums.Posicion;
import com.tallerwebi.dominio.contratos.*;
import com.tallerwebi.dominio.servicios.ServicioNotificacion;
import com.tallerwebi.dominio.servicios.ServicioSolicitudUnion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioSolicitudUnionImpl implements ServicioSolicitudUnion {
    @Autowired
    private RepositorioSolicitud repositorioSolicitud;
    @Autowired
    private RepositorioJugador repositorioJugador;
    @Autowired
    private RepositorioEquipo repositorioEquipo;
    @Autowired
    private RepositorioEquipoJugador repositorioEquipoJugador;
    @Autowired
    private ServicioNotificacion servicioNotificacion;
    @Autowired
    private RepositorioUsuario repositorioUsuario;


    @Override
    public void postularseAEquipo(Long usuarioId, Long equipoId) throws Exception {

        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(usuarioId);
        if (usuario == null || usuario.getJugador() == null) {
            throw new Exception("No se encontró un jugador asociado a este usuario.");
        }
        Jugador jugador = usuario.getJugador();
        Equipo equipo = repositorioEquipo.buscarPorId(equipoId);

        boolean yaPertenece = repositorioEquipoJugador.elJugadorYaEstaEnElEquipo(equipoId, jugador.getId());
        if (yaPertenece) {
            throw new Exception("Ya formás parte de este equipo.");
        }

        SolicitudUnion nuevaSolicitud = new SolicitudUnion();
        nuevaSolicitud.setJugador(jugador);
        nuevaSolicitud.setEquipo(equipo);
        nuevaSolicitud.setEstado(EstadoSolicitud.PENDIENTE);

        repositorioSolicitud.guardar(nuevaSolicitud);
    }

    @Override
    public List<SolicitudUnion> obtenerSolicitudesPendientesPorEquipo(Long equipoId) {
        return repositorioSolicitud.buscarPendientesPorEquipo(equipoId);
    }

    @Override
    public void aceptarSolicitudUnion(Long solicitudId, String posicionElegida) throws Exception {
        SolicitudUnion solicitud = repositorioSolicitud.buscarPorId(solicitudId);
        if(solicitud== null) throw new Exception("La solicitud no existe");

        Equipo equipo = solicitud.getEquipo();
        Jugador jugador = solicitud.getJugador();

        int cantidadActual = repositorioEquipoJugador.contarJugadoresEnEquipo(equipo.getId());
        if (cantidadActual >= equipo.getCantidadMaximaSlots()) {
            throw new Exception("No se puede aceptar la solicitud. El equipo esta lleno.");
        }

        solicitud.setEstado(EstadoSolicitud.ACEPTADA);
        repositorioSolicitud.guardar(solicitud);

        EquipoJugador nuevoIntegrante = new EquipoJugador();
        nuevoIntegrante.setEquipo(equipo);
        nuevoIntegrante.setJugador(jugador);
        nuevoIntegrante.setCapitan(false);

        if(posicionElegida != null && !posicionElegida.isEmpty()){
            nuevoIntegrante.setPosicion(Posicion.valueOf(posicionElegida));
        }

        repositorioEquipoJugador.guardar(nuevoIntegrante);

        servicioNotificacion.crearAvisoSolicitudAceptada(jugador, equipo);
    }

    @Override
    public void rechazarSolicitudUnion(Long solicitudId) throws Exception {
        SolicitudUnion solicitud = repositorioSolicitud.buscarPorId(solicitudId);
        if (solicitud == null) throw new Exception("La solicitud no existe.");

        solicitud.setEstado(EstadoSolicitud.RECHAZADA);
        repositorioSolicitud.guardar(solicitud);

        servicioNotificacion.crearAvisoSolicitudRechazada(solicitud.getJugador(), solicitud.getEquipo());

    }


}
