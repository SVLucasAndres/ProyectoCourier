package com.ucuenca.proyecto_courier.CapaDominio.Excepciones;

public class EntidadNoEncontradaException extends RuntimeException {
    public EntidadNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
