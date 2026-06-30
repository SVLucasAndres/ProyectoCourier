package com.ucuenca.proyecto_courier.CapaDominio.Excepciones;

public class OperacionInvalidaException extends RuntimeException {
    public OperacionInvalidaException(String mensaje) {
        super(mensaje);
    }
}
