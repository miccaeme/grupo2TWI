package com.tallerwebi.dominio.contratos;

import com.tallerwebi.dominio.Enums.TipoEstadistica;
import com.tallerwebi.dominio.Estadistica;

import java.util.List;

public interface RepositorioEstadistica {

    void guardar(Estadistica estadistica);

    List<Estadistica> buscarPorJugadorYPartido(Long idJugador, Long idPartido);

    List<Estadistica> buscarPorJugador(Long idJugador);

    List<Estadistica> buscarPorPartido(Long idPartido);

    int contarPorNicknameYTipo(String nickname, TipoEstadistica tipo);
}
