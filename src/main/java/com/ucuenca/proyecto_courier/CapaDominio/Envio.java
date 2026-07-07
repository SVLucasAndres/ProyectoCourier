package com.ucuenca.proyecto_courier.CapaDominio;

import com.ucuenca.proyecto_courier.CapaDominio.Enums.MetodoPago;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.TipoServicio;

import java.util.ArrayList;
import java.util.List;

public class Envio {
    private String idEnvio;
    private String idRemitente;
    private String idDestinatario;
    private List<Paquete> listaPaquetes = new ArrayList<>();
    private List<String> listaIdPaquetes = new ArrayList<>();
    private TipoServicio rapidez;
    private MetodoPago metodoPago;

    public Envio(String idEnvio, String idRemitente, String idDestinatario, List<Paquete> listaPaquetes,
                 TipoServicio rapidez, MetodoPago metodoPago) {
        this.idEnvio = idEnvio;
        this.idRemitente = idRemitente;
        this.idDestinatario = idDestinatario;
        this.listaPaquetes = listaPaquetes;
        this.rapidez = rapidez;
        this.metodoPago = metodoPago;
    }

    public Envio() {
    }

    public String getIdEnvio() { return idEnvio; }
    public void setIdEnvio(String idEnvio) { this.idEnvio = idEnvio; }

    public String getIdRemitente() { return idRemitente; }
    public void setIdRemitente(String idRemitente) { this.idRemitente = idRemitente; }

    public String getIdDestinatario() { return idDestinatario; }
    public void setIdDestinatario(String idDestinatario) { this.idDestinatario = idDestinatario; }

    public List<Paquete> getListaPaquetes() { return listaPaquetes; }
    public void setListaPaquetes(List<Paquete> listaPaquetes) { this.listaPaquetes = listaPaquetes; }

    public List<String> getListaIdPaquetes() { return listaIdPaquetes; }
    public void setListaIdPaquetes(List<String> listaIdPaquetes) { this.listaIdPaquetes = listaIdPaquetes; }

    public TipoServicio getRapidez() { return rapidez; }
    public void setRapidez(TipoServicio rapidez) { this.rapidez = rapidez; }

    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }

    public double calcularCostoTotal(List<Rango> rangos, double iva) {
        double total = 0.0;
        if (listaPaquetes != null) {
            for (Paquete p : listaPaquetes) {
                total += p.calcularCostoBase(rangos) + p.calcularCostoSeguro();
            }
        }
        return total + (total * (iva / 100.0));
    }
}