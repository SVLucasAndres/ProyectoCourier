package com.ucuenca.proyecto_courier.CapaDominio.interfaces;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteDTO;

public interface ClienteService {
    void crearCliente(ClienteDTO cliente);
    void modificarCliente(ClienteDTO cliente);
    void archivarCliente(ClienteDTO cliente);
    ClienteDTO mostrarCliente(String idCliente);
}
