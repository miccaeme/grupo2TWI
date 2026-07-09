package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Torneo;
import com.tallerwebi.dominio.TorneoEquipo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public interface ServicioTorneo {

    void guardar(Torneo torneo, Long idUsuarioLogueado);
    void guardarEstadoFixture(Torneo torneo);
    List<Torneo> buscarTodos();
    
    void asignarEquipos(Long id, List<Long> equiposIds);

    Torneo buscarPorId(Long id);

    List<TorneoEquipo> buscarEquiposPorTorneoId(Long id);

    List<Torneo> buscarTorneosDelOrganizador(Long idUsuario);
}
