package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.contratos.RepositorioEquipo;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioEquipoImpl implements RepositorioEquipo {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioEquipoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Equipo equipo) {

        sessionFactory.getCurrentSession().save(equipo);
    }

    @Override
    public Equipo buscarPorId(Long id) {

        return sessionFactory.getCurrentSession().get(Equipo.class, id);
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<Equipo> findAll() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Equipo.class).list();

    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<Equipo> buscarPorNombre(String nombre) {
        return sessionFactory.getCurrentSession().
                createCriteria(Equipo.class)
                .add(Restrictions.ilike("nombre",
                        "%" + nombre + "%")).list();
    }
}
