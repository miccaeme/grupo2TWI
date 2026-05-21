package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Enums.EstadoSolicitud;
import com.tallerwebi.dominio.RepositorioSolicitudUnion;
import com.tallerwebi.dominio.SolicitudUnion;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioSolicitudUnionImpl implements RepositorioSolicitudUnion {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioSolicitudUnionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(SolicitudUnion solicitud) {
        sessionFactory.getCurrentSession().save(solicitud);
    }

    @Override
    public void actualizar(SolicitudUnion solicitud) {
        sessionFactory.getCurrentSession().update(solicitud);
    }

    @Override
    public SolicitudUnion buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(SolicitudUnion.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SolicitudUnion> buscarPendientesPorEquipo(Long equipoId) {
        return sessionFactory
                .getCurrentSession()
                .createCriteria(SolicitudUnion.class)
                .createAlias("equipo", "eq")
                .add(Restrictions.eq("eq.id", equipoId))
                .add(Restrictions.eq("estado", EstadoSolicitud.PENDIENTE))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SolicitudUnion> buscarPorJugador(Long jugadorId) {
        return sessionFactory
                .getCurrentSession()
                .createCriteria(SolicitudUnion.class)
                .createAlias("jugador", "jug")
                .add(Restrictions.eq("jug.id", jugadorId))
                .list();
    }

    @Override
    public boolean existeSolicitudPendiente(Long jugadorId, Long equipoId) {
        return sessionFactory
                .getCurrentSession()
                .createCriteria(SolicitudUnion.class)
                .createAlias("jugador", "jug")
                .createAlias("equipo", "eq")
                .add(Restrictions.eq("jug.id", jugadorId))
                .add(Restrictions.eq("eq.id", equipoId))
                .add(Restrictions.eq("estado", EstadoSolicitud.PENDIENTE))
                .uniqueResult() != null;
    }
}