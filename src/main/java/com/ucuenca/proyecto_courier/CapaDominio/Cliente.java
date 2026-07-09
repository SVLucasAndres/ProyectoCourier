package com.ucuenca.proyecto_courier.CapaDominio;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Cliente implements Serializable {
    private String idCliente;
    private String nombre;
    private String direccion;
    private String telefono;
    private List<Envio> listaEnvios;
    private boolean isActive;
    private String contrasena;
    @Serial
    private static final long serialVersionUID = 1L;

    public Cliente(String idCliente, String nombre, String direccion, String telefono, List<Envio> listaEnvios, boolean isActive) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.listaEnvios = listaEnvios;
        this.isActive = isActive;
    }

    public Cliente(){
        this.isActive = true;
    }

    public String getIdCliente() { return idCliente; }
    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public List<Envio> getListaEnvios() { return listaEnvios; }
    public void setListaEnvios(List<Envio> listaEnvios) { this.listaEnvios = listaEnvios; }

    public boolean isIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
