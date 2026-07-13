package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Enums.TipoEstadistica;
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
                .createAlias("est.jugador", "j")
                .createAlias("est.partido", "p")
                .add(Restrictions.eq("j.id", idJugador))
                .add(Restrictions.eq("p.id", idPartido))
                .list();
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<Estadistica> buscarPorJugador(Long idJugador) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Estadistica.class, "est")
                .createAlias("est.jugador", "j")
                .add(Restrictions.eq("j.id", idJugador))
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

    @Override
    public int contarPorNicknameYTipo(String nickname, TipoEstadistica tipo) {
        try {

            var criteria = this.sessionFactory.getCurrentSession().createCriteria(Estadistica.class);


            criteria.createAlias("jugador", "j");

            // Filtramos por el nickname del jugador y por el tipo de estadística
            criteria.add(org.hibernate.criterion.Restrictions.eq("j.nickname", nickname));
            criteria.add(org.hibernate.criterion.Restrictions.eq("tipo", tipo));

            // Le decimos a Hibernate que haga un COUNT
            criteria.setProjection(org.hibernate.criterion.Projections.rowCount());

            // Obtenemos el único resultado (un Long o Integer dependiendo de la versión de Hibernate)
            Number resultado = (Number) criteria.uniqueResult();

            return resultado != null ? resultado.intValue() : 0;

        } catch (Exception e) {
            // Si hay alguna falla o no encuentra nada, devolvemos 0 de forma segura
            return 0;
        }
    }
}
