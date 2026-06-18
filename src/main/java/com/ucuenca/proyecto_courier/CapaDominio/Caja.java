package com.ucuenca.proyecto_courier.CapaDominio;

import java.util.List;

public class Caja extends Paquete {
    private double alto;
    private double ancho;
    private double largo;

    public Caja(String idPaquete, double peso, double valorContenido, boolean tieneSeguro, double porcentajeSeguro, double alto, double ancho, double largo) {
        super(idPaquete, peso, valorContenido, tieneSeguro, porcentajeSeguro);
        this.alto = alto;
        this.ancho = ancho;
        this.largo = largo;
    }

    @Override
    public double calcularCostoBase(List<Rango> rangos) {
        return 0.0;
    }

    public double calcularCostoBase() {
        return 0.0;
    }
}
