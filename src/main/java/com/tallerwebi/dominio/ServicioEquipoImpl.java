package com.tallerwebi.dominio;


import com.tallerwebi.dominio.Enums.Posicion;
import com.tallerwebi.dominio.contratos.RepositorioEquipo;
import com.tallerwebi.dominio.contratos.RepositorioEquipoJugador;
import com.tallerwebi.dominio.contratos.RepositorioJugador;
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

    @Autowired
    public ServicioEquipoImpl(RepositorioEquipo repositorioEquipo, RepositorioJugador repositorioJugador, RepositorioEquipoJugador repositorioEquipoJugador) {
        this.repositorioEquipo = repositorioEquipo;
        this.repositorioJugador = repositorioJugador;
        this.repositorioEquipoJugador = repositorioEquipoJugador;
    }


    @Override
    public void crearEquipo(Equipo equipo, Long jugadorId, Posicion posicion) {
        repositorioEquipo.guardar(equipo);

        Jugador jugador = repositorioJugador.buscarPorId(jugadorId);
        EquipoJugador equipoJugador = new EquipoJugador();
        equipoJugador.setEquipo(equipo);
        equipoJugador.setJugador(jugador);
        equipoJugador.setCapitan(true);
        equipoJugador.setPosicion(posicion);
        repositorioEquipoJugador.guardar(equipoJugador);
    }

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
    }

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
    public List<Equipo> buscarEquiposDelCapitan(Long idJugador) {
        return repositorioEquipoJugador.buscarEquiposPorJugadorYCapitan(idJugador, true);
    }

    @Override
    public List<EquipoJugador> obtenerJugadoresDelEquipo(Long idEquipo) {
        return repositorioEquipoJugador.buscarJugadoresPorEquipo(idEquipo);
    }
}