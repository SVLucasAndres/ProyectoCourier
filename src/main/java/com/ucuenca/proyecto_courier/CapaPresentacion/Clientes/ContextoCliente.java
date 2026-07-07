package com.ucuenca.proyecto_courier.CapaPresentacion.Clientes;

import com.ucuenca.proyecto_courier.CapaPresentacion.NavegadorVistas;

public class ContextoCliente {
    private static ClienteModel clienteSeleccionado;
    private static NavegadorVistas navegadorGlobal;

    public static void setCliente(ClienteModel cliente) {
        clienteSeleccionado = cliente;
    }

    public static ClienteModel getCliente() {
        return clienteSeleccionado;
    }

    public static void setNavegador(NavegadorVistas nav) {
        navegadorGlobal = nav;
    }

    public static NavegadorVistas getNavegador() {
        return navegadorGlobal;
    }
}