package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.servicios.ServicioSolicitudUnion;
import com.tallerwebi.dominio.SolicitudUnion;
import com.tallerwebi.dominio.excepcion.SolicitudDuplicadaException;
import com.tallerwebi.dominio.excepcion.SolicitudNoValidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorSolicitudUnion {

    private final ServicioSolicitudUnion servicioSolicitudUnion;

    @Autowired
    public ControladorSolicitudUnion(ServicioSolicitudUnion servicioSolicitudUnion) {
        this.servicioSolicitudUnion = servicioSolicitudUnion;
    }

    @RequestMapping(path = "/solicitud/unirse/{equipoId}", method = RequestMethod.POST)
    public ModelAndView solicitarUnion(
            @PathVariable("equipoId") Long equipoId,
            HttpSession session
    ) {
        ModelMap modelo = new ModelMap();
        Long jugadorId = (Long) session.getAttribute("jugadorId");

        try {
            servicioSolicitudUnion.solicitarUnion(jugadorId, equipoId);
            modelo.put("mensaje", "Solicitud enviada. Esperá la respuesta del capitán.");
        } catch (SolicitudDuplicadaException excepcion) {
            modelo.put("error", excepcion.getMessage());
        }

        modelo.put("equipoId", equipoId);
        return new ModelAndView("solicitud-enviada", modelo);
    }

    @RequestMapping(path = "/solicitud/pendientes/{equipoId}", method = RequestMethod.GET)
    public ModelAndView verPendientes(
            @PathVariable("equipoId") Long equipoId,
            HttpSession session
    ) {
        Long capitanId = (Long) session.getAttribute("usuarioId");
        List<SolicitudUnion> solicitudes = servicioSolicitudUnion.obtenerPendientesPorEquipo(equipoId);

        ModelMap modelo = new ModelMap();
        modelo.put("solicitudes", solicitudes);
        modelo.put("equipoId", equipoId);
        modelo.put("capitanId", capitanId);
        return new ModelAndView("solicitudes-pendientes", modelo);
    }

    @RequestMapping(path = "/solicitud/aceptar/{solicitudId}/{equipoId}", method = RequestMethod.POST)
    public ModelAndView aceptarSolicitud(
            @PathVariable("solicitudId") Long solicitudId,
            @PathVariable("equipoId") Long equipoId,
            HttpSession session
    ) {
        Long capitanId = (Long) session.getAttribute("usuarioId");
        ModelMap modelo = new ModelMap();

        try {
            servicioSolicitudUnion.aceptarSolicitud(solicitudId, capitanId);
            modelo.put("mensaje", "Solicitud aceptada.");
        } catch (SolicitudNoValidaException excepcion) {
            modelo.put("error", excepcion.getMessage());
        }

        return new ModelAndView("redirect:/solicitud/pendientes/" + equipoId, modelo);
    }

    @RequestMapping(path = "/solicitud/rechazar/{solicitudId}/{equipoId}", method = RequestMethod.POST)
    public ModelAndView rechazarSolicitud(
            @PathVariable("solicitudId") Long solicitudId,
            @PathVariable("equipoId") Long equipoId,
            HttpSession session
    ) {
        Long capitanId = (Long) session.getAttribute("usuarioId");
        ModelMap modelo = new ModelMap();

        try {
            servicioSolicitudUnion.rechazarSolicitud(solicitudId, capitanId);
            modelo.put("mensaje", "Solicitud rechazada.");
        } catch (SolicitudNoValidaException excepcion) {
            modelo.put("error", excepcion.getMessage());
        }

        return new ModelAndView("redirect:/solicitud/pendientes/" + equipoId, modelo);
    }

    @RequestMapping(path = "/solicitud/mis-solicitudes", method = RequestMethod.GET)
    public ModelAndView misSolicitudes(HttpSession session) {
        Long jugadorId = (Long) session.getAttribute("jugadorId");
        List<SolicitudUnion> solicitudes = servicioSolicitudUnion.obtenerSolicitudesDeJugador(jugadorId);

        ModelMap modelo = new ModelMap();
        modelo.put("solicitudes", solicitudes);
        return new ModelAndView("mis-solicitudes", modelo);
    }
}