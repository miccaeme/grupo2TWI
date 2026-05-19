package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Enums.Deporte;
import com.tallerwebi.dominio.Enums.Posicion;
import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.Servicios.ServicioJugador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorJugador {

  @Autowired
  private ServicioJugador servicioJugador;

  @RequestMapping(path = "/jugador/crear", method = RequestMethod.GET)
  public ModelAndView irACrearJugador() {
    ModelMap modelo = new ModelMap();
    modelo.put("jugador", new Jugador());
    modelo.put("posiciones", Posicion.values());
    modelo.put("deportes", Deporte.values());
    return new ModelAndView("crear-jugador", modelo);
  }

  @RequestMapping(path = "/jugador/crear", method = RequestMethod.POST)
  public ModelAndView crearJugador(@ModelAttribute("jugador") Jugador jugador) {
    servicioJugador.crearJugador(jugador);

    ModelMap modelo = new ModelMap();
    modelo.put("mensaje", "Jugador creado correctamente");
    modelo.put("jugador", new Jugador());
    modelo.put("posiciones", Posicion.values());
    modelo.put("deportes", Deporte.values());
    return new ModelAndView("crear-jugador", modelo);
  }
}
