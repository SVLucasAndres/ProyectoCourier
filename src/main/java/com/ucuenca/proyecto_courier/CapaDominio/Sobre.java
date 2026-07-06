package com.ucuenca.proyecto_courier.CapaDominio;

import com.ucuenca.proyecto_courier.CapaDominio.Enums.Tamano;

import java.util.List;

public class Sobre extends Paquete {
    private Tamano tamano;

    public Sobre(String idPaquete, double peso, double valorContenido, boolean tieneSeguro, double porcentajeSeguro,
            RutaSeguimiento ruta, Tamano tamano) {
        super(idPaquete, peso, valorContenido, tieneSeguro, porcentajeSeguro, ruta);
        this.tamano = tamano;
    }

    public Sobre(){}

    @Override
    public double calcularCostoBase(List<Rango> rangos) {
        if (rangos != null) {
            for (Rango rango : rangos) {
                if (getPeso() >= rango.getPesoMinimo() && getPeso() <= rango.getPesoMaximo()) {
                    return getPeso() * rango.getCostoPorKilogramo();
                }
            }
        }
        return 10.0;
    }

    public Tamano getTamano() {
        return tamano;
    }
    public void setTamano(Tamano tamano) {
        this.tamano = tamano;
    }
}
