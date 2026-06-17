package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Partido;
import com.tallerwebi.dominio.excepcion.PartidoInvalidoException;
import com.tallerwebi.dominio.servicios.ServicioEstadistica;
import com.tallerwebi.dominio.servicios.ServicioPartido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorPartido {

    private ServicioPartido servicioPartido;
    private ServicioEstadistica servicioEstadistica;

    @Autowired
    public ControladorPartido(ServicioPartido servicioPartido, ServicioEstadistica servicioEstadistica) {
        this.servicioPartido = servicioPartido;
        this.servicioEstadistica = servicioEstadistica;
    }

    // 📅 1. PANTALLA PRINCIPAL: Listar el Fixture
    @RequestMapping(path = "/fixture", method = RequestMethod.GET)
    public ModelAndView verFixture() {
        ModelMap modelo = new ModelMap();

        List<Partido> partidos = servicioPartido.listarFixture();
        modelo.put("listaPartidos", partidos);

        return new ModelAndView("fixture", modelo); // renderiza fixture.html
    }


    @RequestMapping(path = "/partido/nuevo", method = RequestMethod.GET)
    public ModelAndView irACrearPartido() {
        ModelMap modelo = new ModelMap();
        modelo.put("partido", new Partido());
        // Aquí podrías pasar la lista de equipos desde un servicioEquipo si lo necesitás para los combos select
        return new ModelAndView("crear-partido", modelo); // renderiza crear-partido.html
    }


    @RequestMapping(path = "/partido/guardar", method = RequestMethod.POST)
    public ModelAndView guardarPartido(@ModelAttribute("partido") Partido partido) {
        ModelMap modelo = new ModelMap();
        try {
            servicioPartido.crearPartido(partido);
            return new ModelAndView("redirect:/fixture"); // Si sale bien, vuelve a la lista
        } catch (PartidoInvalidoException e) {
            modelo.put("error", e.getMessage());
            modelo.put("partido", partido);
            return new ModelAndView("crear-partido", modelo); // Si falla, vuelve al formulario mostrando el error
        }
    }

    // 📊 4. DETALLE: Ver las estadísticas de un jugador en un partido específico
    @RequestMapping(path = "/partido/ver-estadisticas", method = RequestMethod.GET)
    public ModelAndView verEstadisticasJugador(@RequestParam("idJugador") Long idJugador,
                                               @RequestParam("idPartido") Long idPartido) {
        ModelMap modelo = new ModelMap();


        int goles = servicioEstadistica.calcularGolesDelJugadorEnPartido(idJugador, idPartido);
        int asistencias = servicioEstadistica.calcularAsistenciasDelJugadorEnPartido(idJugador, idPartido);
        int faltas = servicioEstadistica.calcularFaltasDelJugadorEnPartido(idJugador, idPartido);

        // Pasamos los resultados a la vista
        modelo.put("goles", goles);
        modelo.put("asistencias", asistencias);
        modelo.put("faltas", faltas);

        
        modelo.put("partido", servicioPartido.buscarPorId(idPartido));

        return new ModelAndView("detalle-estadisticas", modelo); // renderiza detalle-estadisticas.html
    }
}
