package com.ucuenca.proyecto_courier.CapaDominio.DTO;

import com.ucuenca.proyecto_courier.CapaDominio.Enums.Tamano;

public class PaqueteDTO {
    private String idPaquete;
    private double peso;
    private double valorContenido;
    private boolean tieneSeguro;
    private double porcentajeSeguro;

    // Campos para creación dinámica
    private String tipo; // "CAJA" o "SOBRE"
    private double alto;
    private double ancho;
    private double largo;
    private Tamano tamano;

    public PaqueteDTO() {
    }

    public String getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(String idPaquete) {
        this.idPaquete = idPaquete;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getValorContenido() {
        return valorContenido;
    }

    public void setValorContenido(double valorContenido) {
        this.valorContenido = valorContenido;
    }

    public boolean isTieneSeguro() {
        return tieneSeguro;
    }

    public void setTieneSeguro(boolean tieneSeguro) {
        this.tieneSeguro = tieneSeguro;
    }

    public double getPorcentajeSeguro() {
        return porcentajeSeguro;
    }

    public void setPorcentajeSeguro(double porcentajeSeguro) {
        this.porcentajeSeguro = porcentajeSeguro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    public double getAncho() {
        return ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public double getLargo() {
        return largo;
    }

    public void setLargo(double largo) {
        this.largo = largo;
    }

    public Tamano getTamano() {
        return tamano;
    }

    public void setTamano(Tamano tamano) {
        this.tamano = tamano;
    }
}
