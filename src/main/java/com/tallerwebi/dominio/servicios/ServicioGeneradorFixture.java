package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Equipo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ServicioGeneradorFixture {
    // Toma los equipos anotados y genera todos los partidos correspondientes en la base de datos
    void generarFixtureAutomatico(Long idTorneo, List<Equipo> equipos, String formato);
}
