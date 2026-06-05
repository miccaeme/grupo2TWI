package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.TorneoEquipo;
import com.tallerwebi.dominio.contratos.RepositorioEquipo;
import com.tallerwebi.dominio.Torneo;
import com.tallerwebi.dominio.contratos.RepositorioTorneo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioTorneoImpl implements RepositorioTorneo {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void guardar(Torneo torneo) {
        sessionFactory.getCurrentSession().save(torneo);
    }


    @Override
    public List<Torneo> buscarTodos() {
        return sessionFactory.getCurrentSession().createQuery("from Torneo", Torneo.class).list();
    }


    @Override
    public Torneo buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Torneo.class, id);
    }

    @Override
    public List<TorneoEquipo> buscarEquiposPorTorneoId(Long id) {
        return List.of();
    }
}