package com.tallerwebi.dominio;

import com.tallerwebi.dominio.contratos.RepositorioEquipo;
import com.tallerwebi.dominio.contratos.RepositorioTorneo;
import com.tallerwebi.dominio.servicios.ServicioEquipo;
import com.tallerwebi.dominio.servicios.ServicioTorneo;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.SessionFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioTorneoImpl implements ServicioTorneo {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private RepositorioTorneo repositorioTorneo;
    @Autowired
    private ServicioEquipo servicioEquipo;
    @Autowired
    private RepositorioEquipo repositorioEquipo;


    public ServicioTorneoImpl(RepositorioTorneo repositorioTorneo, RepositorioEquipo repositorioEquipo, SessionFactory sessionFactory) {
        this.repositorioTorneo = repositorioTorneo;
        this.repositorioEquipo = repositorioEquipo;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Torneo torneo) {
        if (torneo.getFechaDeInicio() == null || !torneo.getFechaDeInicio().isAfter(LocalDate.now())){
            throw new IllegalArgumentException("La fecha del torneo debe ser posterior al dia actual");
        }

        repositorioTorneo.guardar(torneo);
    }


    @Override
    public List<Torneo> buscarTodos() {
        return repositorioTorneo.buscarTodos();
    }

    @Override
    public Torneo buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Torneo.class, id);
    }

    @Override
    public List<TorneoEquipo> buscarEquiposPorTorneoId(Long id) {
        return List.of();
    }

    @Override
    public void asignarEquipos(Long id, List<Long> equiposIds){
        Torneo torneo = repositorioTorneo.buscarPorId(id);
        if(torneo!=null){

            List<TorneoEquipo> relacionesExistentes = repositorioTorneo.buscarEquiposPorTorneoId(id);
            for(Long equiposId : equiposIds){
                if(equiposId!=null){

                    boolean yaEstaAsignado = false;
                    for(TorneoEquipo relacion : relacionesExistentes){
                        if(relacion.getEquipo().getId().equals(equiposId)){
                            yaEstaAsignado = true;
                            break;
                        }
                    }
                    if(!yaEstaAsignado){
                        Equipo equipo = repositorioEquipo.buscarPorId(equiposId);
                        if(equipo!=null){
                            TorneoEquipo nuevaAsignacion = new TorneoEquipo(torneo, equipo);

                            sessionFactory.getCurrentSession().persist(nuevaAsignacion);
                        }
                    }
                }
            }
            //forzar a sql a guardar
            sessionFactory.getCurrentSession().flush();
        }
    }




}
