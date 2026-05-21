package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class ServicioCrearTorneoImpl implements ServicioCrearTorneo {
    private RepositorioEquipo.RepositorioTorneo repositorioTorneo;

    @Autowired
    public ServicioCrearTorneoImpl( RepositorioEquipo.RepositorioTorneo repositorioTorneo) {
        this.repositorioTorneo = repositorioTorneo;
    }

    @Override
    @Transactional
    public void guardar(Torneo torneo) {

        repositorioTorneo.guardar(torneo);
    }
    @Override
    @Transactional
    public List<Torneo> buscarTodos(){
        return repositorioTorneo.buscarTodos();
    }


}
