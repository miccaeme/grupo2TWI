package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.Notificacion;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.servicios.ServicioEquipo;
import com.tallerwebi.dominio.servicios.ServicioLogin;
import com.tallerwebi.dominio.servicios.ServicioNotificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorPerfil {

    @Autowired
    private ServicioLogin servicioLogin;
    @Autowired
    private ServicioEquipo servicioEquipo;
    @Autowired
    private ServicioNotificacion servicioNotificacion;

    @RequestMapping(path = "/perfil", method = RequestMethod.GET)
    public ModelAndView irAPerfil(HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Long idUsuario = (Long) request.getSession().getAttribute("usuarioId");
        if (idUsuario == null) {
            return new ModelAndView("redirect:/login");
        }
        Usuario usuarioLogueado = servicioLogin.buscarUsuarioPorId(idUsuario);
        model.put("usuarioLogueado", usuarioLogueado);

        List<Equipo> misEquipos = servicioEquipo.buscarEquiposDelCapitan(idUsuario);
        model.put("listaEquipos", misEquipos);

        String nickDelUsuarioLogueado = usuarioLogueado.getJugador().getNickname();

        List<Notificacion> novedades = servicioNotificacion.obtenerNotificacionesPorJugador(nickDelUsuarioLogueado);
        model.put("notificaciones", novedades);
        return new ModelAndView("perfil", model);
    }
}
