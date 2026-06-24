package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Estadistica;
import com.tallerwebi.dominio.contratos.RepositorioEstadistica;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hsqldb.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioEstadisticaImpl implements RepositorioEstadistica {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioEstadisticaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Estadistica estadistica) {
        sessionFactory.getCurrentSession().save(estadistica);
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<Estadistica> buscarPorJugadorYPartido(Long idJugador, Long idPartido) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Estadistica.class, "est")
                .createAlias("est.jugador", "j")  // Join con Jugador
                .createAlias("est.partido", "p")  // Join con Partido (Fixture)
                .add(Restrictions.eq("j.id", idJugador))  // Filtro por Jugador
                .add(Restrictions.eq("p.id", idPartido))  // Filtro por Partido
                .list();
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<Estadistica> buscarPorJugador(Long idJugador) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Estadistica.class, "est")
                .createAlias("est.jugador", "j")
                .add(Restrictions.eq("j.id", idJugador)) // Filtramos SOLO por el jugador
                .list();
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<Estadistica> buscarPorPartido(Long idPartido) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Estadistica.class, "est")
                .createAlias("est.partido", "p")
                .add(Restrictions.eq("p.id", idPartido))
                .list();
    }
}
