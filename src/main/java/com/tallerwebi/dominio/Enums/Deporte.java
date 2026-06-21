package com.tallerwebi.dominio.Enums;

public enum Deporte {
  // 1. Las constantes se quedan EXACTAMENTE IGUAL a como las usan tus compañeros
  FUTBOL,
  BASQUET,
  PADEL,
  VOLEY;

  // 2. Agregamos este método dinámico (No rompe nada de lo anterior)
  public int getSlots() {
    switch (this) {
      case FUTBOL:
        return 11;
      case BASQUET:
        return 5;
      case PADEL:
        return 2;
      case VOLEY:
        return 6;
      default:
        return 0; // Por seguridad
    }
  }
}
