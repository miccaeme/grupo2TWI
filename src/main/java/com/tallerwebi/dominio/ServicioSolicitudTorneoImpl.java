package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.EstadoSolicitud;
import com.tallerwebi.dominio.contratos.RepositorioEquipo;
import com.tallerwebi.dominio.contratos.RepositorioSolicitudTorneo;
import com.tallerwebi.dominio.contratos.RepositorioTorneo;
import com.tallerwebi.dominio.servicios.ServicioSolicitudTorneo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioSolicitudTorneoImpl implements ServicioSolicitudTorneo {

    private final RepositorioSolicitudTorneo repositorioSolicitudTorneo;
    private final RepositorioTorneo repositorioTorneo;
    private final RepositorioEquipo repositorioEquipo;

    @Autowired
    public ServicioSolicitudTorneoImpl(RepositorioSolicitudTorneo repositorioSolicitudTorneo,
                                       RepositorioTorneo repositorioTorneo,
                                       RepositorioEquipo repositorioEquipo) {
        this.repositorioSolicitudTorneo = repositorioSolicitudTorneo;
        this.repositorioTorneo = repositorioTorneo;
        this.repositorioEquipo = repositorioEquipo;
    }

    @Override
    public void solicitarIngreso(Long torneoId, Long equipoId) {
        // 1. Evitamos que se dupliquen solicitudes pendientes del mismo equipo al mismo torneo
        SolicitudTorneo existente = repositorioSolicitudTorneo.buscarPendiente(torneoId, equipoId);
        if (existente != null) {
            throw new RuntimeException("Ya existe una solicitud pendiente para este equipo en este torneo.");
        }

        // 2. Buscamos las entidades en la base de datos
        Torneo torneo = repositorioTorneo.buscarPorId(torneoId);
        Equipo equipo = repositorioEquipo.buscarPorId(equipoId);

        if (torneo == null || equipo == null) {
            throw new RuntimeException("Torneo o Equipo no encontrado.");
        }

        // 3. Guardamos la nueva solicitud en estado PENDIENTE
        SolicitudTorneo nuevaSolicitud = new SolicitudTorneo();
        nuevaSolicitud.setTorneo(torneo);
        nuevaSolicitud.setEquipo(equipo);
        nuevaSolicitud.setEstado(EstadoSolicitud.PENDIENTE);

        repositorioSolicitudTorneo.guardar(nuevaSolicitud);
    }

    @Override
    public void aceptarSolicitud(Long solicitudId) {
        // 1. Buscamos la solicitud de ingreso al torneo
        SolicitudTorneo solicitud = repositorioSolicitudTorneo.buscarPorId(solicitudId);
        if (solicitud == null) {
            throw new RuntimeException("Solicitud no encontrada.");
        }

        // 2. Cambiamos el estado de la solicitud a ACEPTADA
        solicitud.setEstado(EstadoSolicitud.ACEPTADA);
        repositorioSolicitudTorneo.actualizar(solicitud);

        // 3. Obtenemos las entidades para armar la relación física
        Torneo torneo = solicitud.getTorneo();
        Equipo equipo = solicitud.getEquipo();

        // 4. Creamos la relación TorneoEquipo usando el constructor con Deporte
        TorneoEquipo nuevaRelacion = new TorneoEquipo(torneo, equipo, torneo.getDeporte());

        // 5. Guardamos la relación en la base de datos usando el repositorio de torneo
        repositorioTorneo.guardarRelacion(nuevaRelacion);
    }

    @Override
    public void rechazarSolicitud(Long solicitudId) {
        // 1. Buscamos la solicitud de ingreso al torneo
        SolicitudTorneo solicitud = repositorioSolicitudTorneo.buscarPorId(solicitudId);
        if (solicitud == null) {
            throw new RuntimeException("Solicitud no encontrada.");
        }

        // 2. Cambiamos el estado de la solicitud a RECHAZADA
        solicitud.setEstado(EstadoSolicitud.RECHAZADA);
        repositorioSolicitudTorneo.actualizar(solicitud);
    }

    @Override
    public List<SolicitudTorneo> obtenerSolicitudesPendientesPorTorneo(Long torneoId) {
        return repositorioSolicitudTorneo.buscarPorTorneo(torneoId);
    }

    @Override
    public List<SolicitudTorneo> obtenerSolicitudesPendientesPorEquipo(Long equipoId) {
        if (equipoId == null) {
            throw new RuntimeException("El ID del equipo no puede ser nulo.");
        }

        // Obtenemos todas las solicitudes del equipo
        List<SolicitudTorneo> todas = repositorioSolicitudTorneo.buscarPorEquipo(equipoId);

        // Filtramos manualmente para quedarnos solo con las PENDIENTES

        List<SolicitudTorneo> pendientes = new java.util.ArrayList<>();
        if (todas != null) {
            for (SolicitudTorneo sol : todas) {
                if (sol.getEstado() == com.tallerwebi.dominio.Enums.EstadoSolicitud.PENDIENTE) {
                    pendientes.add(sol);
                }
            }
        }
        return pendientes;
    }
}
