package com.tallerwebi.dominio.contratos;

import com.tallerwebi.dominio.Partido;

import java.util.List;

public interface RepositorioPartido {

    void guardar(Partido partido);

    Partido buscarPorId(Long id);

    List<Partido> obtenerTodosLosPartidos();
}
