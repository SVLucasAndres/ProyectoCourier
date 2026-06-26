package com.ucuenca.proyecto_courier.CapaDominio.DTO;

import com.ucuenca.proyecto_courier.CapaDominio.Enums.MetodoPago;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.TipoServicio;

public class EnvioDTO {
    private String idEnvio;
    private String idRemitente;
    private String idDestinatario;
    private TipoServicio rapidez;
    private MetodoPago metodoPago;
    private double costoTotal;

    public EnvioDTO() {}

    public String getIdEnvio() { return idEnvio; }
    public void setIdEnvio(String idEnvio) { this.idEnvio = idEnvio; }

    public String getIdRemitente() { return idRemitente; }
    public void setIdRemitente(String idRemitente) { this.idRemitente = idRemitente; }

    public String getIdDestinatario() { return idDestinatario; }
    public void setIdDestinatario(String idDestinatario) { this.idDestinatario = idDestinatario; }

    public TipoServicio getRapidez() { return rapidez; }
    public void setRapidez(TipoServicio rapidez) { this.rapidez = rapidez; }

    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
    
    public double getCostoTotal() { return costoTotal; }
    public void setCostoTotal(double costoTotal) { this.costoTotal = costoTotal; }
}
