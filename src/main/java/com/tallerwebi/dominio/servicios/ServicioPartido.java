package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Enums.TipoEstadistica;
import com.tallerwebi.dominio.Partido;
import com.tallerwebi.presentacion.JugadorDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ServicioPartido {

    void crearPartido(Partido partido);

    void actualizarPartido(Partido partido);

    Partido buscarPorId(Long id);

    List<Partido> listarFixture();

    List<Partido> buscarPartidosPorTorneoId(Long idTorneo);

    List<JugadorDTO> buscarJugadoresDelPartido(Long idPartido, String bando);

    void registrarIncidencia(Long idPartido, Long idJugador, TipoEstadistica tipoEstadistica, String bando, Integer minuto);
}

