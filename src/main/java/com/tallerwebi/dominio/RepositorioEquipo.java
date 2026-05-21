package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioEquipo {
    void guardar(Equipo equipo);

    interface RepositorioTorneo {
        void guardar(Torneo torneo);
        List<Torneo> buscarTodos();
    }
}