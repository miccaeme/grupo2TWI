package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.EstadoSolicitud;
import com.tallerwebi.dominio.contratos.RepositorioEquipo;
import com.tallerwebi.dominio.contratos.RepositorioJugador;
import com.tallerwebi.dominio.contratos.RepositorioSolicitudUnion;
import com.tallerwebi.dominio.servicios.ServicioSolicitudUnion;
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

        SolicitudUnion solicitudUnion = new SolicitudUnion(jugador, equipo);

        repositorioSolicitudUnion.guardar(solicitudUnion);
    }

    @Override
    public void aceptarSolicitud(Long solicitudId, Long capitanId) {
        SolicitudUnion solicitud = validarSolicitud(solicitudId);

        solicitud.setEstado(EstadoSolicitud.ACEPTADA);

        repositorioSolicitudUnion.actualizar(solicitud);
    }

    @Override
    public void rechazarSolicitud(Long solicitudId, Long capitanId) {
        SolicitudUnion solicitud = validarSolicitud(solicitudId);

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

    private SolicitudUnion validarSolicitud(Long solicitudId) {
        SolicitudUnion solicitud = repositorioSolicitudUnion.buscarPorId(solicitudId);

        if (solicitud == null) {
            throw new SolicitudNoValidaException("La solicitud no existe.");
        }

        if (!EstadoSolicitud.PENDIENTE.equals(solicitud.getEstado())) {
            throw new SolicitudNoValidaException("La solicitud ya fue procesada.");
        }

        return solicitud;
    }
}