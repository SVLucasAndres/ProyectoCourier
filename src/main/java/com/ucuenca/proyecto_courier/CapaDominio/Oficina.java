package com.ucuenca.proyecto_courier.CapaDominio;

public class Oficina {
    private String idOficina = "";
    private String nombre = "";
    private String direccion = "";
    private String telefono = "";
    private boolean active = true;

    public Oficina() {
    }

    public Oficina(String idOficina, String nombre, String direccion, String telefono, boolean active) {
        this.idOficina = idOficina;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.active = active;
    }

    public String getIdOficina() {
        return idOficina;
    }
    public void setIdOficina(String idOficina) {
        this.idOficina = idOficina;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}