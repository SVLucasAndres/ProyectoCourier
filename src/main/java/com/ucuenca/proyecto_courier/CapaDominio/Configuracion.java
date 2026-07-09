package com.ucuenca.proyecto_courier.CapaDominio;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Configuracion implements Serializable {
    private String idConfiguracion;
    private double impuestoIVA;
    private List<Rango> rangos;
    @Serial
    private static final long serialVersionUID = 1L;
    private double porcentajeSeguro;
    private double tarifaEntregaInmediata;
    private double tarifaEntregaSegundoDia;
    private double tarifaEntregaNormal;

    public Configuracion() {
        this.idConfiguracion = "GLOBAL";
        this.tarifaEntregaInmediata = 0.0;
        this.tarifaEntregaSegundoDia = 0.0;
        this.tarifaEntregaNormal = 0.0;
        this.impuestoIVA = 15.0;
        this.porcentajeSeguro = 0.0;
    }

    public Configuracion(double tarifaEntregaNormal, double tarifaEntregaSegundoDia, double tarifaEntregaInmediata, double porcentajeSeguro, List<Rango> rangos, double impuestoIVA, String idConfiguracion) {
        this.tarifaEntregaNormal = tarifaEntregaNormal;
        this.tarifaEntregaSegundoDia = tarifaEntregaSegundoDia;
        this.tarifaEntregaInmediata = tarifaEntregaInmediata;
        this.porcentajeSeguro = porcentajeSeguro;
        this.rangos = rangos;
        this.impuestoIVA = impuestoIVA;
        this.idConfiguracion = idConfiguracion;
    }

    public String getIdConfiguracion() { return idConfiguracion; }
    public void setIdConfiguracion(String idConfiguracion) { this.idConfiguracion = idConfiguracion; }
    public double getImpuestoIVA() { return impuestoIVA; }
    public void setImpuestoIVA(double impuestoIVA) { this.impuestoIVA = impuestoIVA; }
    public List<Rango> getRangos() { return rangos; }
    public void setRangos(List<Rango> rangos) { this.rangos = rangos; }

    public double getPorcentajeSeguro() {
        return porcentajeSeguro;
    }

    public void setPorcentajeSeguro(double porcentajeSeguro) {
        this.porcentajeSeguro = porcentajeSeguro;
    }

    public double getTarifaEntregaInmediata() {
        return tarifaEntregaInmediata;
    }

    public void setTarifaEntregaInmediata(double tarifaEntregaInmediata) {
        this.tarifaEntregaInmediata = tarifaEntregaInmediata;
    }

    public double getTarifaEntregaSegundoDia() {
        return tarifaEntregaSegundoDia;
    }

    public void setTarifaEntregaSegundoDia(double tarifaEntregaSegundoDia) {
        this.tarifaEntregaSegundoDia = tarifaEntregaSegundoDia;
    }

    public double getTarifaEntregaNormal() {
        return tarifaEntregaNormal;
    }

    public void setTarifaEntregaNormal(double tarifaEntregaNormal) {
        this.tarifaEntregaNormal = tarifaEntregaNormal;
    }
}
