package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.servicios.ServicioNotificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorNotificacion {


        private ServicioNotificacion notificacion;

        @Autowired
        public ControladorNotificacion(ServicioNotificacion notificacion) {
            this.notificacion = notificacion;
        }

        // Tu método POST para leer la notificación
        @RequestMapping(path = "/notificacion/leer", method = RequestMethod.POST)
        public ModelAndView marcarComoLeida(@RequestParam("id") Long notificacionId, HttpServletRequest request) {

            // Ahora Java sí va a encontrar la variable "notificacion" declarada arriba 🥳
            notificacion.marcarComoLeida(notificacionId);

            String referer = request.getHeader("Referer");
            return new ModelAndView("redirect:" + (referer != null ? referer : "/dashboard"));

    }
}
