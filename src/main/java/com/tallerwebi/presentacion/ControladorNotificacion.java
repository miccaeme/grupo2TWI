package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Notificacion;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.contratos.RepositorioUsuario;
import com.tallerwebi.dominio.servicios.ServicioNotificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ControladorNotificacion {


        private ServicioNotificacion notificacion;
        private RepositorioUsuario repositorioUsuario;

        @Autowired
        public ControladorNotificacion(ServicioNotificacion notificacion, RepositorioUsuario repositorioUsuario) {
            this.notificacion = notificacion;
            this.repositorioUsuario = repositorioUsuario;
        }

        // Tu método POST para leer la notificación
        @RequestMapping(path = "/notificacion/leer", method = RequestMethod.POST)
        public ModelAndView marcarComoLeida(@RequestParam("id") Long notificacionId, HttpServletRequest request) {

            // Ahora Java sí va a encontrar la variable "notificacion" declarada arriba 🥳
            notificacion.marcarComoLeida(notificacionId);

            String referer = request.getHeader("Referer");
            return new ModelAndView("redirect:" + (referer != null ? referer : "/dashboard"));

    }

    @RequestMapping(path = "/api/notificaciones", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> obtenerNotificaciones(HttpServletRequest request) {
        Map<String, Object> resultado = new HashMap<>();
        Long usuarioId = (Long) request.getSession().getAttribute("usuarioId");

        if (usuarioId != null) {
            try {
                Usuario usuario = repositorioUsuario.buscarUsuarioPorId(usuarioId);
                if (usuario != null && usuario.getJugador() != null) {
                    String nickname = usuario.getJugador().getNickname();
                    List<Notificacion> lista = notificacion.obtenerNotificacionesPorJugador(nickname);

                    long noLeidas = lista.stream().filter(n -> n.getLeida() == null || !n.getLeida()).count();

                    resultado.put("lista", lista);
                    resultado.put("cantidadNoLeidas", noLeidas);
                    return resultado;
                }
            } catch (Exception e) {

            }
        }
        resultado.put("lista", List.of());
        resultado.put("cantidadNoLeidas", 0);
        return resultado;
    }
}
