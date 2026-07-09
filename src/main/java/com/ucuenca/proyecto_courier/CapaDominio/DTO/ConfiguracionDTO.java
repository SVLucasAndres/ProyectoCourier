package com.ucuenca.proyecto_courier.CapaDominio.DTO;

import java.util.List;

public class ConfiguracionDTO {
    private String idConfiguracion;
    private double impuestoIVA;
    private List<RangoDTO> rangos;
    private double porcentajeSeguro;
    private double tarifaEntregaInmediata;
    private double tarifaEntregaSegundoDia;
    private double tarifaEntregaNormal;

    public ConfiguracionDTO() {}

    public ConfiguracionDTO(String idConfiguracion, double impuestoIVA, List<RangoDTO> rangos, double porcentajeSeguro, double tarifaEntregaInmediata, double tarifaEntregaSegundoDia, double tarifaEntregaNormal) {
        this.idConfiguracion = idConfiguracion;
        this.impuestoIVA = impuestoIVA;
        this.rangos = rangos;
        this.porcentajeSeguro = porcentajeSeguro;
        this.tarifaEntregaInmediata = tarifaEntregaInmediata;
        this.tarifaEntregaSegundoDia = tarifaEntregaSegundoDia;
        this.tarifaEntregaNormal = tarifaEntregaNormal;
    }

    public double getImpuestoIVA() { return impuestoIVA; }
    public void setImpuestoIVA(double impuestoIVA) { this.impuestoIVA = impuestoIVA; }
    public List<RangoDTO> getRangos() { return rangos; }
    public void setRangos(List<RangoDTO> rangos) { this.rangos = rangos; }

    public String getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(String idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

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
