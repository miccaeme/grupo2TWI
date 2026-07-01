package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.EstadoPartido;
import com.tallerwebi.dominio.contratos.RepositorioPartido;
import com.tallerwebi.dominio.contratos.RepositorioTorneo;
import com.tallerwebi.dominio.servicios.ServicioGeneradorFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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

    //(Round Robin / Algoritmo Cíclico)
    private void generarLigaTodosContraTodos(Torneo torneo, List<Equipo> equiposOriginales) {
        // Clonamos la lista para no alterar la colección original que viene por parámetro
        List<Equipo> equipos = new ArrayList<>(equiposOriginales);

        // Si la cantidad de equipos es impar, agregamos un "null" que representará fecha LIBRE
        if (equipos.size() % 2 != 0) {
            equipos.add(null);
        }

        int cantidadEquipos = equipos.size();
        int cantidadFechas = cantidadEquipos - 1;
        int partidosPorFecha = cantidadEquipos / 2;

        // Iteramos para construir cada una de las fechas del torneo cronológicamente
        for (int nroFecha = 1; nroFecha <= cantidadFechas; nroFecha++) {

            for (int i = 0; i < partidosPorFecha; i++) {
                int localId = (nroFecha + i) % (cantidadEquipos - 1);
                int visitanteId = (nroFecha - i + cantidadEquipos - 1) % (cantidadEquipos - 1);

                // El último elemento se deja fijo en una posición para habilitar la rotación cíclica
                if (i == 0) {
                    visitanteId = cantidadEquipos - 1;
                }

                Equipo local = equipos.get(localId);
                Equipo visitante = equipos.get(visitanteId);

                // Si a un equipo le toca cruzar con el "null", significa que tiene fecha Libre (no se guarda partido)
                if (local != null && visitante != null) {
                    Partido nuevoPartido = new Partido();
                    nuevoPartido.setTorneo(torneo);
                    nuevoPartido.setEquipoLocal(local);
                    nuevoPartido.setEquipoVisitante(visitante);

                    // Cada partido se guarda sumando semanas dinámicamente según su número de fecha
                    nuevoPartido.setFecha(LocalDate.now().plusDays(7L * nroFecha));
                    nuevoPartido.setEstado(EstadoPartido.PROGRAMADO);

                    nuevoPartido.setNroFecha(nroFecha);

                    repositorioPartido.guardar(nuevoPartido);
                }
            }
        }
    }

    private void generarPlayoffs(Torneo torneo, List<Equipo> equipos) {
        for (int i = 0; i < equipos.size() / 2; i++) {
            Partido nuevoPartido = new Partido();
            nuevoPartido.setTorneo(torneo);
            nuevoPartido.setEquipoLocal(equipos.get(i));
            nuevoPartido.setEquipoVisitante(equipos.get(equipos.size() - 1 - i));
            nuevoPartido.setFecha(LocalDate.now().plusDays(7));
            nuevoPartido.setEstado(EstadoPartido.PROGRAMADO);
            nuevoPartido.setNroFecha(1);

            repositorioPartido.guardar(nuevoPartido);
        }
    }

    private void generarGruposYEliminatorias(Torneo torneo, List<Equipo> equipos) {
        // por implementar
    }
}