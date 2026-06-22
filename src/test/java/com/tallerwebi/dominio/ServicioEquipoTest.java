package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.Posicion;
import com.tallerwebi.dominio.contratos.*;
import com.tallerwebi.dominio.servicios.ServicioEquipo;
import com.tallerwebi.dominio.servicios.ServicioNotificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ServicioEquipoTest {
    private ServicioEquipo servicioEquipo;


    private RepositorioEquipo repositorioEquipoMock;
    private RepositorioJugador repositorioJugadorMock;
    private RepositorioEquipoJugador repositorioEquipoJugadorMock;
    private RepositorioUsuario repositorioUsuarioMock;
    private RepositorioNotificacion repositorioNotificacionMock;
    private ServicioNotificacion   servicioNotificacionMock;

    @BeforeEach
    public void init() {

        this.repositorioEquipoMock = mock(RepositorioEquipo.class);
        this.repositorioJugadorMock = mock(RepositorioJugador.class);
        this.repositorioEquipoJugadorMock = mock(RepositorioEquipoJugador.class);
        this.repositorioUsuarioMock = mock(RepositorioUsuario.class);
        this.repositorioNotificacionMock = mock(RepositorioNotificacion.class);
        this.servicioNotificacionMock = mock(ServicioNotificacion.class);

        // 2. Instans la impl real del servicio pasándole los mocks por construct
        this.servicioEquipo = new ServicioEquipoImpl(
                this.repositorioEquipoMock,
                this.repositorioJugadorMock,
                this.repositorioEquipoJugadorMock,
               this.repositorioUsuarioMock,
                this.servicioNotificacionMock

        );
    }
/*
    @Test
    public void siElCapitanAsignaUnJugadorAlEquipoSeGuardaLaRelacionCorrectamente() {
        // GIVEN
        Long idEquipo = 1L;
        Long idJugadorAsignado = 2L;
        Posicion posicion = Posicion.DELANTERO;

        Equipo equipoMock = givenUnEquipoExistente();
        Jugador jugadorMock = givenUnJugadorExistente();

        // Configuración de los mocks: cuando el servicio los busque, devolvemos los objetos de arriba
        when(repositorioEquipoMock.buscarPorId(idEquipo)).thenReturn(equipoMock);
        when(repositorioJugadorMock.buscarPorId(idJugadorAsignado)).thenReturn(jugadorMock);

        // WHEN
        servicioEquipo.asignarJugadorAlEquipo(idEquipo, idJugadorAsignado, posicion);

        // THEN
        thenLaRelacionEquipoJugadorSeGuardaCorrectamente();
    }

    // ==========================================
    // Métodos Auxiliares (GIVEN - WHEN - THEN)
    // ==========================================

    private Equipo givenUnEquipoExistente() {
        Equipo equipo = new Equipo();
        equipo.setId(1L);
        equipo.setNombre("Los Salchichas");
        return equipo;
    }

    private Jugador givenUnJugadorExistente() {
        Jugador jugador = new Jugador();
        jugador.setId(2L);
        jugador.setNombre("Pulga");
        return jugador;
    }

    private void thenLaRelacionEquipoJugadorSeGuardaCorrectamente() {
        verify(repositorioEquipoJugadorMock, times(1)).guardar(any(EquipoJugador.class));
    }
*/
}
