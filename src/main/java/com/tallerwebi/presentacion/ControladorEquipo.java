package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Enums.Deporte;
import com.tallerwebi.dominio.Enums.Posicion;
import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.EquipoJugador;
import com.tallerwebi.dominio.servicios.ServicioEquipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class ControladorEquipo {

    private ServicioEquipo servicioEquipo;

    @Autowired
    public ControladorEquipo(ServicioEquipo servicioEquipo) {

        this.servicioEquipo = servicioEquipo;
    }


    @RequestMapping(path = "/equipo/crear", method = RequestMethod.GET)
    public ModelAndView irACrearEquipo() {
        ModelMap modelo = new ModelMap();

        modelo.put("equipo", new Equipo());
        modelo.put("posiciones", Posicion.values());
        modelo.put("deportes", Deporte.values());

        return new ModelAndView("crear-equipo", modelo);
    }


    @RequestMapping(path = "/equipo/crear", method = RequestMethod.POST)
    public ModelAndView crearEquipo(
            @ModelAttribute("equipo") Equipo equipo,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
         ) {

        Long idUsuarioLogueado = (Long) request.getSession().getAttribute("usuarioId");

        if (idUsuarioLogueado == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            servicioEquipo.crearEquipo(equipo, idUsuarioLogueado);

            redirectAttributes.addFlashAttribute("mensaje", "Equipo creado correctamente");


            return new ModelAndView("redirect:/equipo/mis-equipos");

        } catch (Exception e) {

            ModelMap model = new ModelMap();
            model.put("error", "No se pudo crear el equipo: " + e.getMessage());
            model.put("equipo", equipo);
            return new ModelAndView("crear-equipo", model);

        }
    }
        @RequestMapping(path = "/equipo/mis-equipos", method = RequestMethod.GET)
        public ModelAndView mostrarMisEquipos(HttpServletRequest request) {
            ModelMap modelo = new ModelMap();

            Long idLogueado = (Long) request.getSession().getAttribute("usuarioId");

            // 3. Control de seguridad: si no hay sesión, derecho al login
            if (idLogueado == null) {
                return new ModelAndView("redirect:/login");
            }

            // 4. Buscamos los equipos usando el ID real que viene de la sesión
            List<Equipo> misEquipos = servicioEquipo.buscarEquiposDelCapitan(idLogueado);

            modelo.put("listaEquipos", misEquipos);

            return new ModelAndView("mis-equipos", modelo);
        }


/*
    @RequestMapping(path = "/equipo/mis-equipos", method = RequestMethod.GET)
    public ModelAndView mostrarMisEquipos(@RequestParam(value = "jugadorId", required = false) Long jugadorId) {
        ModelMap modelo = new ModelMap();

        //
        Long idParaBuscar = (jugadorId != null) ? jugadorId : 1L;

        // 2. Buscamos en el servicio los equipos de los cuales es Capitán usando ese id provisorio
        List<Equipo> misEquipos = servicioEquipo.buscarEquiposDelCapitan(idParaBuscar);

        modelo.put("listaEquipos", misEquipos);

        return new ModelAndView("mis-equipos", modelo);
    }

    @RequestMapping(path = "/equipo/gestionar", method = RequestMethod.GET)
    public ModelAndView mostrarGestionarEquipo(@RequestParam("id") Long idEquipo) {
        ModelMap modelo = new ModelMap();

        Equipo equipo = servicioEquipo.buscarEquipoPorId(idEquipo);
        modelo.put("equipo", equipo);

        List<EquipoJugador> actuales = servicioEquipo.obtenerJugadoresDelEquipo(idEquipo);
        modelo.put("jugadoresEquipo", actuales);

        modelo.put("posiciones", Posicion.values());

        return new ModelAndView("gestionar-equipo", modelo);
    }*/

    @RequestMapping(path = "/equipo/gestionar", method = RequestMethod.GET)
    public ModelAndView mostrarGestionarEquipo(@RequestParam("id") Long idEquipo, HttpServletRequest request) {
        ModelMap modelo = new ModelMap();

        // 1. Control de seguridad básico (que esté logueado)
        Long idLogueado = (Long) request.getSession().getAttribute("usuarioId");
        if (idLogueado == null) {
            return new ModelAndView("redirect:/login");
        }

        // 2. Buscamos el equipo por su ID
        Equipo equipo = servicioEquipo.buscarEquipoPorId(idEquipo);
        modelo.put("equipo", equipo);

        // 3. Traemos los integrantes actuales del equipo
        List<EquipoJugador> actuales = servicioEquipo.obtenerJugadoresDelEquipo(idEquipo);


        for (EquipoJugador ej : actuales) {

            if (ej.getPosicion() != null) {
                modelo.put("JUGADOR_" + ej.getPosicion().name(), ej.getJugador());
            } else {

                modelo.put("JUGADOR_CAPITAN", ej.getJugador());
            }
        }


        modelo.put("jugadoresEquipo", actuales);

        return new ModelAndView("gestionar-equipo", modelo);
    }
}