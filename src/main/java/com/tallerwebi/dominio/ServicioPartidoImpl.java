package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.TipoEstadistica;
import com.tallerwebi.dominio.contratos.RepositorioJugador;
import com.tallerwebi.dominio.contratos.RepositorioPartido;
import com.tallerwebi.dominio.excepcion.PartidoInvalidoException;
import com.tallerwebi.dominio.servicios.ServicioEstadistica;
import com.tallerwebi.dominio.servicios.ServicioPartido;
import com.tallerwebi.presentacion.JugadorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioPartidoImpl implements ServicioPartido {

    private RepositorioPartido repositorioPartido;
    private ServicioEstadistica servicioEstadistica;
    private RepositorioJugador repositorioJugador;

    @Autowired
    public ServicioPartidoImpl(RepositorioPartido repositorioPartido, ServicioEstadistica servicioEstadistica, RepositorioJugador repositorioJugador) {
        this.repositorioPartido = repositorioPartido;
        this.servicioEstadistica = servicioEstadistica;
        this.repositorioJugador = repositorioJugador;
    }

    @Override
    public void crearPartido(Partido partido) {
        if (partido.getEquipoLocal() == null || partido.getEquipoVisitante() == null) {
            throw new PartidoInvalidoException("El partido debe tener un equipo local y un equipo visitante.");
        }

        Long idLocal = partido.getEquipoLocal().getId();
        Long idVisitante = partido.getEquipoVisitante().getId();

        if (idLocal.equals(idVisitante)) {
            throw new PartidoInvalidoException("Un equipo no puede jugar contra sí mismo.");
        }

        repositorioPartido.guardar(partido);
    }

    @Override
    @Transactional
    public void actualizarPartido(Partido partido) {
        if (partido == null || partido.getId() == null) {
            throw new IllegalArgumentException("El partido o su ID no pueden ser nulos para actualizar.");
        }
        repositorioPartido.guardar(partido);
    }

    @Override
    @Transactional
    public Partido buscarPorId(Long id) {
        return repositorioPartido.buscarPorId(id);
    }

    @Override
    @Transactional
    public List<Partido> listarFixture() {
        return repositorioPartido.obtenerTodosLosPartidos();
    }

    @Override
    public List<Partido> buscarPartidosPorTorneoId(Long idTorneo) {
        if (idTorneo == null) {
            throw new IllegalArgumentException("El ID del torneo no puede ser nulo");
        }
        return repositorioPartido.buscarPartidosPorTorneoId(idTorneo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JugadorDTO> buscarJugadoresDelPartido(Long idPartido, String bando) {
        List<Jugador> jugadoresDominio = repositorioPartido.obtenerJugadores(idPartido, bando);
        List<JugadorDTO> dtos = new ArrayList<>();

        if (jugadoresDominio == null) {
            return dtos;
        }

        for (Jugador j : jugadoresDominio) {
            if (j != null) {
                String apodo = (j.getNickname() != null) ? j.getNickname() : "Sin apodo";
                dtos.add(new JugadorDTO(j.getId(), apodo));
            }
        }
        return dtos;
    }

    @Override
    @Transactional
    public void registrarIncidencia(Long idPartido, Long idJugador, TipoEstadistica tipoEstadistica, String bando) {
        // 1. Buscamos las entidades completas de la base de datos
        Partido partido = repositorioPartido.buscarPorId(idPartido);
        Jugador jugador = repositorioJugador.buscarPorId(idJugador);

        if (partido == null || jugador == null) {
            throw new IllegalArgumentException("El partido o el jugador especificado no existe.");
        }

        // Si la incidencia elegida en el modal es un GOL, sumamos al tanteador global del partido
        if (TipoEstadistica.GOL == tipoEstadistica) {
            if ("LOCAL".equals(bando)) {
                partido.setGolesLocal((partido.getGolesLocal() != null ? partido.getGolesLocal() : 0) + 1);
            } else if ("VISITANTE".equals(bando)) {
                partido.setGolesVisitante((partido.getGolesVisitante() != null ? partido.getGolesVisitante() : 0) + 1);
            }
            repositorioPartido.guardar(partido);
        }

        //  creamos el obj usando la entidad estadistica
        Estadistica nuevaEstadistica = new Estadistica();
        nuevaEstadistica.setPartido(partido);
        nuevaEstadistica.setJugador(jugador);
        nuevaEstadistica.setTipo(tipoEstadistica);
        nuevaEstadistica.setTiempo(0);

        // servicio de estadísticas para persistirlo en la tabla Estadistica
        servicioEstadistica.registrarAccionJugador(nuevaEstadistica);
    }
}