package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.EstadoPartido;
import com.tallerwebi.dominio.contratos.RepositorioPartido;
import com.tallerwebi.dominio.contratos.RepositorioTorneo;
import com.tallerwebi.dominio.servicios.ServicioGeneradorFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ServicioGeneradorFixtureImpl implements ServicioGeneradorFixture {

    private RepositorioPartido repositorioPartido;
    private RepositorioTorneo repositorioTorneo;

    @Autowired
    public ServicioGeneradorFixtureImpl(RepositorioPartido repositorioPartido, RepositorioTorneo repositorioTorneo) {
        this.repositorioPartido = repositorioPartido;
        this.repositorioTorneo = repositorioTorneo;
    }

    @Override
    public void generarFixtureAutomatico(Long idTorneo, List<Equipo> equipos, String formato) {
        if (equipos == null || equipos.size() < 2) {
            throw new IllegalArgumentException("Se necesitan al menos 2 equipos para armar un fixture.");
        }

        Torneo torneo = repositorioTorneo.buscarPorId(idTorneo);

        repositorioPartido.eliminarPartidosPorTorneoId(idTorneo);

        switch (formato) {
            case "liga":
                generarLigaTodosContraTodos(torneo, equipos);
                break;

            case "eliminacion_directa":
                generarPlayoffs(torneo, equipos);
                break;

            case "grupos_playoffs":
                generarGruposYEliminatorias(torneo, equipos);
                break;

            default:
                throw new IllegalArgumentException("Formato de torneo no reconocido");
        }
    }

    private void generarLigaTodosContraTodos(Torneo torneo, List<Equipo> equipos) {
        for (int i = 0; i < equipos.size(); i++) {
            for (int j = i + 1; j < equipos.size(); j++) {
                Partido nuevoPartido = new Partido();
                nuevoPartido.setTorneo(torneo);
                nuevoPartido.setEquipoLocal(equipos.get(i));
                nuevoPartido.setEquipoVisitante(equipos.get(j));
                nuevoPartido.setFecha(LocalDate.now().plusDays(7));
                nuevoPartido.setEstado(EstadoPartido.PROGRAMADO);
                nuevoPartido.setNroFecha(1);

                repositorioPartido.guardar(nuevoPartido);
            }
        }
    }

    private void generarPlayoffs(Torneo torneo, List<Equipo> equipos) {
        for (int i = 0; i < equipos.size() / 2; i++) {
            Partido nuevoPartido = new Partido();
            nuevoPartido.setTorneo(torneo); //
            nuevoPartido.setEquipoLocal(equipos.get(i));
            nuevoPartido.setEquipoVisitante(equipos.get(equipos.size() - 1 - i));
            nuevoPartido.setFecha(LocalDate.now().plusDays(7));
            nuevoPartido.setEstado(EstadoPartido.PROGRAMADO);
            nuevoPartido.setNroFecha(1);

            repositorioPartido.guardar(nuevoPartido);
        }
    }

    private void generarGruposYEliminatorias(Torneo torneo, List<Equipo> equipos) {

    }
}
