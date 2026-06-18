package com.ucuenca.proyecto_courier.CapaDominio;

import java.util.List;

public abstract class Paquete {
    private String idPaquete;
    private double peso;
    private double valorContenido;
    private boolean tieneSeguro;
    private double porcentajeSeguro;

    public Paquete(String idPaquete, double peso, double valorContenido, boolean tieneSeguro, double porcentajeSeguro) {
        this.idPaquete = idPaquete;
        this.peso = peso;
        this.valorContenido = valorContenido;
        this.tieneSeguro = tieneSeguro;
        this.porcentajeSeguro = porcentajeSeguro;
    }

    public abstract double calcularCostoBase(List<Rango> rangos);

    private double calcularCostoSeguro() {
        return 0.0;
    }
}
