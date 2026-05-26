package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Torneo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ServicioTorneo {

    void guardar(Torneo torneo);
    List<Torneo> buscarTodos();
    
    void asignarEquipos(Long id, List<Long> equiposIds);

    Torneo buscarPorId(Long id);
}
