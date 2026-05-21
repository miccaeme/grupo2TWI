package com.tallerwebi.dominio;

import com.tallerwebi.dominio.contratos.RepositorioEquipo;
import com.tallerwebi.dominio.contratos.RepositorioTorneo;
import com.tallerwebi.dominio.servicios.ServicioTorneo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioTorneoImpl implements ServicioTorneo {

    @Autowired
    private RepositorioTorneo repositorioTorneo;

    @Override
    public void guardar(Torneo torneo) {
        repositorioTorneo.guardar(torneo);
    }

    @Override
    public List<Torneo> buscarTodos() {
        return repositorioTorneo.buscarTodos();
    }
}
