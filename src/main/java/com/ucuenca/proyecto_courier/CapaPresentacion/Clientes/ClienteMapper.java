package com.ucuenca.proyecto_courier.CapaPresentacion.Clientes;

import com.ucuenca.proyecto_courier.CapaDominio.Cliente;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteDTO;

public class ClienteMapper {

    // DE MODELO A DTO
    public static ClienteDTO modeloToDto(ClienteModel modelo) {
        return new ClienteDTO(
                modelo.getIdCliente(),
                modelo.getNombre(),
                modelo.getDireccion(),
                modelo.getTelefono(),
                true
        );
    }
    // DE DTO A MODELO
    public static ClienteModel dtoToModelo(ClienteDTO dto) {
        ClienteModel modelo = new ClienteModel();
        modelo.setIdCliente(dto.getIdCliente());
        modelo.setNombre(dto.getNombre());
        modelo.setDireccion(dto.getDireccion());
        modelo.setTelefono(dto.getTelefono());
        return modelo;
    }

}