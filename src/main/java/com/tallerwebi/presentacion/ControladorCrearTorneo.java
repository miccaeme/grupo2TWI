package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCrearTorneo;
import com.tallerwebi.dominio.Torneo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ControladorCrearTorneo {

  @Autowired
  private ServicioCrearTorneo servicioCrearTorneo;

  @GetMapping("/crear-torneo")
  public String mostrarFormularioCrearTorneo(Model model) {
    model.addAttribute("torneo", new Torneo());
    return "crear-torneo";
  }

  /* @PostMapping("/guardarTorneoCreado") */
  @RequestMapping(value = "/guardarTorneoCreado", method = RequestMethod.POST)
  @Transactional
  public String guardarTorneo(@ModelAttribute("torneo") Torneo torneo) {
    servicioCrearTorneo.guardar(torneo);
    return "guardarTorneoCreado";
  }


  @GetMapping("/verTorneosCreados")
  public String verTorneosCreados(Model model) {
    List<Torneo> listaDeTorneosCreados = servicioCrearTorneo.buscarTodos();
    model.addAttribute("torneos", listaDeTorneosCreados);
    return "verTorneosCreados";
  }

}
