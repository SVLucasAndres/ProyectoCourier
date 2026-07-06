package com.ucuenca.proyecto_courier.CapaDominio;

import java.util.List;

public class Caja extends Paquete {
    private double alto;
    private double ancho;
    private double largo;

    public Caja(String idPaquete, double peso, double valorContenido, boolean tieneSeguro, double porcentajeSeguro,
            RutaSeguimiento ruta, double alto, double ancho, double largo) {
        super(idPaquete, peso, valorContenido, tieneSeguro, porcentajeSeguro, ruta);
        this.alto = alto;
        this.ancho = ancho;
        this.largo = largo;
    }

    public Caja(){

    }

    @Override
    public double calcularCostoBase(List<Rango> rangos) {
        if (rangos != null) {
            for (Rango rango : rangos) {
                if (getPeso() >= rango.getPesoMinimo() && getPeso() <= rango.getPesoMaximo()) {
                    return getPeso() * rango.getCostoPorKilogramo();
                }
            }
        }
        return 20.0;
    }

    public double getAlto() {
        return alto;
    }

    public double getAncho() {
        return ancho;
    }

    public double getLargo() {
        return largo;
    }

    public void setLargo(double largo) {
        this.largo = largo;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }
}
