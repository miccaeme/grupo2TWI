package com.tallerwebi.dominio.excepcion;

import com.tallerwebi.dominio.Torneo;

import java.util.List;

public interface RepositorioTorneo {
    void guardar(Torneo torneo);
    List<Torneo> buscarTodos();
}
