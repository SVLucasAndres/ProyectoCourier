package com.ucuenca.proyecto_courier.CapaDominio;
import java.util.List;

public class RutaSeguimiento {
    private List<PuntoIntermedio> registros;

    public RutaSeguimiento(List<PuntoIntermedio> registros) {
        this.registros = registros;
    }

    public void agregarPaso(PuntoIntermedio punto) {
        // Estructura vacía
    }
}
