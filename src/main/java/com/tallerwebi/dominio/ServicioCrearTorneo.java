package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioCrearTorneo {

    void guardar(Torneo torneo);
    List<Torneo> buscarTodos();

}
