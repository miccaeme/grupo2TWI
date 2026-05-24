package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Enums.Deporte;
import com.tallerwebi.dominio.Enums.Posicion;
import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.servicios.ServicioEquipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ControladorEquipo {

    @Autowired
    private ServicioEquipo servicioEquipo;

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
            @RequestParam("jugadorId") Long jugadorId,//como el profe dijo que el login va a lo ultimo , despues lo cambiamos  (Long) session.getAttribute
            @RequestParam("posicion") Posicion posicion) {

        servicioEquipo.crearEquipo(equipo, jugadorId, posicion);

        ModelMap modelo = new ModelMap();
        modelo.put("mensaje", "Equipo creado correctamente");
        modelo.put("equipo", new Equipo());
        modelo.put("posiciones", Posicion.values());
        modelo.put("deportes", Deporte.values());

        return new ModelAndView("crear-equipo", modelo);

    }
}