package com.tallerwebi.dominio.excepcion;

public class PartidoInvalidoException extends RuntimeException {
    public PartidoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
