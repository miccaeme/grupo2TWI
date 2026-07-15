package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.TorneoEquipo;
import com.tallerwebi.dominio.contratos.RepositorioTorneoEquipo;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class RepositorioTorneoEquipoImpl implements RepositorioTorneoEquipo {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<TorneoEquipo> buscarEquiposPorTorneoId(Long torneoId) {
        return sessionFactory.getCurrentSession()
                .createCriteria(TorneoEquipo.class)
                .add(Restrictions.eq("torneo.id", torneoId))
                .list();
    }

    @Override
    public void guardar(TorneoEquipo torneoEquipo) {
        sessionFactory.getCurrentSession().save(torneoEquipo);
    }
}