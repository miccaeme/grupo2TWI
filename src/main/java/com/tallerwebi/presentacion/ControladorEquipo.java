package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Enums.Deporte;
import com.tallerwebi.dominio.Enums.Posicion;
import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.EquipoJugador;
import com.tallerwebi.dominio.SolicitudUnion;
import com.tallerwebi.dominio.servicios.ServicioEquipo;
import com.tallerwebi.dominio.servicios.ServicioSolicitudUnion;
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
import java.util.ArrayList;
import java.util.List;


@Controller
public class ControladorEquipo {

    private ServicioEquipo servicioEquipo;
    private ServicioSolicitudUnion servicioSolicitudUnion;


    @Autowired
    public ControladorEquipo(ServicioEquipo servicioEquipo, ServicioSolicitudUnion servicioSolicitudUnion) {

        this.servicioEquipo = servicioEquipo;
        this.servicioSolicitudUnion = servicioSolicitudUnion;
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

            if (idLogueado == null) {
                return new ModelAndView("redirect:/login");
            }

            List<Equipo> misEquipos = servicioEquipo.buscarEquiposDelCapitan(idLogueado);

            modelo.put("listaEquipos", misEquipos);

            return new ModelAndView("mis-equipos", modelo);
        }


    @RequestMapping(path = "/equipo/gestionar", method = RequestMethod.GET)
    public ModelAndView mostrarGestionarEquipo(@RequestParam("id") Long idEquipo, HttpServletRequest request) {
        ModelMap modelo = new ModelMap();

        Long idLogueado = (Long) request.getSession().getAttribute("usuarioId");
        if (idLogueado == null) {
            return new ModelAndView("redirect:/login");
        }
        Equipo equipo = servicioEquipo.buscarEquipoPorId(idEquipo);
        modelo.put("equipo", equipo);
        List<EquipoJugador> actuales = servicioEquipo.obtenerJugadoresDelEquipo(idEquipo);

        for (EquipoJugador ej : actuales) {
            if (ej.getPosicion() != null) {
                modelo.put("JUGADOR_" + ej.getPosicion().name(), ej.getJugador());

            } else if (ej.getCapitan() != null && ej.getCapitan()) {
                modelo.put("JUGADOR_CAPITAN", ej.getJugador());
            }
        }
        modelo.put("jugadoresEquipo", actuales);
       List<Posicion>posicionesDisponibles = servicioEquipo.obtenerPosicionesDisponiblesParaElEquipo(idEquipo);
        modelo.put("posiciones", posicionesDisponibles);
        List<SolicitudUnion> pendientes = servicioSolicitudUnion.obtenerSolicitudesPendientesPorEquipo(idEquipo);
        modelo.put("solicitudesPendientes", pendientes);
        return new ModelAndView("gestionar-equipo", modelo);
    }

    @RequestMapping(path = "/equipo/asignar-jugador", method = RequestMethod.POST)
    public ModelAndView asignarJugadorPorNickname(
            @RequestParam("equipoId") Long equipoId,
            @RequestParam("nickname") String nickname,
            @RequestParam("posicion") Posicion posicion,
            RedirectAttributes redirectAttributes
    ) {
        try {
            servicioEquipo.asignarJugadorAlEquipoPorNickname(equipoId, nickname, posicion);
            redirectAttributes.addFlashAttribute("mensaje", "¡Jugador " + nickname + " agregado con éxito!");
        } catch(Exception e){
            redirectAttributes.addFlashAttribute("error","Error: " +  e.getMessage());
        }
        return new ModelAndView("redirect:/equipo/gestionar?id="+equipoId);

    }

    @RequestMapping(path = "/verEquiposCreados", method = RequestMethod.GET)
    public ModelAndView mostrarEquiposCreados(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();

        Long idLogueado = (Long) request.getSession().getAttribute("usuarioId");
        if (idLogueado == null) {
            return new ModelAndView("redirect:/login");
        }
        List<Equipo> todosLosEquipos = servicioEquipo.listarTodos();

        modelo.put("equipos", todosLosEquipos);

        return new ModelAndView("verEquiposCreados", modelo);
    }
}
