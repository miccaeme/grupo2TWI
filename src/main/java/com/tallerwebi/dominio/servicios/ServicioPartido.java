package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Partido;

import java.util.List;

public interface ServicioPartido {

    void crearPartido(Partido partido);

    Partido buscarPorId(Long id);

    List<Partido> listarFixture();
}
