    package com.tallerwebi.presentacion;

    import com.tallerwebi.dominio.Enums.TipoEstadistica;
    import com.tallerwebi.dominio.Estadistica;
    import com.tallerwebi.dominio.Partido;
    import com.tallerwebi.dominio.excepcion.PartidoInvalidoException;
    import com.tallerwebi.dominio.servicios.ServicioEstadistica;
    import com.tallerwebi.dominio.servicios.ServicioPartido;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.ui.ModelMap;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.servlet.ModelAndView;

    import java.util.ArrayList;
    import java.util.List;

    @Controller
    public class ControladorPartido {

        private ServicioPartido servicioPartido;
        private ServicioEstadistica servicioEstadistica;

        @Autowired
        public ControladorPartido(ServicioPartido servicioPartido, ServicioEstadistica servicioEstadistica) {
            this.servicioPartido = servicioPartido;
            this.servicioEstadistica = servicioEstadistica;
        }

        // ver Fixture agrupado por torneo
        @RequestMapping(path = "/fixture", method = RequestMethod.GET)
        public ModelAndView verFixture(@RequestParam(value = "idTorneo", required = false) Long idTorneo) {
            ModelMap modelo = new ModelMap();
            List<Partido> partidos;

            if (idTorneo != null) {
                partidos = servicioPartido.buscarPartidosPorTorneoId(idTorneo);
            } else {
                partidos = servicioPartido.listarFixture();
            }

            // partidos, los ordenamos numéricamente por fecha 1, 2, 3...
            if (partidos != null) {
                partidos.sort((p1, p2) -> Integer.compare(p1.getNroFecha(), p2.getNroFecha()));
            }
            modelo.put("partidos", partidos);
            return new ModelAndView("fixture", modelo);
        }

        @RequestMapping(path = "/partido/detalle", method = RequestMethod.GET)
        public ModelAndView verDetallePartido(@RequestParam("id") Long idPartido) {
            ModelMap modelo = new ModelMap();
            Partido partido = servicioPartido.buscarPorId(idPartido);

            List<Estadistica> estadisticas = servicioEstadistica.obtenerEstadisticasDelPartido(idPartido);

            modelo.put("partido", partido);
            modelo.put("estadisticas", estadisticas);

            return new ModelAndView("detalle-partido", modelo);
        }

        // formulario para nuevo partido
        @RequestMapping(path = "/partido/nuevo", method = RequestMethod.GET)
        public ModelAndView irACrearPartido() {
            ModelMap modelo = new ModelMap();
            modelo.put("partido", new Partido());
            return new ModelAndView("crear-partido", modelo);
        }

        // guarda partido creado
        @RequestMapping(path = "/partido/guardar", method = RequestMethod.POST)
        public ModelAndView guardarPartido(@ModelAttribute("partido") Partido partido) {
            ModelMap modelo = new ModelMap();
            try {
                servicioPartido.crearPartido(partido);
                return new ModelAndView("redirect:/fixture");
            } catch (PartidoInvalidoException e) {
                modelo.put("error", e.getMessage());
                modelo.put("partido", partido);
                return new ModelAndView("crear-partido", modelo);
            }
        }

        // ver rendimiento de un jugador individual
        @RequestMapping(path = "/partido/ver-estadisticas", method = RequestMethod.GET)
        public ModelAndView verEstadisticasJugador(@RequestParam("idJugador") Long idJugador,
                                                   @RequestParam("idPartido") Long idPartido) {
            ModelMap modelo = new ModelMap();

            int goles = servicioEstadistica.calcularGolesDelJugadorEnPartido(idJugador, idPartido);
            int asistencias = servicioEstadistica.calcularAsistenciasDelJugadorEnPartido(idJugador, idPartido);
            int faltas = servicioEstadistica.calcularFaltasDelJugadorEnPartido(idJugador, idPartido);

            modelo.put("goles", goles);
            modelo.put("asistencias", asistencias);
            modelo.put("faltas", faltas);

            modelo.put("partido", servicioPartido.buscarPorId(idPartido));

            return new ModelAndView("detalle-estadisticas", modelo);
        }

        // cambia el estado de PROGRAMADO a EN_VIVO
        @RequestMapping(path = "/partido/iniciar", method = RequestMethod.POST)
        public ModelAndView iniciarPartido(@RequestParam("id") Long idPartido) {
            Partido partido = servicioPartido.buscarPorId(idPartido);
            if (partido != null) {
                partido.setEstado(com.tallerwebi.dominio.Enums.EstadoPartido.EN_VIVO);

                // Inicializamos marcadores en 0 si venían vacíos de la DB
                if (partido.getGolesLocal() == null) partido.setGolesLocal(0);
                if (partido.getGolesVisitante() == null) partido.setGolesVisitante(0);

                servicioPartido.actualizarPartido(partido);
            }
            return new ModelAndView("redirect:/partido/detalle?id=" + idPartido);
        }

        // cambia el estado de EN_VIVO a FINALIZADO
        @RequestMapping(path = "/partido/finalizar", method = RequestMethod.POST)
        public ModelAndView finalizarPartido(@RequestParam("id") Long idPartido) {
            Partido partido = servicioPartido.buscarPorId(idPartido);
            if (partido != null) {
                partido.setEstado(com.tallerwebi.dominio.Enums.EstadoPartido.FINALIZADO);
                servicioPartido.actualizarPartido(partido);
            }
            return new ModelAndView("redirect:/partido/detalle?id=" + idPartido);
        }

        @RequestMapping(path = "/partido/registrar-incidencia", method = RequestMethod.POST)
        public ModelAndView registrarIncidencia(
                @RequestParam("idPartido") Long idPartido,
                @RequestParam("idJugador") Long idJugador,
                @RequestParam("tipoIncidencia") TipoEstadistica tipoIncidencia,
                @RequestParam("bando") String bando,
                @RequestParam(value = "minuto", defaultValue = "0") Integer minuto) {


            servicioPartido.registrarIncidencia(idPartido, idJugador, tipoIncidencia, bando, minuto);

            return new ModelAndView("redirect:/partido/detalle?id=" + idPartido);
        }

        @RequestMapping(path = "/partido/gestionar", method = RequestMethod.GET)
        public String gestionarPartido(
                @RequestParam("idPartido") Long idPartido,
                @RequestParam(value = "bando", required = false) String bando,
                Model model) {

            // 1. Buscamos el partido completo y lo mandamos a la vista
            Partido partido = servicioPartido.buscarPorId(idPartido);
            model.addAttribute("partido", partido);

            List<Estadistica> estadisticas = servicioEstadistica.obtenerEstadisticasDelPartido(idPartido);
            model.addAttribute("estadisticas", estadisticas);

            // 3. Mantenemos el bando seleccionado y cargamos sus jugadores si aplica
            model.addAttribute("bandoSeleccionado", bando);
            if (bando != null) {
                List<JugadorDTO> jugadores = servicioPartido.buscarJugadoresDelPartido(idPartido, bando);
                model.addAttribute("jugadores", jugadores);
            }

            return "detalle-partido";
        }

    }