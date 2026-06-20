package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Partido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ServicioPartido {

    void crearPartido(Partido partido);

    Partido buscarPorId(Long id);

    List<Partido> listarFixture();

    List<Partido> buscarPartidosPorTorneoId(Long idTorneo);
}
