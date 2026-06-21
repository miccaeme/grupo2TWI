package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.EquipoJugador;
import com.tallerwebi.dominio.contratos.RepositorioEquipoJugador;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioEquipoJugadorImpl implements RepositorioEquipoJugador {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioEquipoJugadorImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void guardar(EquipoJugador equipoJugador) {

        sessionFactory.getCurrentSession().save(equipoJugador);
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<Equipo> buscarEquiposPorJugadorYCapitan(Long idJugador, boolean esCapitan) {


        return sessionFactory.getCurrentSession().
                createCriteria(EquipoJugador.class, "ej")
                .createAlias("ej.jugador", "j")
                .add(Restrictions.eq("j.id", idJugador))
                .add(Restrictions.eq("ej.capitan", esCapitan))
                .setProjection(Projections.property("ej.equipo")) // uso el set porque asi me traigo una columna especifica en vez de traerme toda la fila
                .list();

    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<EquipoJugador> buscarJugadoresPorEquipo(Long idEquipo) {

        return sessionFactory.getCurrentSession()
                .createCriteria(EquipoJugador.class, "ej")
                .createAlias("ej.equipo", "e")
                .add(Restrictions.eq("e.id", idEquipo))
                .list();
    }

    @Override
    public int contarJugadoresEnEquipo(Long equipoId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EquipoJugador.class);
        criteria.createAlias("equipo", "e");
        criteria.add(Restrictions.eq("e.id", equipoId));

        // Contamos las filas devueltas (nivel principiante: traemos la lista y vemos su size)
        return criteria.list().size();
    }

    @Override
    public boolean elJugadorYaEstaEnElEquipo(Long equipoId, Long jugadorId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EquipoJugador.class);
        criteria.createAlias("equipo", "e");
        criteria.createAlias("jugador", "j");

        criteria.add(Restrictions.eq("e.id", equipoId));
        criteria.add(Restrictions.eq("j.id", jugadorId));

        List<?> resultado = criteria.list();
        return !resultado.isEmpty(); // Si no está vacía, es porque ya juega en este equipo
    }

}
