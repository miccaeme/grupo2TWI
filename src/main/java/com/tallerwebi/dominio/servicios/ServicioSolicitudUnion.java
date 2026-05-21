package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.SolicitudUnion;
import java.util.List;

public interface ServicioSolicitudUnion {

    void solicitarUnion(Long jugadorId, Long equipoId);

    void aceptarSolicitud(Long solicitudId, Long capitanId);

    void rechazarSolicitud(Long solicitudId, Long capitanId);

    List<SolicitudUnion> obtenerPendientesPorEquipo(Long equipoId);

    List<SolicitudUnion> obtenerSolicitudesDeJugador(Long jugadorId);
}