package com.ucuenca.proyecto_courier.CapaDominio.interfaces;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteEnviosDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Rango;

import java.util.List;

public interface ClienteService {
    void crearCliente(ClienteDTO cliente);
    void modificarCliente(ClienteDTO cliente);
    void archivarCliente(ClienteDTO cliente);
    ClienteDTO buscarClientePorID(String id);
    ClienteDTO buscarClientePorNombre(String nombre);
    List<ClienteDTO> mostrarListaClientes();
    ClienteEnviosDTO obtenerListadoPaquetesPorIdCliente(String id, List<Rango> rangos, double iva);
}
