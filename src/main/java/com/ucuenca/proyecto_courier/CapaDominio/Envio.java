package com.ucuenca.proyecto_courier.CapaDominio;

import com.ucuenca.proyecto_courier.CapaDominio.Enums.MetodoPago;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.TipoServicio;

import java.util.List;

public class Envio {
    private String idEnvio;
    private Cliente remitente;
    private Cliente destinatario;
    private List<Paquete> listaPaquetes;
    private TipoServicio rapidez;
    private MetodoPago metodoPago;
    private RutaSeguimiento ruta;

    public Envio() {
    }

    public double calcularCostoTotal() {
        return 0.0;
    }
}
