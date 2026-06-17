package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Partido;
import com.tallerwebi.dominio.contratos.RepositorioPartido;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioPartidoImpl implements RepositorioPartido {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPartidoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Partido partido) {

        sessionFactory.getCurrentSession().save(partido);
    }

    @Override
    public Partido buscarPorId(Long id) {

        return sessionFactory.getCurrentSession().get(Partido.class, id);
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<Partido> obtenerTodosLosPartidos() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Partido.class)
                .list();
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<Partido> buscarPartidosPorTorneoId(Long idTorneo) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Partido.class)
                .add(Restrictions.eq("torneo.id", idTorneo)) // filtro por la propiedad torneo.id de la entidad
                .list();
    }


    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public void eliminarPartidosPorTorneoId(Long idTorneo) {
        var session = sessionFactory.getCurrentSession();


        List<Partido> partidosDelTorneo = session.createCriteria(Partido.class)
                .add(Restrictions.eq("torneo.id", idTorneo))
                .list();

        // recorre la lista y borra uno por uno
        for (Partido partido : partidosDelTorneo) {
            session.delete(partido);
        }
    }
}
