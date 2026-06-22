package com.tallerwebi.dominio.contratos;

import com.tallerwebi.dominio.Jugador;

public interface RepositorioJugador {
  void save(Jugador jugador);

  Jugador buscarPorId(Long id);

    Jugador buscarPorNickname(String nickname);
}
