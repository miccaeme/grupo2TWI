package com.tallerwebi.dominio;

import com.tallerwebi.dominio.contratos.RepositorioEquipo;
import com.tallerwebi.dominio.contratos.RepositorioTorneo;
import com.tallerwebi.dominio.servicios.ServicioTorneo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// ServicioTorneo servicioTorneo = new ServicioTorneoImpl();

public class ServicioTorneoTest {

    private ServicioTorneoImpl servicioTorneo;
    private RepositorioTorneo repositorioTorneoMock;
    private RepositorioEquipo repositorioEquipoMock;


    private Torneo torneoValido;
    private Torneo torneoInvalido;

    @BeforeEach
    public void init() {
        this.repositorioTorneoMock = mock(RepositorioTorneo.class);
        this.repositorioEquipoMock = mock(RepositorioEquipo.class);

        this.servicioTorneo = new ServicioTorneoImpl(this.repositorioTorneoMock, this.repositorioEquipoMock);

        torneoValido = new Torneo();
        torneoValido.setFechaDeInicio(LocalDate.now().plusDays(2)); //posterior a Hoy

        torneoInvalido = new Torneo();
        torneoInvalido.setFechaDeInicio(LocalDate.now().minusDays(1)); //pasada o igual a hoy.
}
    @Test
    public void siLaFechaEsPosteriorAlDiaActualSeGuardeElTorneoExitosamente(){

        // given: Torneo Valido
        //when;
        servicioTorneo.guardar(torneoValido);

        // THEN:El repositorio invoco 1 vez para guardar torneo
        verify(repositorioTorneoMock, times(1)).guardar(torneoValido);

    }

    @Test
    public void siLaFechaEsIgualOAnteriorAlDiaActualDebeLanzarexcepcionYnoGuardar(){
        // given: Torneo Invalido - fecha pasada


        // WHEN: Cuando intento guardar el torneo inválido en el servicio...

        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class,() -> {
        servicioTorneo.guardar(torneoInvalido); });

        //then: se valida excepcion y que el repositorio no haya sido guardado
        assertEquals("La fecha del torneo debe ser posterior al dia actual", excepcion.getMessage());
        verify(repositorioTorneoMock, never()).guardar(any(Torneo.class));
    }

    @Test
    public void sielTorneoSecreaExitosamenteSePuedenAsignarEquipos(){
        //Given
        Long torneoId = 1L;
        Torneo torneoTest = new Torneo();
        torneoTest.setId(torneoId);

        torneoTest.setEquipos(new ArrayList<>());

        when(this.repositorioTorneoMock.buscarPorId(torneoId)).thenReturn(torneoTest);

        List<Long> equiposIds= List.of(10L, 20L);
        Equipo equipo1 = new Equipo();
        equipo1.setId(10L);
        when(this.repositorioEquipoMock.buscarPorId(10L)).thenReturn(equipo1);

        Equipo equipo2 = new Equipo();
        equipo2.setId(20L);
        when(this.repositorioEquipoMock.buscarPorId(20L)).thenReturn(equipo2);


        //When:
        servicioTorneo.asignarEquipos(torneoId,equiposIds);

        //Then:
        assertEquals(2, torneoTest.getEquipos().size());
        assertTrue(torneoTest.getEquipos().contains(equipo1));
        assertTrue(torneoTest.getEquipos().contains(equipo2));

    }
    @Test
    public void siSeAsignanEquiposDuplicadosAlTorneoSoloSeDeberiaAgregarUnaVezAlTorneo(){
        Long torneoId = 3L;
        Torneo torneoTestAgregacion = new Torneo();
        torneoTestAgregacion.setId(torneoId);
        torneoTestAgregacion.setEquipos(new ArrayList<>());

        when(this.repositorioTorneoMock.buscarPorId(torneoId)).thenReturn(torneoTestAgregacion);

        List<Long> equiposIdsDuplicados = List.of(30L, 30L);

        Equipo equipo1 = new Equipo();
        equipo1.setId(30L);

        when(this.repositorioEquipoMock.buscarPorId(30L)).thenReturn(equipo1 );

        //When
        servicioTorneo.asignarEquipos(torneoId,equiposIdsDuplicados);

        assertEquals(1, torneoTestAgregacion.getEquipos().size(), "La lista no debe contener duplicados");
        assertTrue(torneoTestAgregacion.getEquipos().contains(equipo1));


    }
}
