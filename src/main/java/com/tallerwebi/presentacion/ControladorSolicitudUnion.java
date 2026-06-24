package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.servicios.ServicioSolicitudUnion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorSolicitudUnion {

    @Autowired
    private ServicioSolicitudUnion servicioSolicitudUnion;


    @RequestMapping(path="/solicitud/postularse", method = RequestMethod.POST)
    public ModelAndView postularseAEquipo(
            @RequestParam("equipoId") Long equipoId,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes){

        Long idUsuario = (Long) request.getSession().getAttribute("usuarioId");
        if(idUsuario == null){
            return new ModelAndView("redirect:/login");
        }
        try{
            servicioSolicitudUnion.postularseAEquipo(idUsuario,equipoId);
            redirectAttributes.addFlashAttribute("mensaje", "¡Tu solicitud fue enviada con exito!");
        }
        catch (Exception e) {

            redirectAttributes.addFlashAttribute("error", "Error al postularse: " + e.getMessage());
        }

        return new ModelAndView("redirect:/perfil");
    }

    @RequestMapping(path = "/solicitud/aceptar", method = RequestMethod.POST)
    public ModelAndView aceptarSolicitud(
            @RequestParam("solicitudId") Long solicitudId,
            @RequestParam("equipoId") Long equipoId,
            @RequestParam("posicionElegida") String posicionElegida,
            RedirectAttributes redirectAttributes
    ) {
        try {
            servicioSolicitudUnion.aceptarSolicitudUnion(solicitudId,posicionElegida);
            redirectAttributes.addFlashAttribute("mensaje", "¡Solicitud aceptada! El jugador ya está en el equipo.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al aceptar: " + e.getMessage());
        }

        return new ModelAndView("redirect:/equipo/gestionar?id=" + equipoId);
    }


    @RequestMapping(path = "/solicitud/rechazar", method = RequestMethod.POST)
    public ModelAndView rechazarSolicitud(
            @RequestParam("solicitudId") Long solicitudId,
            @RequestParam("equipoId") Long equipoId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            servicioSolicitudUnion.rechazarSolicitudUnion(solicitudId);
            redirectAttributes.addFlashAttribute("mensaje", "Solicitud rechazada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al rechazar: " + e.getMessage());
        }

        return new ModelAndView("redirect:/equipo/gestionar?id=" + equipoId);
    }



}
