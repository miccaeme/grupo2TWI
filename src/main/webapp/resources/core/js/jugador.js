const posicionesPorDeporte = {
    FUTBOL: ["ARQUERO", "DEFENSOR", "MEDIOCAMPISTA", "DELANTERO"],
    BASQUET: ["BASE", "ESCOLTA", "ALERO", "ALA_PIVOT", "PIVOT"],
    VOLEY: ["ARMADOR", "CENTRAL", "PUNTA", "OPUESTO", "LIBERO"],
    PADEL: ["DRIVE", "REVES"]
};

const deporteSelect = document.getElementById("deporte");
const posicionSelect = document.getElementById("posicion");

deporteSelect.addEventListener("change", function () {
    posicionSelect.innerHTML = '<option value="">Seleccione una posici&oacute;n</option>';

    const posiciones = posicionesPorDeporte[this.value];

    posiciones.forEach(function (posicion) {
        const option = document.createElement("option");
        option.value = posicion;
        option.text = posicion;
        posicionSelect.appendChild(option);
    });
});