package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Enums.TipoDeTorneo;
import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.TorneoEquipo;
import com.tallerwebi.dominio.servicios.ServicioEquipo;
import com.tallerwebi.dominio.servicios.ServicioTorneo;
import com.tallerwebi.dominio.Torneo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorTorneo {

  @Autowired
  private ServicioTorneo servicioTorneo;

  @Autowired
  private ServicioEquipo servicioEquipo;

  @RequestMapping(value = "/crear-torneo", method = RequestMethod.GET)
  public ModelAndView mostrarFormularioCrearTorneo() {

    ModelMap model = new ModelMap();

    model.put("torneo", new Torneo());
    return new ModelAndView("crear-torneo", model);
  }


  @RequestMapping(value = "/guardarTorneoCreado", method = RequestMethod.POST)

  public ModelAndView guardarTorneo(@ModelAttribute("torneo") Torneo torneo) {
    ModelMap model = new ModelMap();
    try{
      servicioTorneo.guardar(torneo);
      String urlRedireccion = "redirect:/asignarEquipos?id=" + torneo.getId();
      return new ModelAndView(urlRedireccion, model);

    } catch (IllegalArgumentException e){
      model.put("errorFecha", e.getMessage());
      model.put("torneo", torneo);
      return new ModelAndView("crear-torneo", model);
    }

  }

  @RequestMapping(value = "/verTorneosCreados", method = RequestMethod.GET)
  public ModelAndView verTorneosCreados() {

    List<Torneo> torneos = servicioTorneo.buscarTodos();

    ModelMap model = new ModelMap();

    model.put("torneos", torneos);

    return new ModelAndView("verTorneosCreados", model);
  }



  @RequestMapping(value= "/asignarEquipos", method = RequestMethod.GET)
  public ModelAndView mostrarFormularioAsignarEquipos(@RequestParam("id") Long id) {
    ModelMap model = new ModelMap();
    Torneo torneo = servicioTorneo.buscarPorId(id);
    model.put("torneo", torneo);
    List<TorneoEquipo> relaciones =servicioTorneo.buscarEquiposPorTorneoId(id);
    model.put("relacionesExistentes", relaciones);

    List<Equipo> equiposDisponibles = servicioEquipo.listarTodos();
    model.put("todosLosEquipos", equiposDisponibles);

    return new ModelAndView("asignarEquipos", model);
  }


 @RequestMapping(value = "/guardarEquiposAsignados", method = RequestMethod.POST)
 public ModelAndView guardarEquiposAsignados (@RequestParam("id") Long id,
                                               @RequestParam(value = "equiposIds", required = false) List<Long> equiposIds){
   if(equiposIds == null){
      equiposIds = new ArrayList<>();
    }
   servicioTorneo.asignarEquipos(id,equiposIds);
   return new ModelAndView("redirect:/asignarEquipos?id=" + id);
 }

@RequestMapping(value="/verDetalleTorneo", method = RequestMethod.GET)
  public ModelAndView mostrarDetalleTorneo(@RequestParam("id") Long id) {
    ModelMap model = new ModelMap();
    Torneo torneo = servicioTorneo.buscarPorId(id);
    model.addAttribute("torneo", torneo);
    return new ModelAndView("verDetalleTorneo",model);

  }



}
