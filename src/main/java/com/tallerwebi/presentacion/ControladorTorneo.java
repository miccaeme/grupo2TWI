package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.Enums.TipoDeTorneo;
import com.tallerwebi.dominio.contratos.RepositorioUsuario;
import com.tallerwebi.dominio.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

  @Autowired
  private ServicioLogin servicioLogin;

  @Autowired
  private ServicioNotificacion servicioNotificacion;

  // NUEVO SERVICIO INYECTADO
  @Autowired
  private ServicioSolicitudTorneo servicioSolicitudTorneo;

  @RequestMapping(value = "/crear-torneo", method = RequestMethod.GET)
  public ModelAndView mostrarFormularioCrearTorneo(HttpServletRequest request) {

    ModelMap model = new ModelMap();

    model.put("torneo", new Torneo());
    //carga notis en el header
    Long idUsuarioLogueado = (Long) request.getSession().getAttribute("usuarioId");
    model.put("headerData", servicioNotificacion.obtenerDatosHeader(idUsuarioLogueado));

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

  @RequestMapping(path = "/verTorneosCreados", method = RequestMethod.GET)
  public ModelAndView verTorneosCreados(HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView("verTorneosCreados");

    Long idUsuario = (Long) request.getSession().getAttribute("usuarioId");
    if (idUsuario != null) {
      try {

        List<Equipo> deCapitan = servicioEquipo.buscarEquiposDelCapitan(idUsuario);
        List<Equipo> misEquipos = new ArrayList<>();
        if (deCapitan != null) {
          misEquipos = new ArrayList<>(deCapitan);
        }
        modelAndView.addObject("misEquipos", misEquipos);


        List<SolicitudTorneo> solicitudesUsuario = new ArrayList<>();
        for (Equipo eq : misEquipos) {

          List<SolicitudTorneo> sols = servicioSolicitudTorneo.obtenerSolicitudesPendientesPorEquipo(eq.getId());
          if (sols != null) {
            solicitudesUsuario.addAll(sols);
          }
        }
        modelAndView.addObject("solicitudesUsuario", solicitudesUsuario);

      } catch (Exception e) {
        System.out.println("Error al obtener datos: " + e.getMessage());
      }
    }

    List<Torneo> torneos = servicioTorneo.obtenerTodosLosTorneos();
    modelAndView.addObject("torneos", torneos);
    //carga notis en el header
    Long idUsuarioLogueado = (Long) request.getSession().getAttribute("usuarioId");
    modelAndView.addObject("headerData", servicioNotificacion.obtenerDatosHeader(idUsuarioLogueado));
    return modelAndView;
  }

  @RequestMapping(value= "/asignarEquipos", method = RequestMethod.GET)
  public ModelAndView mostrarFormularioAsignarEquipos(@RequestParam("id") Long id, HttpServletRequest request) {
    ModelMap model = new ModelMap();
    Torneo torneo = servicioTorneo.buscarPorId(id);
    model.put("torneo", torneo);

    List<TorneoEquipo> relaciones = servicioTorneo.buscarEquiposPorTorneoId(id);
    model.put("relacionesExistentes", relaciones);


    boolean yaAsignados = relaciones != null && !relaciones.isEmpty();
    model.put("yaAsignados", yaAsignados);

    List<Equipo> equiposDisponibles = servicioEquipo.listarPorDeporte(torneo.getDeporte());
    model.put("todosLosEquipos", equiposDisponibles);
    //carga notis en el header
    Long idUsuarioLogueado = (Long) request.getSession().getAttribute("usuarioId");
    model.put("headerData", servicioNotificacion.obtenerDatosHeader(idUsuarioLogueado));

    return new ModelAndView("asignarEquipos", model);
  }

  @RequestMapping(value = "/guardarEquiposAsignados", method = RequestMethod.POST)
  public ModelAndView guardarEquiposAsignados(@RequestParam("id") Long id, @RequestParam(value = "equiposIds", required = false) List<Long> equiposIds, RedirectAttributes redirectAttributes) {
    if (equiposIds == null) {
      equiposIds = new ArrayList<>();
    }

    List<Long> idsValidos = new ArrayList<>();
    for (Long eqId : equiposIds) {
      if (eqId != null) {
        idsValidos.add(eqId);
      }
    }

    servicioTorneo.asignarEquipos(id, idsValidos);
    redirectAttributes.addFlashAttribute("mensajeExito", "Se ha confirmado la inscripción del equipo correctamente.");


    return new ModelAndView("redirect:/verDetalleTorneo?id=" + id);
  }


  @RequestMapping(value="/verDetalleTorneo", method = RequestMethod.GET)
  public ModelAndView mostrarDetalleTorneo(@RequestParam("id") Long id, HttpServletRequest request) {
    ModelMap model = new ModelMap();
    Torneo torneo = servicioTorneo.buscarPorId(id);
    List<TorneoEquipo>asignaciones = servicioTorneo.buscarEquiposPorTorneoId(id);

    model.addAttribute("torneo", torneo);
    model.addAttribute("asignaciones", asignaciones);
    //carga notis en el header
    Long idUsuarioLogueado = (Long) request.getSession().getAttribute("usuarioId");
    model.put("headerData", servicioNotificacion.obtenerDatosHeader(idUsuarioLogueado));
    return new ModelAndView("verDetalleTorneo",model);

  }

  @RequestMapping(value = "/generarFixtureManual", method = RequestMethod.GET)
  public ModelAndView generarFixtureManual(@RequestParam("idTorneo") Long idTorneo) {


    Torneo torneo = servicioTorneo.buscarPorId(idTorneo);

    // 2. Traemos las asignaciones para el algoritmo
    List<TorneoEquipo> relaciones = servicioTorneo.buscarEquiposPorTorneoId(idTorneo);

    List<Equipo> equiposCompletos = new ArrayList<>();
    for (TorneoEquipo rel : relaciones) {
      equiposCompletos.add(rel.getEquipo());
    }

    // 3. Se ejecuta el algoritmo matemático que genera los partidos
    servicioGeneradorFixture.generarFixtureAutomatico(idTorneo, equiposCompletos, torneo.getFormato());

    // 4. CAMBIO CRÍTICO: Modificamos la bandera directamente sobre el torneo persistido
    torneo.setFixtureGenerado(true);

    // Le avisamos al servicio que guarde/actualice el torneo existente en lugar de duplicarlo
    servicioTorneo.guardarEstadoFixture(torneo);

    // 5. Redirigimos al fixture de forma limpia
    return new ModelAndView("redirect:/fixture?idTorneo=" + idTorneo);
  }

  @RequestMapping(path = "/mis-torneos", method = RequestMethod.GET)
  public ModelAndView verMisTorneos(HttpServletRequest request) {
    ModelMap model = new ModelMap();

    // 1. Verificamos sesión
    Long idUsuario = (Long) request.getSession().getAttribute("usuarioId");
    if (idUsuario == null) {
      return new ModelAndView("redirect:/login");
    }

    List<Torneo> misTorneos = servicioTorneo.buscarTorneosDelOrganizador(idUsuario);
    model.put("listaTorneos", misTorneos);


    try {

      Usuario userNoti = servicioLogin.buscarUsuarioPorId(idUsuario);
      if (userNoti != null && userNoti.getJugador() != null) {
        String nickNoti = userNoti.getJugador().getNickname();
        List<Notificacion> novedadesNoti = servicioNotificacion.obtenerNotificacionesPorJugador(nickNoti);
        long noLeidasNoti = novedadesNoti.stream()
                .filter(n -> n.getLeida() == null || !n.getLeida())
                .count();

        model.put("notificaciones", novedadesNoti);
        model.put("cantNotificacionesNoLeidas", (int) noLeidasNoti);
      }
    } catch (Exception e) {

    }

    //carga notis en el header
    Long idUsuarioLogueado = (Long) request.getSession().getAttribute("usuarioId");
    model.put("headerData", servicioNotificacion.obtenerDatosHeader(idUsuarioLogueado));
    return new ModelAndView("mis-torneos", model);
  }




  @RequestMapping(path = "/torneo/solicitar-ingreso", method = RequestMethod.POST)
  public ModelAndView solicitarIngresoATorneo(@RequestParam("torneoId") Long torneoId,
                                              @RequestParam("equipoId") Long equipoId) {
    ModelMap modelo = new ModelMap();
    try {
      servicioSolicitudTorneo.solicitarIngreso(torneoId, equipoId);
      return new ModelAndView("redirect:/verTorneosCreados?solicitudExitosa=true");
    } catch (Exception e) {
      return new ModelAndView("redirect:/verTorneosCreados?errorSolicitud=" + e.getMessage());
    }
  }

  @RequestMapping(path = "/torneo/gestionar-solicitudes", method = RequestMethod.GET)
  public ModelAndView verSolicitudes(@RequestParam("torneoId") Long torneoId, HttpServletRequest request) {
    ModelMap modelo = new ModelMap();

    Torneo torneo = servicioTorneo.buscarPorId(torneoId);
    List<SolicitudTorneo> solicitudes = servicioSolicitudTorneo.obtenerSolicitudesPendientesPorTorneo(torneoId);

    modelo.put("torneo", torneo);
    modelo.put("solicitudes", solicitudes);
    //carga notis en el header
    Long idUsuarioLogueado = (Long) request.getSession().getAttribute("usuarioId");
    modelo.put("headerData", servicioNotificacion.obtenerDatosHeader(idUsuarioLogueado));

    return new ModelAndView("gestionar-solicitudes-torneo", modelo);
  }

  @RequestMapping(path = "/torneo/solicitud/aceptar", method = RequestMethod.POST)
  public ModelAndView aceptarSolicitud(@RequestParam("solicitudId") Long solicitudId,
                                       @RequestParam("torneoId") Long torneoId) {
    try {
      servicioSolicitudTorneo.aceptarSolicitud(solicitudId);
      return new ModelAndView("redirect:/torneo/gestionar-solicitudes?torneoId=" + torneoId + "&aceptado=true");
    } catch (Exception e) {
      return new ModelAndView("redirect:/torneo/gestionar-solicitudes?torneoId=" + torneoId + "&error=" + e.getMessage());
    }
  }

  @RequestMapping(path = "/torneo/solicitud/rechazar", method = RequestMethod.POST)
  public ModelAndView rechazarSolicitud(@RequestParam("solicitudId") Long solicitudId,
                                        @RequestParam("torneoId") Long torneoId) {
    try {
      servicioSolicitudTorneo.rechazarSolicitud(solicitudId);
      return new ModelAndView("redirect:/torneo/gestionar-solicitudes?torneoId=" + torneoId + "&rechazado=true");
    } catch (Exception e) {
      return new ModelAndView("redirect:/torneo/gestionar-solicitudes?torneoId=" + torneoId + "&error=" + e.getMessage());
    }
  }
}