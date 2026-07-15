package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Enums.EstadoSolicitud;
import com.tallerwebi.dominio.SolicitudTorneo;
import com.tallerwebi.dominio.contratos.RepositorioSolicitudTorneo;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioSolicitudTorneoImpl implements RepositorioSolicitudTorneo {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioSolicitudTorneoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(SolicitudTorneo solicitud) {
        sessionFactory.getCurrentSession().save(solicitud);
    }

    @Override
    public void actualizar(SolicitudTorneo solicitud) {
        sessionFactory.getCurrentSession().update(solicitud);
    }

    @Override
    public SolicitudTorneo buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(SolicitudTorneo.class, id);
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<SolicitudTorneo> buscarPorTorneo(Long torneoId) {
        return (List<SolicitudTorneo>) sessionFactory.getCurrentSession()
                .createCriteria(SolicitudTorneo.class)
                .createAlias("torneo", "t")
                .add(Restrictions.eq("t.id", torneoId))
                .add(Restrictions.eq("estado", EstadoSolicitud.PENDIENTE)) // Filtramos solo las pendientes
                .list();
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<SolicitudTorneo> buscarPorEquipo(Long equipoId) {
        return sessionFactory.getCurrentSession()
                .createCriteria(SolicitudTorneo.class)
                .createAlias("equipo", "e")
                .add(Restrictions.eq("e.id", equipoId))
                .list();
    }

    @Override
    public SolicitudTorneo buscarPendiente(Long torneoId, Long equipoId) {
        return (SolicitudTorneo) sessionFactory.getCurrentSession()
                .createCriteria(SolicitudTorneo.class)
                .createAlias("torneo", "t")
                .createAlias("equipo", "e")
                .add(Restrictions.eq("t.id", torneoId))
                .add(Restrictions.eq("e.id", equipoId))
                .add(Restrictions.eq("estado", EstadoSolicitud.PENDIENTE))
                .uniqueResult();
    }
}
