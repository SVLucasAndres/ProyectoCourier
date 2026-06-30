package com.ucuenca.proyecto_courier.CapaDominio.Excepciones;

public class ValidacionException extends RuntimeException {
    public ValidacionException(String mensaje) {
        super(mensaje);
    }
}
