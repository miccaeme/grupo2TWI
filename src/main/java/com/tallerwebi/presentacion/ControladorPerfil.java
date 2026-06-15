package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.servicios.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
@Controller
public class ControladorPerfil {

    @Autowired
    private ServicioLogin servicioLogin;

    @RequestMapping(path = "/perfil", method = RequestMethod.GET)
    public ModelAndView irAPerfil(HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Long idUsuario = (Long) request.getSession().getAttribute("usuarioId");
        Usuario usuarioLogueado = servicioLogin.buscarUsuarioPorId(idUsuario);
        model.put("usuarioLogueado", usuarioLogueado);

        return new ModelAndView("perfil", model);
    }
}
