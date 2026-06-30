package com.ucuenca.proyecto_courier.CapaDominio.DTO;

public class CajaDTO extends PaqueteDTO {
    private double alto;
    private double ancho;
    private double largo;

    public CajaDTO() {}

    public double getAlto() { return alto; }
    public void setAlto(double alto) { this.alto = alto; }
    public double getAncho() { return ancho; }
    public void setAncho(double ancho) { this.ancho = ancho; }
    public double getLargo() { return largo; }
    public void setLargo(double largo) { this.largo = largo; }
}
