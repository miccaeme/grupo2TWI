package com.tallerwebi.dominio.excepcion;

public class SolicitudDuplicadaException extends RuntimeException {

    public SolicitudDuplicadaException(String mensaje) {
        super(mensaje);
    }
}