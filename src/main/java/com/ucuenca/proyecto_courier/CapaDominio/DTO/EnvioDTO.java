package com.ucuenca.proyecto_courier.CapaDominio.DTO;

import com.ucuenca.proyecto_courier.CapaDominio.Enums.EstadoEnvio;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.MetodoPago;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.TipoServicio;

import java.util.List;

public class EnvioDTO {
    private String idEnvio;
    private String idRemitente;
    private String idDestinatario;
    private TipoServicio rapidez;
    private MetodoPago metodoPago;
    private double costoTotal;
    private List<PaqueteDTO> listaPaquetes;
    private EstadoEnvio estadoEnvio;

    public EnvioDTO() {}

    public EnvioDTO(String idRemitente, String idEnvio, String idDestinatario, TipoServicio rapidez, MetodoPago metodoPago, EstadoEnvio estadoEnvio) {
        this.idRemitente = idRemitente;
        this.idEnvio = idEnvio;
        this.idDestinatario = idDestinatario;
        this.rapidez = rapidez;
        this.metodoPago = metodoPago;
        this.estadoEnvio = estadoEnvio;
    }

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

    public List<PaqueteDTO> getListaPaquetes() {
        return listaPaquetes;
    }

    public void setListaPaquetes(List<PaqueteDTO> listaPaquetes) {
        this.listaPaquetes = listaPaquetes;
    }

    public EstadoEnvio getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(EstadoEnvio estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }
}
