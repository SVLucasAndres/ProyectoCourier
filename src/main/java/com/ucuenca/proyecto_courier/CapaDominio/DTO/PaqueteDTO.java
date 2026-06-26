package com.ucuenca.proyecto_courier.CapaDominio.DTO;

public class PaqueteDTO {
    private String idPaquete;
    private double peso;
    private double valorContenido;
    private boolean tieneSeguro;
    private double porcentajeSeguro;

    public PaqueteDTO() {}

    public String getIdPaquete() { return idPaquete; }
    public void setIdPaquete(String idPaquete) { this.idPaquete = idPaquete; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public double getValorContenido() { return valorContenido; }
    public void setValorContenido(double valorContenido) { this.valorContenido = valorContenido; }

    public boolean isTieneSeguro() { return tieneSeguro; }
    public void setTieneSeguro(boolean tieneSeguro) { this.tieneSeguro = tieneSeguro; }

    public double getPorcentajeSeguro() { return porcentajeSeguro; }
    public void setPorcentajeSeguro(double porcentajeSeguro) { this.porcentajeSeguro = porcentajeSeguro; }
}
