package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.contratos.RepositorioEquipo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioEquipoImpl implements RepositorioEquipo {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void guardar(Equipo equipo) {

        sessionFactory.getCurrentSession().save(equipo);
    }

    @Override
    public Equipo buscarPorId(Long id) {

        return sessionFactory.getCurrentSession().get(Equipo.class, id);
    }
}
