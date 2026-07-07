package com.ucuenca.proyecto_courier.CapaDominio.DTO;

import java.util.List;

public class ConfiguracionDTO {
    private String idConfiguracion;
    private double impuestoIVA;
    private List<RangoDTO> rangos;

    public ConfiguracionDTO() {}

    public ConfiguracionDTO(double impuestoIVA, List<RangoDTO> rangos) {
        this.impuestoIVA = impuestoIVA;
        this.rangos = rangos;
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
}
