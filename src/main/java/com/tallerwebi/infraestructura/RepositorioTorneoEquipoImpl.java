package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.TorneoEquipo;
import com.tallerwebi.dominio.contratos.RepositorioTorneoEquipo;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class RepositorioTorneoEquipoImpl implements RepositorioTorneoEquipo {
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<TorneoEquipo> buscarEquiposPorTorneoId(Long torneoId) {
        return sessionFactory.getCurrentSession()
                .createCriteria(TorneoEquipo.class)
                .add(Restrictions.eq("torneo.id", torneoId))
                .list();

    }

}
