package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.EstadoPartido;
import com.tallerwebi.dominio.contratos.RepositorioPartido;
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

    @Autowired
    public ServicioGeneradorFixtureImpl(RepositorioPartido repositorioPartido) {
        this.repositorioPartido = repositorioPartido;
    }

    @Override
    public void generarFixtureAutomatico(Long idTorneo, List<Equipo> equipos, String formato) {
        if (equipos == null || equipos.size() < 2) {
            throw new IllegalArgumentException("Se necesitan al menos 2 equipos para armar un fixture.");
        }


        switch (formato) {
            case "liga":
                generarLigaTodosContraTodos(idTorneo, equipos);
                break;

            case "eliminacion_directa ":
                generarPlayoffs(idTorneo, equipos);
                break;

            case "grupos_playoffs":
                generarGruposYEliminatorias(idTorneo, equipos);
                break;

            default:
                throw new IllegalArgumentException("Formato de torneo no reconocido");
        }
    }

    private void generarLigaTodosContraTodos(Long idTorneo, List<Equipo> equipos) {
        // Cruza todos los equipos uno contra uno en formato liga
        for (int i = 0; i < equipos.size(); i++) {
            for (int j = i + 1; j < equipos.size(); j++) {
                Partido nuevoPartido = new Partido();
                nuevoPartido.setEquipoLocal(equipos.get(i));
                nuevoPartido.setEquipoVisitante(equipos.get(j));
                nuevoPartido.setFecha(LocalDate.now().plusDays(7)); // Fecha tentativa de prueba
                nuevoPartido.setEstado(EstadoPartido.PROGRAMADO); // Usando tu Enum seguro

                repositorioPartido.guardar(nuevoPartido);
            }
        }
    }

    private void generarPlayoffs(Long idTorneo, List<Equipo> equipos) {
        // Cruces de eliminación directa para la primera ronda
        for (int i = 0; i < equipos.size() / 2; i++) {
            Partido nuevoPartido = new Partido();
            nuevoPartido.setEquipoLocal(equipos.get(i));
            nuevoPartido.setEquipoVisitante(equipos.get(equipos.size() - 1 - i));
            nuevoPartido.setFecha(LocalDate.now().plusDays(7));
            nuevoPartido.setEstado(EstadoPartido.PROGRAMADO);

            repositorioPartido.guardar(nuevoPartido);
        }
    }

    private void generarGruposYEliminatorias(Long idTorneo, List<Equipo> equipos) {

    }
}
