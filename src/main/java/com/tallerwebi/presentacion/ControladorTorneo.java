package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Enums.TipoDeTorneo;
import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.ServicioTorneoImpl;
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

import javax.servlet.http.HttpServletRequest;
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

  public ModelAndView guardarTorneo(@ModelAttribute("torneo") Torneo torneo, HttpServletRequest request) {
    ModelMap model = new ModelMap();
    try{
      Long idLogueado = (Long) request.getSession().getAttribute("usuarioId");
      servicioTorneo.guardar(torneo, idLogueado);
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

    List<TorneoEquipo> relaciones = servicioTorneo.buscarEquiposPorTorneoId(id);
    model.put("relacionesExistentes", relaciones);

    // determino si ya tiene los equipos asignados guardados en la BD
    boolean yaAsignados = relaciones != null && !relaciones.isEmpty();
    model.put("yaAsignados", yaAsignados);

    List<Equipo> equiposDisponibles = servicioEquipo.listarTodos();
    model.put("todosLosEquipos", equiposDisponibles);

    return new ModelAndView("asignarEquipos", model);
  }

  @RequestMapping(value = "/guardarEquiposAsignados", method = RequestMethod.POST)
  public ModelAndView guardarEquiposAsignados(@RequestParam("id") Long id,
                                              @RequestParam(value = "equiposIds", required = false) List<Long> equiposIds) {
    if (equiposIds == null) {
      equiposIds = new ArrayList<>();
    }

    // Control de seguridad por si vuelven a enviar un torneo con equipos ya fijos
    List<TorneoEquipo> relacionesExistentes = servicioTorneo.buscarEquiposPorTorneoId(id);
    if (relacionesExistentes != null && !relacionesExistentes.isEmpty()) {
      return new ModelAndView("redirect:/fixture?idTorneo=" + id);
    }

    // Persistir de verdad los equipos en la tabla intermedia del torneo
    // Esto asegura que al ir a "Ver Detalles" no salte vacío
    servicioTorneo.asignarEquipos(id, equiposIds);

    //Buscar el objeto torneo para extraer los metadatos necesarios (como el formato)
    Torneo torneo = servicioTorneo.buscarPorId(id);

    // 4. Mapear y buscar los objetos completos de cada equipo mediante su ID
    List<Equipo> equiposCompletos = new ArrayList<>();
    for (Long equipoId : equiposIds) {
      equiposCompletos.add(servicioEquipo.buscarEquipoPorId(equipoId));
    }

    // 5. Generar el fixture automático impecable pasándole los objetos correctos
    servicioGeneradorFixture.generarFixtureAutomatico(id, equiposCompletos, torneo.getFormato());

    // 6. Redirigir a la pantalla del fixture definitivo
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
