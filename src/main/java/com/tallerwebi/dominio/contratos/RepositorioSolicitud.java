package com.tallerwebi.dominio.contratos;

import com.tallerwebi.dominio.SolicitudUnion;

import java.util.List;

public interface RepositorioSolicitud {
    void guardar(SolicitudUnion solicitud);
    SolicitudUnion buscarPorId(Long id);
    List<SolicitudUnion> buscarPendientesPorEquipo(Long equipoId);
}
