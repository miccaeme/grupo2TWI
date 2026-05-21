package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioSolicitudUnion {

    void guardar(SolicitudUnion solicitud);

    void actualizar(SolicitudUnion solicitud);

    SolicitudUnion buscarPorId(Long id);

    List<SolicitudUnion> buscarPendientesPorEquipo(Long equipoId);

    List<SolicitudUnion> buscarPorJugador(Long jugadorId);

    boolean existeSolicitudPendiente(Long jugadorId, Long equipoId);
}