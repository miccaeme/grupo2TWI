package com.tallerwebi.dominio.contratos;

import com.tallerwebi.dominio.TorneoEquipo;

import java.util.List;

public interface RepositorioTorneoEquipo {
     List<TorneoEquipo> buscarEquiposPorTorneoId(Long torneoId);
}
