package com.ucuenca.proyecto_courier.CapaDominio;

public class Oficina {
    private String idOficina;
    private String nombre;
    private String direccion;
    private String telefono;

    public Oficina(String idOficina, String nombre, String direccion, String telefono) {
        this.idOficina = idOficina;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }
}