package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Enums.Posicion;
import com.tallerwebi.dominio.Equipo;
import org.springframework.stereotype.Service;

@Service
public interface ServicioEquipo {

    void crearEquipo(Equipo equipo, Long jugadorId, Posicion posicion);
}
