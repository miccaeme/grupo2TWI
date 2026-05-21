package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioEquipo;
import com.tallerwebi.dominio.Torneo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioTorneoImpl implements RepositorioEquipo.RepositorioTorneo {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioTorneoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override   public void guardar(Torneo torneo){
        final Session session = sessionFactory.getCurrentSession();
        session.save(torneo);
    }
    @Override
    public List<Torneo> buscarTodos(){
        final Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Torneo").list();
    }
}
