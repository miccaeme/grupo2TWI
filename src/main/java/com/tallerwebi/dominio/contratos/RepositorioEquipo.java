package com.tallerwebi.dominio.contratos;

import com.tallerwebi.dominio.Enums.Deporte;
import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.Torneo;

import java.util.List;

public interface RepositorioEquipo {
    void guardar(Equipo equipo);

    Equipo buscarPorId(Long id);

    List<Equipo> findAll();

    List<Equipo> buscarPorNombre(String nombre);

    List<Equipo> buscarEquiposPorJugadorIdYCapitan(Long jugadorId, boolean b);

    List<Equipo> buscarPorDeporte(Deporte deporte);
}