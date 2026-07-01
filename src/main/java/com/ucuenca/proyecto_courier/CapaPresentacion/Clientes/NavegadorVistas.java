package com.ucuenca.proyecto_courier.CapaPresentacion.Clientes;

@FunctionalInterface
public interface NavegadorVistas {
    void cambiarAPantalla(String rutaFxml, String nombreVista);
}
