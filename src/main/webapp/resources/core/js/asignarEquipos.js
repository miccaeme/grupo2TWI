document.addEventListener("DOMContentLoaded", function() {
    const selectors = document.querySelectorAll('.selector-equipo');

    function filtrarOpcionesCruzadas() {
        // Guardamos los valores seleccionados en los inputs que no estén vacíos
        const valoresSeleccionados = Array.from(selectors).map(s => s.value).filter(v => v !== "");

        selectors.forEach(selectorActual => {
            const options = selectorActual.querySelectorAll('option');

            options.forEach(option => {
                // Si la opción está tomada en OTRO slot, la ocultamos/deshabilitamos
                if (option.value !== "" && option.value !== selectorActual.value) {
                    if (valoresSeleccionados.includes(option.value)) {
                        option.style.display = 'none';
                        option.disabled = true;
                    } else {
                        option.style.display = 'block';
                        option.disabled = false;
                    }
                }
            });
        });
    }

    selectors.forEach(selector => {
        selector.addEventListener('change', filtrarOpcionesCruzadas);
    });

    // Ejecución inicial para actualizar estados al cargar la vista
    filtrarOpcionesCruzadas();
});