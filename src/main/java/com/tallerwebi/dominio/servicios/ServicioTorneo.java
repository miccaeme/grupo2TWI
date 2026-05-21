package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Torneo;

import java.util.List;

public interface ServicioTorneo {

    void guardar(Torneo torneo);
    List<Torneo> buscarTodos();

}
