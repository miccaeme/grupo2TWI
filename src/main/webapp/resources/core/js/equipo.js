document.addEventListener("DOMContentLoaded", function () {

  const deporteSelect = document.getElementById("deporte");
  const posicionSelect = document.getElementById("posicion");

  const posicionesPorDeporte = {
    FUTBOL: ["ARQUERO", "DEFENSOR", "MEDIOCAMPISTA", "DELANTERO"],
    BASQUET: ["BASE", "ESCOLTA", "ALERO", "ALA_PIVOT", "PIVOT"],
    VOLEY: ["ARMADOR", "CENTRAL", "PUNTA", "OPUESTO", "LIBERO"],
    PADEL: ["DRIVE", "REVES"]
  };

  deporteSelect.addEventListener("change", function () {

    const deporteSeleccionado = deporteSelect.value;

    posicionSelect.innerHTML = "";

    const opcionDefault = document.createElement("option");

    opcionDefault.value = "";
    opcionDefault.innerHTML = "Seleccione una posici&oacute;n";

    posicionSelect.appendChild(opcionDefault);

    const posiciones = posicionesPorDeporte[deporteSeleccionado] || [];

    posiciones.forEach(function (posicion) {

      const option = document.createElement("option");

      option.value = posicion;
      option.textContent = posicion;

      posicionSelect.appendChild(option);

    });

  });

});