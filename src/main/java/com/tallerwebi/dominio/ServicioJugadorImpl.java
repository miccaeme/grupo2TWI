package com.tallerwebi.dominio;

import com.tallerwebi.dominio.contratos.RepositorioJugador;
import com.tallerwebi.dominio.servicios.ServicioJugador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioJugadorImpl implements ServicioJugador {

  @Autowired
  private RepositorioJugador repositorioJugador;

  @Override
  public void crearJugador(Jugador jugador) {
    repositorioJugador.save(jugador);
  }

  @Override
  public Jugador buscarPorId(Long id) {

    return repositorioJugador.buscarPorId(id);
  }
}
