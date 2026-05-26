package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EquipoJugador;
import com.tallerwebi.dominio.contratos.RepositorioEquipoJugador;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioEquipoJugadorImpl implements RepositorioEquipoJugador {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void guardar(EquipoJugador equipoJugador) {
        sessionFactory.getCurrentSession().save(equipoJugador);
    }
}
