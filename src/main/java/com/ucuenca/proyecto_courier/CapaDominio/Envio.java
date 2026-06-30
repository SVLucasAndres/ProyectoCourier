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

    public Envio(String idEnvio, Cliente remitente, Cliente destinatario, List<Paquete> listaPaquetes, TipoServicio rapidez, MetodoPago metodoPago) {
        this.idEnvio = idEnvio;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.listaPaquetes = listaPaquetes;
        this.rapidez = rapidez;
        this.metodoPago = metodoPago;
    }

    public String getIdEnvio() { return idEnvio; }
    public Cliente getRemitente() { return remitente; }
    public Cliente getDestinatario() { return destinatario; }
    public List<Paquete> getListaPaquetes() { return listaPaquetes; }
    public TipoServicio getRapidez() { return rapidez; }
    public MetodoPago getMetodoPago() { return metodoPago; }

    public double calcularCostoTotal(List<Rango> rangos) {
        double total = 0.0;
        if (listaPaquetes != null) {
            for (Paquete p : listaPaquetes) {
                total += p.calcularCostoBase(rangos) + p.calcularCostoSeguro();
            }
        }
        return total;
    }

}
