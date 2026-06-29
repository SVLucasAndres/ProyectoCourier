package com.ucuenca.proyecto_courier.CapaDominio;

import java.io.Serializable;
import java.util.List;

public abstract class Paquete implements Serializable {
    private String idPaquete;
    private double peso;
    private double valorContenido;
    private boolean tieneSeguro;
    private double porcentajeSeguro;
    private RutaSeguimiento ruta;

    public Paquete() {
    }

    public abstract double calcularCostoBase(List<Rango> rangos);

    public double calcularCostoSeguro() {
        if (tieneSeguro) {
            return valorContenido * (porcentajeSeguro / 100.0);
        }
        return 0.0;
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

    public RutaSeguimiento getRuta() {
        return ruta;
    }

    public void setRuta(RutaSeguimiento ruta) {
        this.ruta = ruta;
    }

    public enum Tamano {
        XS, PEQUENO, MEDIANO, GRANDE, XL
    }

    public static class Rango implements Serializable {
        private String nombre;
        private double pesoMinimo;
        private double pesoMaximo;
        private double costoPorKilogramo;

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public double getPesoMinimo() { return pesoMinimo; }
        public void setPesoMinimo(double pesoMinimo) { this.pesoMinimo = pesoMinimo; }
        public double getPesoMaximo() { return pesoMaximo; }
        public void setPesoMaximo(double pesoMaximo) { this.pesoMaximo = pesoMaximo; }
        public double getCostoPorKilogramo() { return costoPorKilogramo; }
        public void setCostoPorKilogramo(double costoPorKilogramo) { this.costoPorKilogramo = costoPorKilogramo; }
    }

    public static class Sobre extends Paquete {
        private Tamano tamano;

        @Override
        public double calcularCostoBase(List<Rango> rangos) {
            return calcularCostoBase();
        }

        public double calcularCostoBase() {
            return 0.0;
        }

        public Tamano getTamano() { return tamano; }
        public void setTamano(Tamano tamano) { this.tamano = tamano; }
    }

    public static class Caja extends Paquete {
        private double alto;
        private double ancho;
        private double largo;

        @Override
        public double calcularCostoBase(List<Rango> rangos) {
            return calcularCostoBase();
        }

        public double calcularCostoBase() {
            return 0.0;
        }

        public double getAlto() { return alto; }
        public void setAlto(double alto) { this.alto = alto; }
        public double getAncho() { return ancho; }
        public void setAncho(double ancho) { this.ancho = ancho; }
        public double getLargo() { return largo; }
        public void setLargo(double largo) { this.largo = largo; }
    }
}
