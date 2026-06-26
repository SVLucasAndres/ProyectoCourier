package com.ucuenca.proyecto_courier.CapaDominio;

public class Rango {
    private String nombre;
    private double pesoMinimo;
    private double pesoMaximo;
    private double costoPorKilogramo;

    public Rango(String nombre, double pesoMinimo, double pesoMaximo, double costoPorKilogramo) {
        this.nombre = nombre;
        this.pesoMinimo = pesoMinimo;
        this.pesoMaximo = pesoMaximo;
        this.costoPorKilogramo = costoPorKilogramo;
    }

    public double getPesoMinimo() { return pesoMinimo; }
    public double getPesoMaximo() { return pesoMaximo; }
    public double getCostoPorKilogramo() { return costoPorKilogramo; }
}
