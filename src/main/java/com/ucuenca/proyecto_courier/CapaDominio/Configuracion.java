package com.ucuenca.proyecto_courier.CapaDominio;

import java.util.List;

public class Configuracion {
    private String idConfiguracion;
    private double impuestoIVA;
    private List<Rango> rangos;

    public Configuracion() {
        this.idConfiguracion = "GLOBAL";
    }

    public Configuracion(double impuestoIVA, List<Rango> rangos) {
        this.idConfiguracion = "GLOBAL";
        this.impuestoIVA = impuestoIVA;
        this.rangos = rangos;
    }

    public String getIdConfiguracion() { return idConfiguracion; }
    public void setIdConfiguracion(String idConfiguracion) { this.idConfiguracion = idConfiguracion; }
    public double getImpuestoIVA() { return impuestoIVA; }
    public void setImpuestoIVA(double impuestoIVA) { this.impuestoIVA = impuestoIVA; }
    public List<Rango> getRangos() { return rangos; }
    public void setRangos(List<Rango> rangos) { this.rangos = rangos; }
}
