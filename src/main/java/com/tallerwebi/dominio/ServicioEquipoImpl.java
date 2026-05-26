package com.tallerwebi.dominio;


import com.tallerwebi.dominio.Enums.Posicion;
import com.tallerwebi.dominio.contratos.RepositorioEquipo;
import com.tallerwebi.dominio.contratos.RepositorioEquipoJugador;
import com.tallerwebi.dominio.contratos.RepositorioJugador;
import com.tallerwebi.dominio.servicios.ServicioEquipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioEquipoImpl implements ServicioEquipo {

    @Autowired
    private RepositorioEquipo repositorioEquipo;
    @Autowired
    private RepositorioJugador repositorioJugador;
    @Autowired
    private RepositorioEquipoJugador repositorioEquipoJugador;


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
    public List<Equipo> buscarEquiposPorNombre(String nombre) {
        return repositorioEquipo.buscarPorNombre(nombre);
    }

    @Override
    public Equipo buscarEquiposPorId(Long id) {
        return repositorioEquipo.buscarPorId(id);
    }
    @Transactional(readOnly = true)
    public List<Equipo> listarTodos() {
        return repositorioEquipo.findAll();
    }
}