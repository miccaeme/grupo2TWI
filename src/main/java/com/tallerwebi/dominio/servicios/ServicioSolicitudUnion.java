package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.SolicitudUnion;

import java.util.List;

public interface ServicioSolicitudUnion {
    void postularseAEquipo(Long jugadorId, Long equipoId) throws Exception;
    List<SolicitudUnion> obtenerSolicitudesPendientesPorEquipo(Long equipoId);
    void aceptarSolicitudUnion(Long solicitudId, String posicionElegida) throws Exception;
    void rechazarSolicitudUnion(Long solicitudId) throws Exception;
}
