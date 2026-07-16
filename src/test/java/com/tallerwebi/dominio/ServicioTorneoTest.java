package com.tallerwebi.dominio;

import com.tallerwebi.dominio.contratos.RepositorioEquipo;
import com.tallerwebi.dominio.contratos.RepositorioTorneo;
import com.tallerwebi.dominio.contratos.RepositorioTorneoEquipo;
import com.tallerwebi.dominio.contratos.RepositorioUsuario;
import com.tallerwebi.dominio.Enums.Deporte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioTorneoTest {

    private ServicioTorneoImpl servicioTorneo;

    private RepositorioTorneo repositorioTorneoMock;
    private RepositorioEquipo repositorioEquipoMock;
    private RepositorioTorneoEquipo repositorioTorneoEquipoMock;
    private RepositorioUsuario repositorioUsuarioMock;

    private Torneo torneoValido;
    private Torneo torneoInvalido;

    @BeforeEach
    public void init() {
        this.repositorioTorneoMock = mock(RepositorioTorneo.class);
        this.repositorioEquipoMock = mock(RepositorioEquipo.class);
        this.repositorioTorneoEquipoMock = mock(RepositorioTorneoEquipo.class);
        this.repositorioUsuarioMock = mock(RepositorioUsuario.class);

        // Instanciamos el servicio usando su constructor
        this.servicioTorneo = new ServicioTorneoImpl(
                this.repositorioTorneoMock,
                this.repositorioEquipoMock,
                this.repositorioTorneoEquipoMock
        );

        // Inyectamos por reflexión el repositorio de usuarios que se usa en guardar()
        org.springframework.test.util.ReflectionTestUtils.setField(servicioTorneo, "repositorioUsuario", repositorioUsuarioMock);

        // Configuración de torneos base para las pruebas de fechas
        torneoValido = new Torneo();
        torneoValido.setFechaDeInicio(LocalDate.now().plusDays(2));
        torneoValido.setDeporte(Deporte.FUTBOL);

        torneoInvalido = new Torneo();
        torneoInvalido.setFechaDeInicio(LocalDate.now().minusDays(1));
    }

    @Test
    public void siLaFechaEsIgualOAnteriorAlDiaActualDebeLanzarExcepcionYnoGuardar(){
        // GIVEN
        Long idUsuarioMock = 1L;
        when(repositorioUsuarioMock.buscarUsuarioPorId(idUsuarioMock)).thenReturn(new Usuario());

        // WHEN & THEN
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            servicioTorneo.guardar(torneoInvalido, idUsuarioMock);
        });

        assertEquals("La fecha del torneo debe ser posterior al dia actual", excepcion.getMessage());
        verify(repositorioTorneoMock, never()).guardar(any(Torneo.class));
    }

    @Test
    public void siElTorneoSeCreaExitosamenteSePuedenAsignarEquipos(){
        // GIVEN
        Long torneoId = 1L;
        Torneo torneoTest = new Torneo();
        torneoTest.setId(torneoId);
        torneoTest.setDeporte(Deporte.FUTBOL);

        when(this.repositorioTorneoMock.buscarPorId(torneoId)).thenReturn(torneoTest);

        // En tu servicio: buscarEquiposPorTorneoId(id) llama internamente a repositorioTorneoEquipo
        when(this.repositorioTorneoEquipoMock.buscarEquiposPorTorneoId(torneoId)).thenReturn(new ArrayList<>());

        List<Long> equiposIds = List.of(10L, 20L);

        Equipo equipo1 = new Equipo();
        equipo1.setId(10L);
        when(this.repositorioEquipoMock.buscarPorId(10L)).thenReturn(equipo1);

        Equipo equipo2 = new Equipo();
        equipo2.setId(20L);
        when(this.repositorioEquipoMock.buscarPorId(20L)).thenReturn(equipo2);

        // WHEN
        servicioTorneo.asignarEquipos(torneoId, equiposIds);

        // THEN - Verificamos sobre el repo que realmente llama el servicio
        verify(this.repositorioTorneoMock, times(2)).guardarRelacion(any(TorneoEquipo.class));
    }

  /*  @Test
    public void siSeAsignanEquiposDuplicadosAlTorneoSoloSeDeberiaAgregarUnaVezAlTorneo(){
        // GIVEN
        Long torneoId = 3L;
        Torneo torneoTestAgregacion = new Torneo();
        torneoTestAgregacion.setId(torneoId);
        torneoTestAgregacion.setDeporte(Deporte.FUTBOL);

        when(this.repositorioTorneoMock.buscarPorId(torneoId)).thenReturn(torneoTestAgregacion);
        when(this.repositorioTorneoEquipoMock.buscarEquiposPorTorneoId(torneoId)).thenReturn(new ArrayList<>());

        Equipo equipo1 = new Equipo();
        equipo1.setId(30L);
        when(this.repositorioEquipoMock.buscarPorId(30L)).thenReturn(equipo1);

        // Enviamos intencionalmente el mismo ID duplicado en la lista
        List<Long> equiposIdsDuplicados = List.of(30L, 30L);

        // WHEN
        servicioTorneo.asignarEquipos(torneoId, equiposIdsDuplicados);

<<<<<<< HEAD
        // THEN - Verificamos que solo se haya guardado 1 vez gracias al filtro de duplicados
        verify(this.repositorioTorneoMock, times(1)).guardarRelacion(any(TorneoEquipo.class));
    }
=======
        // THEN
        verify(repositorioTorneoMock, times(1)).guardarRelacion(any(TorneoEquipo.class));
    }*/
}