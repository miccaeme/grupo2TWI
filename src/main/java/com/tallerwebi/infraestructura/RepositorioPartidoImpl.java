package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Partido;
import com.tallerwebi.dominio.contratos.RepositorioPartido;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RepositorioPartidoImpl implements RepositorioPartido {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPartidoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Partido partido) {
        sessionFactory.getCurrentSession().save(partido);
    }

    @Override
    public Partido buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Partido.class, id);
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<Partido> obtenerTodosLosPartidos() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Partido.class)
                .list();
    }
}
