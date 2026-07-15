package com.tallerwebi.dominio;

import com.tallerwebi.dominio.contratos.RepositorioEquipo;
import com.tallerwebi.dominio.contratos.RepositorioTorneo;
import com.tallerwebi.dominio.contratos.RepositorioTorneoEquipo;
import com.tallerwebi.dominio.contratos.RepositorioUsuario;
import com.tallerwebi.dominio.servicios.ServicioEquipo;
import com.tallerwebi.dominio.servicios.ServicioTorneo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioTorneoImpl implements ServicioTorneo {

    @Autowired
    private RepositorioTorneo repositorioTorneo;

    @Autowired
    private RepositorioEquipo repositorioEquipo;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioTorneoEquipo repositorioTorneoEquipo;


    @Autowired
    public ServicioTorneoImpl(RepositorioTorneo repositorioTorneo, RepositorioEquipo repositorioEquipo, RepositorioTorneoEquipo repositorioTorneoEquipo) {
        this.repositorioTorneo = repositorioTorneo;
        this.repositorioEquipo = repositorioEquipo;
        this.repositorioTorneoEquipo = repositorioTorneoEquipo;
    }


    @Override
    public void guardar(Torneo torneo, Long idUsuarioLogueado) {
        if (torneo.getFechaDeInicio() == null || !torneo.getFechaDeInicio().isAfter(LocalDate.now())){
            throw new IllegalArgumentException("La fecha del torneo debe ser posterior al dia actual");
        }
        Usuario creador = repositorioUsuario.buscarUsuarioPorId(idUsuarioLogueado);
        torneo.setCreador(creador);

        repositorioTorneo.guardar(torneo);
    }

    @Override
    public void guardarEstadoFixture(Torneo torneo) {

        repositorioTorneo.guardar(torneo);
    }

    @Override
    public List<Torneo> buscarTodos() {
        return repositorioTorneo.buscarTodos();
    }

    @Override
    public Torneo buscarPorId(Long id) {
        return repositorioTorneo.buscarPorId(id);
    }

    @Override
    public List<TorneoEquipo> buscarEquiposPorTorneoId(Long id) {
        return repositorioTorneoEquipo.buscarEquiposPorTorneoId(id);
    }

    @Override
    public List<Torneo> obtenerTodosLosTorneos() {
        // Usamos el repositorio de torneos para traer la lista completa
        return repositorioTorneo.obtenerTodos();
    }
    @Override
    public List<Torneo> buscarTorneosDelOrganizador(Long idUsuario) {
        // 1. Validamos que el ID no sea nulo por seguridad
        if (idUsuario == null) {
            return new ArrayList<>();
        }

        // 2. Le pedimos al repositorio que busque en la base de datos
        // todos los torneos que tengan como organizador a este usuario
        return repositorioTorneo.buscarTorneosPorOrganizadorId(idUsuario);
    }

    @Override
    public void asignarEquipos(Long id, List<Long> equiposIds){
        Torneo torneo = repositorioTorneo.buscarPorId(id);
        if(torneo != null){

            // Buscamos las relaciones existentes usando el método limpio
            List<TorneoEquipo> relacionesExistentes = buscarEquiposPorTorneoId(id);

            for(Long equiposId : equiposIds){
                if(equiposId != null){

                    boolean yaEstaAsignado = false;
                    for(TorneoEquipo relacion : relacionesExistentes){
                        if(relacion.getEquipo().getId().equals(equiposId)){
                            yaEstaAsignado = true;
                            break;
                        }
                    }

                    if(!yaEstaAsignado){
                        Equipo equipo = repositorioEquipo.buscarPorId(equiposId);
                        if(equipo != null){
                            TorneoEquipo nuevaAsignacion = new TorneoEquipo(torneo, equipo, torneo.getDeporte());
                            repositorioTorneo.guardarRelacion(nuevaAsignacion);
                        }
                    }
                }
            }
        }
    }
}