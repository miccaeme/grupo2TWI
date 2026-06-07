package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Enums.Deporte;
import com.tallerwebi.dominio.Enums.Posicion;
import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.EquipoJugador;
import com.tallerwebi.dominio.Jugador;
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

import javax.servlet.http.HttpSession;
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
            @RequestParam("jugadorId") Long jugadorId, // modificar cuando hagamos el login por httpSession
            @RequestParam("posicion") Posicion posicion,
            RedirectAttributes redirectAttributes) {

        servicioEquipo.crearEquipo(equipo, jugadorId, posicion);

        // para mostrar un mensaje temporal de exito y se borra solo
        redirectAttributes.addFlashAttribute("mensaje", "Equipo creado correctamente");

        //Redirigimos a mis-equipos arrastrando el id de prueba hardcodeado
        return new ModelAndView("redirect:/equipo/mis-equipos?jugadorId=" + jugadorId);
    }



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

        Equipo equipo = servicioEquipo.buscarEquiposPorId(idEquipo);
        modelo.put("equipo", equipo);

        List<EquipoJugador> actuales = servicioEquipo.obtenerJugadoresDelEquipo(idEquipo);
        modelo.put("jugadoresEquipo", actuales);

        modelo.put("posiciones", Posicion.values());

        return new ModelAndView("gestionar-equipo", modelo);
    }
}