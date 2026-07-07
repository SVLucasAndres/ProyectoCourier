package com.ucuenca.proyecto_courier.CapaPresentacion.Envios;

import com.ucuenca.proyecto_courier.CapaPresentacion.NavegadorVistas;

public class ContextoEnvio {
    private static EnvioModel envioSeleccionado;
    private static NavegadorVistas navegadorGlobal;

    public static void setEnvio(EnvioModel envio) {
        envioSeleccionado = envio;
    }

    public static EnvioModel getEnvio() {
        return envioSeleccionado;
    }

    public static void setNavegador(NavegadorVistas nav) {
        navegadorGlobal = nav;
    }

    public static NavegadorVistas getNavegador() {
        return navegadorGlobal;
    }
}
