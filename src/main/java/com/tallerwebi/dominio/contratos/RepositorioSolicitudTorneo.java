package com.tallerwebi.dominio.contratos;

import com.tallerwebi.dominio.SolicitudTorneo;

import java.util.List;

public interface RepositorioSolicitudTorneo {
    void guardar(SolicitudTorneo solicitud);
    void actualizar(SolicitudTorneo solicitud);
    SolicitudTorneo buscarPorId(Long id);
    List<SolicitudTorneo> buscarPorTorneo(Long torneoId);
    List<SolicitudTorneo> buscarPorEquipo(Long equipoId);
    // Para evitar duplicados: busca si ya existe una solicitud pendiente de este equipo a este torneo
    SolicitudTorneo buscarPendiente(Long torneoId, Long equipoId);
}
