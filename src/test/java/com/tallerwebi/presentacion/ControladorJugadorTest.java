package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.servicios.ServicioJugador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorJugadorTest {
    private ControladorJugador controladorJugador;
    private ServicioJugador servicioJugadorMock;

    @BeforeEach
    public void init() {
        // 1. Inicializamos el mock del servicio
        this.servicioJugadorMock = mock(ServicioJugador.class);

        // 2. Creamos el controlador inyectándole ese mock manualmente
        this.controladorJugador = new ControladorJugador(this.servicioJugadorMock);

    }

    @Test
    public void siElJugadorEsValidoElRegistroEsExitoso() {
        // GIVEN (Preparación)
        Jugador jugadorValido = givenUnJugadorValido();

        // WHEN (Ejecución)
        ModelAndView modelAndView = whenCreoElJugador(jugadorValido);

        // THEN (Comprobación)
        thenElRegistroEsExitoso(modelAndView);
    }

    // ==========================================
    // Métodos Auxiliares (GIVEN - WHEN - THEN)
    // ==========================================

    private Jugador givenUnJugadorValido() {
        Jugador jugador = new Jugador();
        jugador.setNombre("Diego");
        jugador.setApellido("Maradona");
        jugador.setDni(10101010);
        return jugador;
    }

    private ModelAndView whenCreoElJugador(Jugador jugador) {
        ModelAndView mav = controladorJugador.crearJugador(jugador);
        return mav;
    }

    private void thenElRegistroEsExitoso(ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("crear-jugador"));

        String mensaje = (String) mav.getModel().get("mensaje");
        assertThat(mensaje, equalToIgnoringCase("Jugador creado correctamente"));

        // Verificamos que el servicio haya sido llamado exactamente 1 vez
        verify(servicioJugadorMock, times(1)).crearJugador(any(Jugador.class));
    }
}

