package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Enums.Deporte;
import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.EquipoJugador;
import com.tallerwebi.dominio.contratos.RepositorioEquipo;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    @Override
    @SuppressWarnings("unchecked")
    public List<Equipo> buscarPorDeporte(Deporte deporte) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Equipo.class);

        criteria.add(Restrictions.eq("deporte", deporte));

        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Equipo> buscarEquiposPorJugadorIdYCapitan(Long jugadorId, boolean esCapitan) {
        // 1. Creamos el Criteria apuntando a la clase RAÍZ (EquipoJugador)
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EquipoJugador.class);

        // 2. Creamos un ALIAS para poder filtrar por los atributos del objeto interno 'jugador'
        // Esto equivale a hacer un INNER JOIN en SQL
        criteria.createAlias("jugador", "j");

        // 3. Agregamos las restricciones (Restrictions)
        criteria.add(Restrictions.eq("j.id", jugadorId)); // Filtramos por el ID del jugador usando el alias
        criteria.add(Restrictions.eq("capitan", esCapitan)); // Filtramos por el flag capitan

        // 4. Ejecutamos para obtener las relaciones intermedias
        List<EquipoJugador> relaciones = criteria.list();

        // 5. Transformamos la lista de relaciones intermedias a una lista de Equipos pura
        List<Equipo> equipos = new ArrayList<>();
        for (EquipoJugador relacion : relaciones) {
            equipos.add(relacion.getEquipo());
        }

        return equipos;
    }

}
