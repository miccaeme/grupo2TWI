package com.tallerwebi.dominio.excepcion;

public class SolicitudNoValidaException extends RuntimeException {

    public SolicitudNoValidaException(String mensaje) {
        super(mensaje);
    }
}