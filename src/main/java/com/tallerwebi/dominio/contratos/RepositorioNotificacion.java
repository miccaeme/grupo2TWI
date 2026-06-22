package com.tallerwebi.dominio.contratos;


import com.tallerwebi.dominio.Notificacion;

import java.util.List;

public interface RepositorioNotificacion {
    void guardar(Notificacion notificacion);
    List<Notificacion> obtenerNotificacionesPorJugador(String nickname);
}
