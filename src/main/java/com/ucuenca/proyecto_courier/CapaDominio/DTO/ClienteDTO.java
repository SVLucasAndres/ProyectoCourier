package com.ucuenca.proyecto_courier.CapaDominio.DTO;

public class ClienteDTO {
    private String idCliente;
    private String nombre;
    private String direccion;
    private String telefono;
    private boolean active;

    public ClienteDTO() {}

    public ClienteDTO(String idCliente, String nombre, String direccion, String telefono, boolean active) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.active = active;
    }

    public String getIdCliente() { return idCliente; }
    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
