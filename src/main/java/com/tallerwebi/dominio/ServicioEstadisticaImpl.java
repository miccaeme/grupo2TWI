package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.TipoEstadistica;
import com.tallerwebi.dominio.contratos.RepositorioEstadistica;
import com.tallerwebi.dominio.servicios.ServicioEstadistica;
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
}
