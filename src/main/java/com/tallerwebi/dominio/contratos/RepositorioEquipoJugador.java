package com.tallerwebi.dominio.contratos;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.EquipoJugador;

import java.util.List;

public interface RepositorioEquipoJugador {
    void guardar(EquipoJugador equipoJugador);

    List<Equipo> buscarEquiposPorJugadorYCapitan(Long idJugador, boolean esCapitan);

    List<EquipoJugador> buscarJugadoresPorEquipo(Long idEquipo);

    int contarJugadoresEnEquipo(Long equipoId);

    boolean elJugadorYaEstaEnElEquipo(Long equipoId, Long jugadorId);
}
