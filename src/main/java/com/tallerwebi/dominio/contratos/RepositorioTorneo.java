package com.tallerwebi.dominio.contratos;

import com.tallerwebi.dominio.Torneo;

import java.util.List;

public interface RepositorioTorneo {
    void guardar(Torneo torneo);

    List<Torneo> buscarTodos();

    Torneo buscarPorId(Long id);
}
