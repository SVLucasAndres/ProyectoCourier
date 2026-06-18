package com.ucuenca.proyecto_courier.CapaDominio;

import java.util.List;

public class Cliente {
    private String idCliente;
    private String nombre;
    private String direccion;
    private String telefono;
    private List<Envio> listaEnvios;

    public Cliente() {
    }
}
