package com.ucuenca.proyecto_courier.CapaDominio;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.RangoDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.MetodoPago;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.TipoServicio;

import java.util.List;

public class Envio {
    private String idEnvio;
    private String idRemitente;
    private String idDestinatario;
    private List<Paquete> listaPaquetes;
    private TipoServicio rapidez;
    private MetodoPago metodoPago;

    public Envio(String idEnvio, String idRemitente, String idDestinatario, List<Paquete> listaPaquetes, TipoServicio rapidez, MetodoPago metodoPago) {
        this.idEnvio = idEnvio;
        this.idRemitente = idRemitente;
        this.idDestinatario = idDestinatario;
        this.listaPaquetes = listaPaquetes;
        this.rapidez = rapidez;
        this.metodoPago = metodoPago;
    }

    public Envio(){

    }

    public String getIdEnvio() { return idEnvio; }
    public String getRemitente() { return idRemitente; }
    public String getDestinatario() { return idDestinatario; }
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
