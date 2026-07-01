package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Enums.Posicion;
import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.EquipoJugador;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServicioEquipo {

    void crearEquipo(Equipo equipo, Long jugadorId);
    List<Equipo> buscarEquiposPorNombre(String nombre);
    Equipo buscarEquipoPorId(Long id);
    List<Equipo> listarTodos();
    List<Equipo> buscarEquiposDelCapitan(Long idJugador);
    List<EquipoJugador> obtenerJugadoresDelEquipo(Long idEquipo);
    void asignarJugadorAlEquipoPorNickname(Long equipoId, String nickname, Posicion posicion) throws Exception;
    List<Posicion> obtenerPosicionesDisponiblesParaElEquipo(Long equipoId);


    void asignarPosicionAlCapitan(Long equipoId, Long idLogueado, Posicion posicion) throws Exception;
}
