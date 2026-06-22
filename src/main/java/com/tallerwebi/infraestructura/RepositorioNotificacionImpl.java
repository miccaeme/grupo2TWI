package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Notificacion;
import com.tallerwebi.dominio.contratos.RepositorioNotificacion;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioNotificacionImpl implements RepositorioNotificacion {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioNotificacionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Notificacion notificacion) {
        this.sessionFactory.getCurrentSession().save(notificacion);
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Notificacion> obtenerNotificacionesPorJugador(String nickname) {
        return this.sessionFactory.getCurrentSession().createCriteria(Notificacion.class).createAlias("jugador", "j").add(Restrictions.eq("j.nickname", nickname)).list();
    }
}
