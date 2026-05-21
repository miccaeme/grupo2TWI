package com.tallerwebi.dominio.contratos;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.Torneo;

import java.util.List;

public interface RepositorioEquipo {
    void guardar(Equipo equipo);

    Equipo buscarPorId(Long id);

    interface RepositorioTorneo {
        void guardar(Torneo torneo);
        List<Torneo> buscarTodos();
    }
}