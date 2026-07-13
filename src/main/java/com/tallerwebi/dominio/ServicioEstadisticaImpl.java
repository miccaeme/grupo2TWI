package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.TipoEstadistica;
import com.tallerwebi.dominio.contratos.RepositorioEstadistica;
import com.tallerwebi.dominio.servicios.ServicioEstadistica;
import com.tallerwebi.presentacion.EstadisticasJugadorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ServicioEstadisticaImpl implements ServicioEstadistica {

    private RepositorioEstadistica repositorioEstadistica;

    @Autowired
    public ServicioEstadisticaImpl(RepositorioEstadistica repositorioEstadistica) {
        this.repositorioEstadistica = repositorioEstadistica;
    }

    @Override
    @Transactional
    public void registrarAccionJugador(Estadistica estadistica) {
        repositorioEstadistica.guardar(estadistica);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estadistica> obtenerEstadisticasDelPartido(Long idPartido) {
        return repositorioEstadistica.buscarPorPartido(idPartido);
    }

    @Override
    @Transactional
    public int calcularGolesDelJugadorEnPartido(Long idJugador, Long idPartido) {
        List<Estadistica> estadisticas = repositorioEstadistica.buscarPorJugadorYPartido(idJugador, idPartido);
        int goles = 0;
        for (Estadistica est : estadisticas) {
            if (est.getTipo() == TipoEstadistica.GOL) {
                goles++;
            }
        }
        return goles;
    }

    @Override
    @Transactional
    public int calcularAsistenciasDelJugadorEnPartido(Long idJugador, Long idPartido) {
        List<Estadistica> estadisticas = repositorioEstadistica.buscarPorJugadorYPartido(idJugador, idPartido);
        int asistencias = 0;
        for (Estadistica est : estadisticas) {
            if (est.getTipo() == TipoEstadistica.ASISTENCIA) {
                asistencias++;
            }
        }
        return asistencias;
    }

    @Override
    @Transactional
    public int calcularFaltasDelJugadorEnPartido(Long idJugador, Long idPartido) {
        List<Estadistica> estadisticas = repositorioEstadistica.buscarPorJugadorYPartido(idJugador, idPartido);
        int faltas = 0;
        for (Estadistica est : estadisticas) {
            if (est.getTipo() == TipoEstadistica.FALTA) {
                faltas++;
            }
        }
        return faltas;
    }

    @Override
    @Transactional
    public int calcularGolesTotalesDelJugador(Long idJugador) {
        List<Estadistica> todoElHistorial = repositorioEstadistica.buscarPorJugador(idJugador);

        int golesTotales = 0;
        for (Estadistica est : todoElHistorial) {
            if (est.getTipo() == TipoEstadistica.GOL) {
                golesTotales++;
            }
        }
        return golesTotales;
    }

    @Override
    public EstadisticasJugadorDTO obtenerEstadisticasHistoricas(String nickname, String deporte) {
        EstadisticasJugadorDTO dto = new EstadisticasJugadorDTO();
        dto.setNickname(nickname);
        dto.setDeporte(deporte.toUpperCase());

        // Filtrado por Deporte para el conteo de incidencias históricas
        if ("FUTBOL".equalsIgnoreCase(deporte)) {
            dto.setGoles(repositorioEstadistica.contarPorNicknameYTipo(nickname, TipoEstadistica.GOL));
            dto.setAsistencias(repositorioEstadistica.contarPorNicknameYTipo(nickname, TipoEstadistica.ASISTENCIA));
            dto.setFaltas(repositorioEstadistica.contarPorNicknameYTipo(nickname, TipoEstadistica.FALTA));

        } else if ("BASQUET".equalsIgnoreCase(deporte)) {
            dto.setTriples(repositorioEstadistica.contarPorNicknameYTipo(nickname, TipoEstadistica.TRIPLE));
            dto.setDobles(repositorioEstadistica.contarPorNicknameYTipo(nickname, TipoEstadistica.DOBLE));
            dto.setSimples(repositorioEstadistica.contarPorNicknameYTipo(nickname, TipoEstadistica.SIMPLE));

        } else if ("VOLEY".equalsIgnoreCase(deporte)) {
            // Adaptalo a los nombres exactos de tu enum TipoEstadistica (ej: PUNTO, ACE)
            dto.setPuntosVoley(repositorioEstadistica.contarPorNicknameYTipo(nickname, TipoEstadistica.PUNTO_VOLEY));
            dto.setAces(repositorioEstadistica.contarPorNicknameYTipo(nickname, TipoEstadistica.ACE));
        }

        return dto;
    }
}
