package com.ucuenca.proyecto_courier.CapaDominio;

import java.io.Serializable;
import java.util.List;

public class Envio implements Serializable {

    public enum TipoServicio {
        ENTREGA_SIGUIENTE_DIA,
        SEGUNDO_DIA,
        NORMAL
    }

    public enum MetodoPago {
        EFECTIVO,
        TARJETA_CREDITO_DEBITO,
        PAYPAL
    }

    private String idEnvio;
    private Cliente remitente;
    private Cliente destinatario;
    private List<Paquete> listaPaquetes;
    private TipoServicio rapidez;
    private MetodoPago metodoPago;

    public Envio() {
    }

    public double calcularCostoTotal() {
        // Logica para calcular el costo total sumando paquetes
        return 0.0;
    }

    public String getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Cliente getRemitente() {
        return remitente;
    }

    public void setRemitente(Cliente remitente) {
        this.remitente = remitente;
    }

    public Cliente getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Cliente destinatario) {
        this.destinatario = destinatario;
    }

    public List<Paquete> getListaPaquetes() {
        return listaPaquetes;
    }

    public void setListaPaquetes(List<Paquete> listaPaquetes) {
        this.listaPaquetes = listaPaquetes;
    }

    public TipoServicio getRapidez() {
        return rapidez;
    }

    public void setRapidez(TipoServicio rapidez) {
        this.rapidez = rapidez;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }
}
