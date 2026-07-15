package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.SolicitudTorneo;

import java.util.List;

public interface ServicioSolicitudTorneo {

    void solicitarIngreso(Long torneoId, Long equipoId);
    void aceptarSolicitud(Long solicitudId);
    void rechazarSolicitud(Long solicitudId);
    List<SolicitudTorneo> obtenerSolicitudesPendientesPorTorneo(Long torneoId);
    List<SolicitudTorneo> obtenerSolicitudesPendientesPorEquipo(Long equipoId);

}
