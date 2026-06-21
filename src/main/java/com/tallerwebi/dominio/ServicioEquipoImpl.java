package com.tallerwebi.dominio;


import com.tallerwebi.dominio.Enums.Posicion;
import com.tallerwebi.dominio.contratos.RepositorioEquipo;
import com.tallerwebi.dominio.contratos.RepositorioEquipoJugador;
import com.tallerwebi.dominio.contratos.RepositorioJugador;
import com.tallerwebi.dominio.contratos.RepositorioUsuario;
import com.tallerwebi.dominio.servicios.ServicioEquipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioEquipoImpl implements ServicioEquipo {

    private RepositorioEquipo repositorioEquipo;
    private RepositorioJugador repositorioJugador;
    private RepositorioEquipoJugador repositorioEquipoJugador;
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioEquipoImpl(RepositorioEquipo repositorioEquipo,
                              RepositorioJugador repositorioJugador,
                              RepositorioEquipoJugador repositorioEquipoJugador,
                              RepositorioUsuario repositorioUsuario) {
        this.repositorioEquipo = repositorioEquipo;
        this.repositorioJugador = repositorioJugador;
        this.repositorioEquipoJugador = repositorioEquipoJugador;
        this.repositorioUsuario = repositorioUsuario;
    }


    @Override
    public void crearEquipo(Equipo equipo, Long idUsuarioLogueado) {

        //Referencia Enum
        int cupoMaximo = equipo.getDeporte().getSlots();
        equipo.setCantidadMaximaSlots(cupoMaximo);

        Usuario creador = repositorioUsuario.buscarUsuarioPorId(idUsuarioLogueado);
        equipo.setCreador(creador);


        repositorioEquipo.guardar(equipo);

        //el creador es el primer integrante y Capitan
        EquipoJugador equipoJugador = new EquipoJugador();
        equipoJugador.setEquipo(equipo);
        equipoJugador.setJugador(creador.getJugador()); // Vinculamos su perfil de jugador
        equipoJugador.setCapitan(true);


        repositorioEquipoJugador.guardar(equipoJugador);
    }

/*
    @Override
    public void asignarJugadorAlEquipo(Long idEquipo, Long idJugador, Posicion posicion) {
        Equipo equipo = repositorioEquipo.buscarPorId(idEquipo);
        Jugador jugador = repositorioJugador.buscarPorId(idJugador);


        EquipoJugador relacionNueva = new EquipoJugador();
        relacionNueva.setEquipo(equipo);
        relacionNueva.setJugador(jugador);
        relacionNueva.setPosicion(posicion);

        relacionNueva.setCapitan(false);


        repositorioEquipoJugador.guardar(relacionNueva);
    } */

    @Override
    public List<Equipo> buscarEquiposPorNombre(String nombre) {
        return repositorioEquipo.buscarPorNombre(nombre);
    }

    @Override
    public Equipo buscarEquipoPorId(Long id) {

        return repositorioEquipo.buscarPorId(id);
    }
    @Transactional(readOnly = true)
    public List<Equipo> listarTodos() {

        return repositorioEquipo.findAll();
    }

    @Override
    public List<Equipo> buscarEquiposDelCapitan(Long idUsuarioLogueado) {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(idUsuarioLogueado);

        if (usuario == null || usuario.getJugador() == null) {
            return List.of();
        }

        Long jugadorId = usuario.getJugador().getId();
        return repositorioEquipo.buscarEquiposPorJugadorIdYCapitan(jugadorId, true);
    }

    @Override
    public List<EquipoJugador> obtenerJugadoresDelEquipo(Long idEquipo) {
        return repositorioEquipoJugador.buscarJugadoresPorEquipo(idEquipo);
    }
}