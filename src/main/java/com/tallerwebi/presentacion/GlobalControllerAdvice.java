package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.Notificacion;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.contratos.RepositorioUsuario;
import com.tallerwebi.dominio.servicios.ServicioNotificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@Component
public class GlobalControllerAdvice {

    private ServicioNotificacion notificacion;
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public GlobalControllerAdvice(ServicioNotificacion servicioNotificacion, RepositorioUsuario repositorioUsuario) {
        this.notificacion = servicioNotificacion;
        this.repositorioUsuario = repositorioUsuario;
    }

    @ModelAttribute
    public void cargarDatosGlobalesNotificaciones(HttpServletRequest request, Model model) {
        Long usuarioId = (Long) request.getSession().getAttribute("usuarioId");

        if (usuarioId != null) {
            try {
                Usuario usuario = repositorioUsuario.buscarUsuarioPorId(usuarioId);
                if (usuario != null && usuario.getJugador() != null) {
                    String nickname = usuario.getJugador().getNickname();

                    List<Notificacion> listaNotis = notificacion.obtenerNotificacionesPorJugador(nickname);

                    // Contamos cuántas tienen leida == false de manera segura
                    long noLeidas = listaNotis.stream()
                            .filter(n -> n.getLeida() == null || !n.getLeida())
                            .count();

                    model.addAttribute("notificaciones", listaNotis);
                    model.addAttribute("cantNotificacionesNoLeidas", (int) noLeidas);
                    return;
                }
            } catch (Exception e) {

            }
        }

        model.addAttribute("notificaciones", List.of());
        model.addAttribute("cantNotificacionesNoLeidas", 0);
    }
}
