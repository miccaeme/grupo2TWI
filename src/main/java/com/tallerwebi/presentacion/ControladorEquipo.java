package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Equipo;

import com.tallerwebi.dominio.ServicioEquipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorEquipo {

    @Autowired
    private ServicioEquipo servicioEquipo;

    @RequestMapping(path = "/equipo/crear", method = RequestMethod.GET)
    public ModelAndView irACrearEquipo() {
        ModelMap modelo = new ModelMap();
        modelo.put("equipo", new Equipo());
        return new ModelAndView("crear-equipo", modelo);
    }

    @RequestMapping(path = "/equipo/crear", method = RequestMethod.POST)
    public ModelAndView crearEquipo(@ModelAttribute("equipo") Equipo equipo) {
        servicioEquipo.crearEquipo(equipo);

        ModelMap modelo = new ModelMap();
        modelo.put("mensaje", "Equipo creado correctamente");
        modelo.put("equipo", new Equipo());
        return new ModelAndView("crear-equipo", modelo);
    }
}