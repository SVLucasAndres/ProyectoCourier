package com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes;

import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.NavegadorVistas;

public class ContextoPaquete {
    private static PaqueteModel envioSeleccionado;
    private static NavegadorVistas navegadorGlobal;

    public static void setEnvio(PaqueteModel envio) {
        envioSeleccionado = envio;
    }

    public static PaqueteModel getEnvio() {
        return envioSeleccionado;
    }

    public static void setNavegador(NavegadorVistas nav) {
        navegadorGlobal = nav;
    }

    public static NavegadorVistas getNavegador() {
        return navegadorGlobal;
    }
}
