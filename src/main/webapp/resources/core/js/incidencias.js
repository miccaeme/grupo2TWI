document.addEventListener("DOMContentLoaded", function() {
    const selectEquipo = document.getElementById("selectEquipo");
    const selectJugador = document.getElementById("selectJugador");
    const partidoIdInput = document.getElementById("partidoIdHidden");

    if (selectEquipo && selectJugador && partidoIdInput) {

        selectEquipo.addEventListener("change", function() {
            const bandoSeleccionado = selectEquipo.value; // "LOCAL" o "VISITANTE"
            const idPartido = partidoIdInput.value;

            // 🔍 DEBUG 1: Verificamos en consola qué datos está capturando el JS
            console.log("-> Se disparó el cambio de equipo.");
            console.log("ID Partido capturado:", idPartido);
            console.log("Bando seleccionado:", bandoSeleccionado);

            // Colocamos el estado de carga y congelamos temporalmente
            selectJugador.innerHTML = '<option value="">Cargando jugadores...</option>';
            selectJugador.disabled = true;

            if (!bandoSeleccionado) {
                selectJugador.innerHTML = '<option value="">Primero seleccione un equipo...</option>';
                return;
            }

            // ✨ CORRECCIÓN: Ruta relativa (sin la barra inicial '/') para respetar el contexto /spring/partido/
            const urlFetch = `jugadores-por-equipo?idPartido=${idPartido}&bando=${bandoSeleccionado}`;
            console.log("Enviando petición FETCH a:", urlFetch);

            // Hacemos la llamada HTTP asíncrona
            fetch(urlFetch)
                .then(response => {
                    if (!response.ok) {
                        throw new Error("El servidor respondió con código de error: " + response.status);
                    }
                    return response.json();
                })
                .then(jugadores => {
                    // 🔍 DEBUG 2: Ver el JSON exacto que nos devuelve Java
                    console.log("Respuesta recibida del servidor (Lista de jugadores):", jugadores);

                    // Limpiamos las opciones para armar el listado real
                    selectJugador.innerHTML = '<option value="" disabled selected hidden style="background-color: #212529; color: #6c757d;">🔍 Seleccione un jugador...</option>';

                    if (!jugadores || jugadores.length === 0) {
                        // Si no hay jugadores, cambiamos el texto Y LO HABILITAMOS para que el usuario pueda hacer click y leerlo
                        selectJugador.innerHTML = '<option value="">No hay jugadores en este equipo</option>';
                        selectJugador.disabled = false;
                        return;
                    }

                    // Poblamos el select con los datos del DTO (id y nombre)
                    jugadores.forEach(jugador => {
                        const option = document.createElement("option");
                        option.value = jugador.id;
                        option.textContent = jugador.nombre; // Muestra el nickname del backend
                        option.style.backgroundColor = "#212529";
                        option.style.color = "white";
                        selectJugador.appendChild(option);
                    });

                    // Habilitamos el selector si todo salió bien
                    selectJugador.disabled = false;
                    console.log("¡Selector de jugadores habilitado con éxito!");
                })
                .catch(error => {
                    console.error("Error crítico en el FETCH:", error);
                    selectJugador.innerHTML = '<option value="">Error al cargar jugadores</option>';
                    selectJugador.disabled = false; // Lo habilitamos para que puedan ver el error
                });
        });
    } else {
        console.error("Error de inicialización: No se encontró alguno de los elementos HTML (selectEquipo, selectJugador o partidoIdHidden)");
    }
});