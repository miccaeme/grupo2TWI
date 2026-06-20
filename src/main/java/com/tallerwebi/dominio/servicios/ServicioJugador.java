package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Jugador;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public interface ServicioJugador {
  void crearJugador(Jugador jugador);

  Jugador buscarPorId(Long id);
}
