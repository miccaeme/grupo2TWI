package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Enums.EstadoSolicitud;
import com.tallerwebi.dominio.Notificacion;
import com.tallerwebi.dominio.SolicitudUnion;
import com.tallerwebi.dominio.contratos.RepositorioSolicitud;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioSolicitudImpl implements RepositorioSolicitud {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioSolicitudImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(SolicitudUnion solicitud) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(solicitud);

    }

    @Override
    public SolicitudUnion buscarPorId(Long id) {
        return this.sessionFactory.getCurrentSession().get(SolicitudUnion.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SolicitudUnion> buscarPendientesPorEquipo(Long equipoId) {
        return this.sessionFactory.getCurrentSession().createCriteria(SolicitudUnion.class).createAlias("equipo", "e").add(Restrictions.eq("e.id", equipoId)).add(Restrictions.eq("estado", EstadoSolicitud.PENDIENTE)).list();
    }
}
