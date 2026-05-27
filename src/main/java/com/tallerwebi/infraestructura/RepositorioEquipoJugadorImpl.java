package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.EquipoJugador;
import com.tallerwebi.dominio.contratos.RepositorioEquipoJugador;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioEquipoJugadorImpl implements RepositorioEquipoJugador {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void guardar(EquipoJugador equipoJugador) {
        sessionFactory.getCurrentSession().save(equipoJugador);
    }

    @Override
    public List<Equipo> buscarEquiposPorJugadorYCapitan(Long idJugador, boolean esCapitan) {
        String hql = "SELECT ej.equipo FROM EquipoJugador ej " +
                "WHERE ej.jugador.id = :idJugador AND ej.capitan = :esCapitan";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, Equipo.class)
                .setParameter("idJugador", idJugador)
                .setParameter("esCapitan", esCapitan)
                .getResultList();
    }

    @Override
    public List<EquipoJugador> buscarJugadoresPorEquipo(Long idEquipo) {
        String hql = "FROM EquipoJugador ej WHERE ej.equipo.id = :idEquipo";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, EquipoJugador.class)
                .setParameter("idEquipo", idEquipo)
                .getResultList();
    }
}
