package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.contratos.RepositorioJugador;
import org.hibernate.SessionFactory;
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
}
