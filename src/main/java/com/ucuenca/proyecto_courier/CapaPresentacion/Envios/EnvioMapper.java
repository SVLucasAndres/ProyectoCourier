package com.ucuenca.proyecto_courier.CapaPresentacion.Envios;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.MetodoPago;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.TipoServicio;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ClienteModel;

public class EnvioMapper {

    // DE MODELO A DTO
    public static EnvioDTO modeloToDto(EnvioModel modelo) {
        return new EnvioDTO(
                modelo.getIdRemitente(),
                modelo.getIdEnvio(),
                modelo.getIdDestinatario(),
                TipoServicio.valueOf(modelo.getRapidez()),
                MetodoPago.valueOf(modelo.getMetodoPago())
        );
    }
    // DE DTO A MODELO
    public static EnvioModel dtoToModelo(EnvioDTO dto) {
        EnvioModel modelo = new EnvioModel();
        modelo.setIdDestinatario(dto.getIdDestinatario());
        modelo.setIdEnvio(dto.getIdEnvio());
        modelo.setRapidez(dto.getRapidez().toString());
        modelo.setMetodoPago(dto.getMetodoPago().toString());
        return modelo;
    }

}
