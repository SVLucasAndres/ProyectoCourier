package com.ucuenca.proyecto_courier.CapaDominio;

import java.util.List;

public class Cliente {
    private String idCliente;
    private String nombre;
    private String direccion;
    private String telefono;
    private List<Envio> listaEnvios;

    public Cliente(String idCliente, String nombre, String direccion, String telefono, List<Envio> listaEnvios) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.listaEnvios = listaEnvios; // Se pasa la lista por parámetro
    }
}
