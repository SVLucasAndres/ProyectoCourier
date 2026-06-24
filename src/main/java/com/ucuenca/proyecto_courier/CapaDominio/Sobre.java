package com.ucuenca.proyecto_courier.CapaDominio;

import com.ucuenca.proyecto_courier.CapaDominio.Enums.Tamano;

import java.util.List;

public class Sobre extends Paquete {
    private Tamano tamano;

    public Sobre(String idPaquete, double peso, double valorContenido, boolean tieneSeguro, double porcentajeSeguro, RutaSeguimiento ruta, Tamano tamano) {
        super(idPaquete, peso, valorContenido, tieneSeguro, porcentajeSeguro, ruta);
        this.tamano = tamano;
    }

    @Override
    public double calcularCostoBase(List<Rango> rangos) {
        return 0.0;
    }
}
