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

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getPesoMinimo() { return pesoMinimo; }
    public void setPesoMinimo(double pesoMinimo) { this.pesoMinimo = pesoMinimo; }
    public double getPesoMaximo() { return pesoMaximo; }
    public void setPesoMaximo(double pesoMaximo) { this.pesoMaximo = pesoMaximo; }
    public double getCostoPorKilogramo() { return costoPorKilogramo; }
    public void setCostoPorKilogramo(double costoPorKilogramo) { this.costoPorKilogramo = costoPorKilogramo; }
}
