package com.ucuenca.proyecto_courier.CapaDominio;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public abstract class Paquete implements Serializable {
    private String idPaquete;
    private double peso;
    private double valorContenido;
    private boolean tieneSeguro;
    private double porcentajeSeguro;
    private RutaSeguimiento ruta;
    @Serial
    private static final long serialVersionUID = 1L;
    public Paquete(String idPaquete, double peso, double valorContenido, boolean tieneSeguro, double porcentajeSeguro, RutaSeguimiento ruta) {
        this.idPaquete = idPaquete;
        this.peso = peso;
        this.valorContenido = valorContenido;
        this.tieneSeguro = tieneSeguro;
        this.porcentajeSeguro = porcentajeSeguro;
        this.ruta = ruta;
    }

    public Paquete(){

    }

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

    public RutaSeguimiento getRuta() { return ruta; }
    public void setRuta(RutaSeguimiento ruta) { this.ruta = ruta; }

    public abstract double calcularCostoBase(List<Rango> rangos);

    public double calcularCostoSeguro() {

        if (tieneSeguro) {
            return valorContenido * (porcentajeSeguro / 100.0);
        }
        return 0.0;
    }
}
