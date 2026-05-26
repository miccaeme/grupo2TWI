const opcionesPorFormato = {
    'eliminacion_directa': [
        { valor: '2', texto: '2 Equipos (Final Directa)' },
        { valor: '4', texto: '4 Equipos (Semifinal)' },
        { valor: '8', texto: '8 Equipos (Cuartos de final)' }
    ],
    'liga': [
        { valor: '4', texto: '4 Equipos' },
        { valor: '8', texto: '8 Equipos' }
    ],
    'grupos_playoffs': [
        { valor: '8', texto: '8 Equipos (2 grupos de 4)' },
        { valor: '16', texto: '16 Equipos (4 grupos de 4)' }
    ]
};

// Esperamos a que todo el HTML esté cargado en el navegador para ejecutar el código
document.addEventListener('DOMContentLoaded', function() {

    // 2. Capturamos los elementos del HTML por su ID
    const selectFormato = document.getElementById('formato');
    const selectEquipos = document.getElementById('cantidadEquipos');

    // Si los elementos existen en la página actual, activamos la lógica
    if (selectFormato && selectEquipos) {

        selectFormato.addEventListener('change', function() {
            const formatoSeleccionado = this.value;

            // Limpiamos las opciones anteriores
            selectEquipos.innerHTML = '';

            if (opcionesPorFormato[formatoSeleccionado]) {
                // Habilitamos el select de equipos
                selectEquipos.disabled = false;

                // Agregamos las nuevas opciones dinámicas
                opcionesPorFormato[formatoSeleccionado].forEach(opcion => {
                    const nuevaOpcion = document.createElement('option');
                    nuevaOpcion.value = opcion.valor;
                    nuevaOpcion.textContent = opcion.texto;
                    selectEquipos.appendChild(nuevaOpcion);
                });
            } else {
                // Si vuelve a la opción por defecto, deshabilitamos
                selectEquipos.disabled = true;
                selectEquipos.innerHTML = '<option value="">Primero elegí un formato...</option>';
            }
        });
    }
});