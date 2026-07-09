package com.ucuenca.proyecto_courier.CapaDominio;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList; // 🌟 Asegúrate de importar ArrayList
import java.util.List;

public class RutaSeguimiento implements Serializable {
    private List<PuntoIntermedio> puntosIntermedios = new ArrayList<>();

    public RutaSeguimiento(List<PuntoIntermedio> puntosIntermedios) {
        this.puntosIntermedios = puntosIntermedios;
    }

    public RutaSeguimiento(){}

    public void agregarPaso(PuntoIntermedio puntoIntermedio) {
        if (this.puntosIntermedios != null) {
            this.puntosIntermedios.add(puntoIntermedio);
        }
    }
    @Serial
    private static final long serialVersionUID = 1L;

    public List<PuntoIntermedio> getPuntosIntermedios() {
        return puntosIntermedios;
    }

    public void setPuntosIntermedios(List<PuntoIntermedio> puntosIntermedios) {
        this.puntosIntermedios = puntosIntermedios;
    }
}