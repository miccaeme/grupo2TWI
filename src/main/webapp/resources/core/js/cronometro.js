document.addEventListener("DOMContentLoaded", function() {
  // Levantamos los datos del partido que dejamos anotados en el HTML
  const contenedorEstado = document.getElementById("estado-partido-badge");
  const contenedorCronometro = document.getElementById("contenedor-cronometro");

  if (!contenedorEstado || !contenedorCronometro) return;

  // Leemos los atributos 'data-' personalizados
  const estado = contenedorCronometro.getAttribute("data-estado");
  const partidoId = contenedorCronometro.getAttribute("data-id");

  if (estado === "EN_VIVO") {
    const relojElemento = document.getElementById("tiempo-reloj");

    // Mostramos el bloque del reloj
    contenedorCronometro.style.display = "block";

    let claveTiempo = "partido_tiempo_" + partidoId;
    let segundosTranscurridos = parseInt(sessionStorage.getItem(claveTiempo)) || 0;

    function actualizarReloj() {
      segundosTranscurridos++;
      sessionStorage.setItem(claveTiempo, segundosTranscurridos);

      let minutos = Math.floor(segundosTranscurridos / 60);
      let segundos = segundosTranscurridos % 60;

      let minStr = minutos < 10 ? "0" + minutos : minutos;
      let segStr = segundos < 10 ? "0" + segundos : segundos;

      relojElemento.textContent = minStr + ":" + segStr;
    }

    // Formateo inicial para evitar el salto visual de 00:00 al cargar
    let minutos = Math.floor(segundosTranscurridos / 60);
    let segundos = segundosTranscurridos % 60;
    relojElemento.textContent = (minutos < 10 ? "0" + minutos : minutos) + ":" + (segundos < 10 ? "0" + segundos : segundos);

    // Clavamos el intervalo de un segundo
    setInterval(actualizarReloj, 1000);
  }

  if (estado === "FINALIZADO") {
    sessionStorage.removeItem("partido_tiempo_" + partidoId);
  }
});