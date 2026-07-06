package com.ucuenca.proyecto_courier.CapaDominio.DTO;

public class OficinaDTO {
    private String idOficina;
    private String nombre;
    private String direccion;
    private String telefono;
    private boolean active;

    public OficinaDTO() {}

    public OficinaDTO(String idOficina, String nombre, String telefono, String direccion) {
        this.idOficina = idOficina;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getIdOficina() { return idOficina; }
    public void setIdOficina(String idOficina) { this.idOficina = idOficina; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
