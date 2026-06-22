package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.contratos.RepositorioJugador;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioJugadorImpl implements RepositorioJugador {

  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public void save(Jugador jugador) {

    sessionFactory.getCurrentSession().save(jugador);
  }

  @Override
  public Jugador buscarPorId(Long id) {

    return sessionFactory.getCurrentSession().get(Jugador.class,id);
  }
  @Override
  public Jugador buscarPorNickname(String nickname) {

    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Jugador.class);

    criteria.add(Restrictions.eq("nickname", nickname));

    return (Jugador) criteria.uniqueResult();
  }
}
