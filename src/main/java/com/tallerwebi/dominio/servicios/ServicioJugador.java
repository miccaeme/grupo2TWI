package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Jugador;

public interface ServicioJugador {
  void crearJugador(Jugador jugador);

  Jugador buscarPorId(Long id);
}
