package com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas;

import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.NavegadorVistas;

public class ContextoOficina {

    private static OficinaModel envioSeleccionado;
    private static NavegadorVistas navegadorGlobal;

    public static void setOficina(OficinaModel envio) {
        envioSeleccionado = envio;
    }

    public static OficinaModel getOficina() {
        return envioSeleccionado;
    }

    public static void setNavegador(NavegadorVistas nav) {
        navegadorGlobal = nav;
    }

    public static NavegadorVistas getNavegador() {
        return navegadorGlobal;
    }
}
