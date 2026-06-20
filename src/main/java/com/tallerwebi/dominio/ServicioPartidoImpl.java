package com.tallerwebi.dominio;

import com.tallerwebi.dominio.contratos.RepositorioPartido;
import com.tallerwebi.dominio.excepcion.PartidoInvalidoException;
import com.tallerwebi.dominio.servicios.ServicioPartido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioPartidoImpl implements ServicioPartido {

    private RepositorioPartido repositorioPartido;

    @Autowired
    public ServicioPartidoImpl(RepositorioPartido repositorioPartido) {

        this.repositorioPartido = repositorioPartido;
    }

    @Override
    public void crearPartido(Partido partido) {

        if (partido.getEquipoLocal() == null || partido.getEquipoVisitante() == null) {
            throw new PartidoInvalidoException("El partido debe tener un equipo local y un equipo visitante.");
        }

        // Validamos que no sea el mismo equipo (comparamos por ID)
        Long idLocal = partido.getEquipoLocal().getId();
        Long idVisitante = partido.getEquipoVisitante().getId();

        if (idLocal.equals(idVisitante)) {
            throw new PartidoInvalidoException("Un equipo no puede jugar contra sí mismo.");
        }


        repositorioPartido.guardar(partido);
    }

    @Override
    @Transactional
    public Partido buscarPorId(Long id) {
        return repositorioPartido.buscarPorId(id);
    }

    @Override
    @Transactional
    public List<Partido> listarFixture() {

        return repositorioPartido.obtenerTodosLosPartidos();
    }

    @Override
    public List<Partido> buscarPartidosPorTorneoId(Long idTorneo) {
        if (idTorneo == null) {
            throw new IllegalArgumentException("El ID del torneo no puede ser nulo");
        }
        // Le pide al repositorio que busque usando el Criteria que armamos antes
        return repositorioPartido.buscarPartidosPorTorneoId(idTorneo);
    }

}
