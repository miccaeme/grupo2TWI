package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.servicios.ServicioEquipo;
import com.tallerwebi.dominio.servicios.ServicioLogin;
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
        return new ModelAndView("perfil", model);
    }
}
