package com.tallerwebi.dominio.contratos;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.Partido;

import java.util.List;

public interface RepositorioPartido {

    void guardar(Partido partido);

    Partido buscarPorId(Long id);

    List<Partido> obtenerTodosLosPartidos();

    void eliminarPartidosPorTorneoId(Long idTorneo);

    List<Partido> buscarPartidosPorTorneoId(Long idTorneo);

    List<Jugador> obtenerJugadores(Long idPartido, String bando);
}
