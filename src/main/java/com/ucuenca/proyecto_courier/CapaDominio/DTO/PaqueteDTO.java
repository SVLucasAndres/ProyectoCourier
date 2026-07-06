package com.ucuenca.proyecto_courier.CapaDominio.DTO;

import java.util.List;

public abstract class PaqueteDTO {
    private String idPaquete;
    private double peso;
    private double valorContenido;
    private boolean tieneSeguro;
    private double porcentajeSeguro;
    private List<OficinaDTO> listaOficinas;
    private List<String> puntosRuta;

    public PaqueteDTO() {
    }

    public PaqueteDTO(String idPaquete, double peso, double valorContenido, boolean tieneSeguro, List<OficinaDTO> listaOficinas) {
        this.idPaquete = idPaquete;
        this.peso = peso;
        this.valorContenido = valorContenido;
        this.tieneSeguro = tieneSeguro;
        this.listaOficinas = listaOficinas;
    }

    public String getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(String idPaquete) {
        this.idPaquete = idPaquete;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getValorContenido() {
        return valorContenido;
    }

    public void setValorContenido(double valorContenido) {
        this.valorContenido = valorContenido;
    }

    public boolean isTieneSeguro() {
        return tieneSeguro;
    }

    public void setTieneSeguro(boolean tieneSeguro) {
        this.tieneSeguro = tieneSeguro;
    }

    public double getPorcentajeSeguro() {
        return porcentajeSeguro;
    }

    public void setPorcentajeSeguro(double porcentajeSeguro) {
        this.porcentajeSeguro = porcentajeSeguro;
    }

    public List<OficinaDTO> getListaOficinas() {
        return listaOficinas;
    }

    public void setListaOficinas(List<OficinaDTO> listaOficinas) {
        this.listaOficinas = listaOficinas;
    }

    public List<String> getPuntosRuta() {
        return puntosRuta;
    }

    public void setPuntosRuta(List<String> puntosRuta) {
        this.puntosRuta = puntosRuta;
    }
}