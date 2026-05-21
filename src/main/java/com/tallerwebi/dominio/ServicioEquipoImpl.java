package com.tallerwebi.dominio;


import com.tallerwebi.dominio.Enums.Posicion;
import com.tallerwebi.dominio.contratos.RepositorioEquipo;
import com.tallerwebi.dominio.servicios.ServicioEquipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioEquipoImpl implements ServicioEquipo {

    @Autowired
    private RepositorioEquipo repositorioEquipo;


    @Override
    public void crearEquipo(Equipo equipo, Long jugadorId, Posicion posicion) {
        repositorioEquipo.guardar(equipo);
    }
}