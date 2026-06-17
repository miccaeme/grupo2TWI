package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Enums.TipoDeTorneo;
import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.TorneoEquipo;
import com.tallerwebi.dominio.servicios.ServicioEquipo;
import com.tallerwebi.dominio.servicios.ServicioGeneradorFixture;
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
  private ServicioGeneradorFixture servicioGeneradorFixture;

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

    // 1. Guardamos los equipos en el torneo (tu lógica actual)
    servicioTorneo.asignarEquipos(id, equiposIds);

    // 2. Buscamos el objeto Torneo completo para saber qué formato tiene ("liga", "eliminacion_directa", etc.)
    Torneo torneo = servicioTorneo.buscarPorId(id);

    // 3. Traemos las entidades de los Equipos completos usando la lista de IDs que marcó el usuario
    List<Equipo> equiposCompletos = new ArrayList<>();
    for(Long equipoId : equiposIds) {
      equiposCompletos.add(servicioEquipo.buscarEquipoPorId(equipoId)); // Asegurate de que servicioEquipo
    }

    // 🚀 4. ¡LA MAGIA! Gatillamos el algoritmo pasándole el formato (String) del torneo
    servicioGeneradorFixture.generarFixtureAutomatico(id, equiposCompletos, torneo.getFormato());

    return new ModelAndView("redirect:/fixture?idTorneo=" + id);
  }







@RequestMapping(value="/verDetalleTorneo", method = RequestMethod.GET)
  public ModelAndView mostrarDetalleTorneo(@RequestParam("id") Long id) {
    ModelMap model = new ModelMap();
    Torneo torneo = servicioTorneo.buscarPorId(id);
    List<TorneoEquipo>asignaciones = servicioTorneo.buscarEquiposPorTorneoId(id);

    model.addAttribute("torneo", torneo);
    model.addAttribute("asignaciones", asignaciones);
    return new ModelAndView("verDetalleTorneo",model);

  }



}
