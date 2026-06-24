package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Estadistica;

public interface ServicioEstadistica {

    int calcularGolesDelJugadorEnPartido(Long idJugador, Long idPartido);
    int calcularAsistenciasDelJugadorEnPartido(Long idJugador, Long idPartido);
    int calcularFaltasDelJugadorEnPartido(Long idJugador, Long idPartido);
    int calcularGolesTotalesDelJugador(Long idJugador);
    void registrarAccionJugador(Estadistica estadistica);
}
