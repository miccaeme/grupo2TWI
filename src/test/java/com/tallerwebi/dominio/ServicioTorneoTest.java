package com.tallerwebi.dominio;

import com.tallerwebi.dominio.contratos.RepositorioTorneo;
import com.tallerwebi.dominio.servicios.ServicioTorneo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

// ServicioTorneo servicioTorneo = new ServicioTorneoImpl();

public class ServicioTorneoTest {

    @Mock
    private RepositorioTorneo repositorioTorneo;

    @InjectMocks
    private ServicioTorneoImpl servicioTorneo;
    private Torneo torneoValido;
    private Torneo torneoInvalido;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
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
        verify(repositorioTorneo, times(1)).guardar(torneoValido);

    }

    @Test
    public void siLaFechaEsIgualOAnteriorAlDiaActualDebeLanzarexcepcionYnoGuardar(){
        // given: Torneo Invalido - fecha pasada


        // WHEN: Cuando intento guardar el torneo inválido en el servicio...

        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class,() -> {
        servicioTorneo.guardar(torneoInvalido); });

        //then: se valida excepcion y que el repositorio no haya sido guardado
    assertEquals("La fecha del torneo debe ser posterior al dia actual", excepcion.getMessage());
    verify(repositorioTorneo, never()).guardar(any(Torneo.class));
    }
}
