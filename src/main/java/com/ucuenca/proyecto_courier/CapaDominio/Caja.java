package com.ucuenca.proyecto_courier.CapaDominio;

import java.util.List;

public class Caja extends Paquete {
    private double alto;
    private double ancho;
    private double largo;

    public Caja() {
        super();
    }

    @Override
    public double calcularCostoBase(List<Rango> rangos) {
        return 0.0;
    }

    public double calcularCostoBase() {
        return 0.0;
    }
}
