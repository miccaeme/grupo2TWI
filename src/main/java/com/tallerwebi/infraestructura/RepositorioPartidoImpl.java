package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.EquipoJugador;
import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.Partido;
import com.tallerwebi.dominio.contratos.RepositorioPartido;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioPartidoImpl implements RepositorioPartido {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPartidoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Partido partido) {
        sessionFactory.getCurrentSession().saveOrUpdate(partido);
        sessionFactory.getCurrentSession().flush();
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

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<Partido> buscarPartidosPorTorneoId(Long idTorneo) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Partido.class)
                .add(Restrictions.eq("torneo.id", idTorneo))
                .list();
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<Jugador> obtenerJugadores(Long idPartido, String bando) {
        Partido partido = sessionFactory.getCurrentSession().get(Partido.class, idPartido);
        if (partido == null) return List.of();

        Equipo equipoSeleccionado = null;
        if ("LOCAL".equals(bando)) {
            equipoSeleccionado = partido.getEquipoLocal();
        } else if ("VISITANTE".equals(bando)) {
            equipoSeleccionado = partido.getEquipoVisitante();
        }

        if (equipoSeleccionado == null || equipoSeleccionado.getId() == null) {
            return List.of();
        }

        return sessionFactory.getCurrentSession()
                .createCriteria(EquipoJugador.class)
                .add(Restrictions.eq("equipo.id", equipoSeleccionado.getId()))
                .setProjection(Projections.property("jugador"))
                .list();
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public void eliminarPartidosPorTorneoId(Long idTorneo) {
        var session = sessionFactory.getCurrentSession();
        List<Partido> partidosDelTorneo = session.createCriteria(Partido.class)
                .add(Restrictions.eq("torneo.id", idTorneo))
                .list();

        for (Partido partido : partidosDelTorneo) {
            session.delete(partido);
        }
    }
}