package com.ucuenca.proyecto_courier.CapaDominio;
import java.util.List;

public class RutaSeguimiento {
    private List<PuntoIntermedio> puntosIntermedios;

    public RutaSeguimiento(List<PuntoIntermedio> puntosIntermedios) {
        this.puntosIntermedios = puntosIntermedios;
    }

    public void agregarPaso(PuntoIntermedio puntoIntermedio) {
        if (this.puntosIntermedios != null) {
            this.puntosIntermedios.add(puntoIntermedio);
        }
    }

    public List<PuntoIntermedio> getPuntosIntermedios() {
        return puntosIntermedios;
    }
}
