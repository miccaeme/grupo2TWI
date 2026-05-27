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

    @Override
    public void guardar(Torneo torneo) {
        if (torneo.getFechaDeInicio() == null || !torneo.getFechaDeInicio().isAfter(LocalDate.now())){
            throw new IllegalArgumentException("La fecha del torneo debe ser posterior al dia actual");
        }

        repositorioTorneo.guardar(torneo);
    }
    public ServicioTorneoImpl(RepositorioTorneo repositorioTorneo, RepositorioEquipo repositorioEquipo) {
        this.repositorioTorneo = repositorioTorneo;
        this.repositorioEquipo = repositorioEquipo;
    }

    @Override
    public List<Torneo> buscarTodos() {
        return repositorioTorneo.buscarTodos();
    }

    @Override
    public void asignarEquipos(Long id, List<Long> equiposIds) {
        Torneo torneo = repositorioTorneo.buscarPorId(id);
        if(torneo != null){
            List<Equipo> equiposAAsignar= new ArrayList<>();

            for(Long equipoId : equiposIds){
                if(equipoId != null){
                    Equipo equipo = repositorioEquipo.buscarPorId(equipoId);
                    if(equipo != null && !equiposAAsignar.contains(equipo)){
                        equiposAAsignar.add(equipo);
                    }
                }
            }
            torneo.setEquipos(equiposAAsignar);

            repositorioTorneo.actualizar(torneo);
        }

    }


    @Override
    public Torneo buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Torneo.class, id);
    }
}
