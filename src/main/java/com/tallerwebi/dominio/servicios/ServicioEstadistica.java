package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Estadistica;

import java.util.List;

public interface ServicioEstadistica {

    int calcularGolesDelJugadorEnPartido(Long idJugador, Long idPartido);
    int calcularAsistenciasDelJugadorEnPartido(Long idJugador, Long idPartido);
    int calcularFaltasDelJugadorEnPartido(Long idJugador, Long idPartido);
    int calcularGolesTotalesDelJugador(Long idJugador);
    void registrarAccionJugador(Estadistica estadistica);
    List<Estadistica> obtenerEstadisticasDelPartido(Long idPartido);
}
