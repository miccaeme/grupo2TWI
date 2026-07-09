package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Notificacion;
import com.tallerwebi.dominio.contratos.RepositorioNotificacion;
import com.tallerwebi.dominio.contratos.RepositorioUsuario;
import com.tallerwebi.dominio.servicios.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import javax.servlet.http.HttpServletRequest;

import com.tallerwebi.dominio.servicios.ServicioNotificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorLogin {

  private ServicioLogin servicioLogin;
  private ServicioNotificacion servicioNotificacion;
  private RepositorioUsuario repositorioUsuario;


    public ControladorLogin(ServicioLogin servicioLogin) {
    this.servicioLogin = servicioLogin;
  }

  @RequestMapping("/login")
  public ModelAndView irALogin() {
    ModelMap modelo = new ModelMap();
    modelo.put("datosLogin", new DatosLogin());
    return new ModelAndView("login", modelo);
  }

  @RequestMapping(path = "/validar-login", method = RequestMethod.POST)

  public ModelAndView validarLogin(

          @ModelAttribute("datosLogin") DatosLogin datosLogin,

          HttpServletRequest request

  ) {

    Usuario usuarioBuscado = servicioLogin.consultarUsuario(

            datosLogin.getEmail(),

            datosLogin.getPassword()

    );

    if (usuarioBuscado != null) {

      request.getSession().setAttribute("ROL", usuarioBuscado.getRol());

      request.getSession().setAttribute("usuarioId", usuarioBuscado.getId());


//se busca nick

      if (usuarioBuscado.getJugador() != null) {

        request.getSession().setAttribute("usuarioNick", usuarioBuscado.getJugador().getNickname());

      }

      return new ModelAndView("redirect:/home"); }

    else {

      /* Se instancia el ModelMap solo cuando es necesario (en el flujo de error) para evitar anomalías en el flujo de datos (DU-anomaly de PMD) */

      ModelMap model = new ModelMap();

      model.put("error", "Usuario o clave incorrecta");

      return new ModelAndView("login", model);

    }

  }



  @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
  public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario) {
    ModelMap model = new ModelMap();
    try {
      servicioLogin.registrar(usuario);
    } catch (UsuarioExistente e) {
      model.put("error", "El usuario ya existe");
      return new ModelAndView("nuevo-usuario", model);
    } catch (Exception e) {
      model.put("error", "Error al registrar el nuevo usuario");
      return new ModelAndView("nuevo-usuario", model);
    }
    return new ModelAndView("redirect:/login");
  }

  @RequestMapping(path = "/nuevo-usuario", method = RequestMethod.GET)
  public ModelAndView nuevoUsuario() {
    ModelMap model = new ModelMap();
    model.put("usuario", new Usuario());
    return new ModelAndView("nuevo-usuario", model);
  }

  @RequestMapping(path = "/home", method = RequestMethod.GET)
  public ModelAndView irAHome(HttpServletRequest request) {
    ModelMap model = new ModelMap();

    Long idUsuarioLogueadoNoti = (Long) request.getSession().getAttribute("usuarioId");
    if (idUsuarioLogueadoNoti != null) {
      try {

        Usuario userNoti = servicioLogin.buscarUsuarioPorId(idUsuarioLogueadoNoti);

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
    }

    return new ModelAndView("home", model);
  }

  @RequestMapping(path = "/", method = RequestMethod.GET)
  public ModelAndView inicio() {
    return new ModelAndView("redirect:/login");
  }
}
