package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Enums.Posicion;
import com.tallerwebi.dominio.Equipo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServicioEquipo {

    void crearEquipo(Equipo equipo, Long jugadorId, Posicion posicion);

    List<Equipo> buscarEquiposPorNombre(String nombre);
    Equipo buscarEquiposPorId(Long id);
    List<Equipo> listarTodos();
}
