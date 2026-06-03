document.addEventListener("DOMContentLoaded", function () {
  const posicionSelect = document.getElementById("posicion");

  // Si no encuentra el select en la pantalla, corta la ejecución
  if (!posicionSelect) return;

  const posicionesPorDeporte = {
    FUTBOL: ["ARQUERO", "DEFENSOR", "MEDIOCAMPISTA", "DELANTERO"],
    BASQUET: ["BASE", "ESCOLTA", "ALERO", "ALA_PIVOT", "PIVOT"],
    VOLEY: ["ARMADOR", "CENTRAL", "PUNTA", "OPUESTO", "LIBERO"],
    PADEL: ["DRIVE", "REVES"]
  };

  // Leemos el deporte fijo que le mandamos desde el atributo del HTML
  const deporteEquipo = posicionSelect.getAttribute("data-deporte");

  if (deporteEquipo) {
    posicionSelect.innerHTML = "";

    const opcionDefault = document.createElement("option");
    opcionDefault.value = "";
    opcionDefault.innerHTML = "Seleccione una posici&oacute;n";
    posicionSelect.appendChild(opcionDefault);

    // Buscamos el array correspondiente (ej: "FUTBOL")
    const posiciones = posicionesPorDeporte[deporteEquipo.trim().toUpperCase()] || [];

    posiciones.forEach(function (posicion) {
      const option = document.createElement("option");
      option.value = posicion;
      option.textContent = posicion.replace("_", " "); // Estética para ALA_PIVOT
      posicionSelect.appendChild(option);
    });
  }
});