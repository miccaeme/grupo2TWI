package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.servicios.ServicioTorneo;
import com.tallerwebi.dominio.Torneo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorTorneo {

  @Autowired
  private ServicioTorneo servicioTorneo;

  @RequestMapping(value = "/crear-torneo", method = RequestMethod.GET)
  public ModelAndView mostrarFormularioCrearTorneo() {

    ModelMap model = new ModelMap();

    model.put("torneo", new Torneo());

    return new ModelAndView("crear-torneo", model);
  }

  @RequestMapping(value = "/guardarTorneoCreado", method = RequestMethod.POST)
  public ModelAndView guardarTorneo(@ModelAttribute("torneo") Torneo torneo) {

    servicioTorneo.guardar(torneo);

    ModelMap model = new ModelMap();

    model.put("mensaje", "Torneo creado correctamente");

    return new ModelAndView("guardarTorneoCreado", model);
  }

  @RequestMapping(value = "/verTorneosCreados", method = RequestMethod.GET)
  public ModelAndView verTorneosCreados() {

    List<Torneo> torneos = servicioTorneo.buscarTodos();

    ModelMap model = new ModelMap();

    model.put("torneos", torneos);

    return new ModelAndView("verTorneosCreados", model);
  }
}
