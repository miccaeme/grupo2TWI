package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.EstadoSolicitud;
import com.tallerwebi.dominio.Servicios.ServicioSolicitudUnion;
import com.tallerwebi.dominio.excepcion.SolicitudDuplicadaException;
import com.tallerwebi.dominio.excepcion.SolicitudNoValidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioSolicitudUnionImpl implements ServicioSolicitudUnion {

    @Autowired
    private RepositorioSolicitudUnion repositorioSolicitudUnion;

    @Autowired
    private RepositorioJugador repositorioJugador;

    @Autowired
    private RepositorioEquipo repositorioEquipo;

    @Override
    public void solicitarUnion(Long jugadorId, Long equipoId) {
        if (repositorioSolicitudUnion.existeSolicitudPendiente(jugadorId, equipoId)) {
            throw new SolicitudDuplicadaException("Ya existe una solicitud pendiente para este equipo.");
        }
        Jugador jugador = repositorioJugador.buscarPorId(jugadorId);
        Equipo equipo = repositorioEquipo.buscarPorId(equipoId);
        repositorioSolicitudUnion.guardar(new SolicitudUnion(jugador, equipo));
    }

    @Override
    public void aceptarSolicitud(Long solicitudId, Long capitanId) {
        SolicitudUnion solicitud = validarSolicitudYPermiso(solicitudId, capitanId);
        solicitud.setEstado(EstadoSolicitud.ACEPTADA);
        repositorioSolicitudUnion.actualizar(solicitud);
    }

    @Override
    public void rechazarSolicitud(Long solicitudId, Long capitanId) {
        SolicitudUnion solicitud = validarSolicitudYPermiso(solicitudId, capitanId);
        solicitud.setEstado(EstadoSolicitud.RECHAZADA);
        repositorioSolicitudUnion.actualizar(solicitud);
    }

    @Override
    public List<SolicitudUnion> obtenerPendientesPorEquipo(Long equipoId) {
        return repositorioSolicitudUnion.buscarPendientesPorEquipo(equipoId);
    }

    @Override
    public List<SolicitudUnion> obtenerSolicitudesDeJugador(Long jugadorId) {
        return repositorioSolicitudUnion.buscarPorJugador(jugadorId);
    }

    /** Valida que la solicitud exista, esté PENDIENTE y que quien actúa sea el capitán del equipo. */
    private SolicitudUnion validarSolicitudYPermiso(Long solicitudId, Long capitanId) {
        SolicitudUnion solicitud = repositorioSolicitudUnion.buscarPorId(solicitudId);
        if (solicitud == null || !EstadoSolicitud.PENDIENTE.equals(solicitud.getEstado())) {
            throw new SolicitudNoValidaException("La solicitud no existe o ya fue procesada.");
        }
        if (!capitanId.equals(solicitud.getEquipo().getCapitanId())) {
            throw new SolicitudNoValidaException("Solo el capitán del equipo puede gestionar solicitudes.");
        }
        return solicitud;
    }
}