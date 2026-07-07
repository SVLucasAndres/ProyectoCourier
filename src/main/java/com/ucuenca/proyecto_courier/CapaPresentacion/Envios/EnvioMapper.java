package com.ucuenca.proyecto_courier.CapaPresentacion.Envios;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.MetodoPago;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.TipoServicio;
import java.util.ArrayList;

public class EnvioMapper {

    // DE MODELO A DTO
    public static EnvioDTO modeloToDto(EnvioModel modelo) {
        EnvioDTO dto = new EnvioDTO(
                modelo.getIdRemitente(),
                modelo.getIdEnvio(),
                modelo.getIdDestinatario(),
                TipoServicio.valueOf(modelo.getRapidez()),
                MetodoPago.valueOf(modelo.getMetodoPago())
        );
        return dto;
    }

    // DE DTO A MODELO
    public static EnvioModel dtoToModelo(EnvioDTO dto) {
        EnvioModel modelo = new EnvioModel();
        modelo.setIdDestinatario(dto.getIdDestinatario());
        modelo.setIdRemitente(dto.getIdRemitente());
        modelo.setIdEnvio(dto.getIdEnvio());
        modelo.setRapidez(dto.getRapidez().toString());
        modelo.setMetodoPago(dto.getMetodoPago().toString());
        //modelo.setListaPaquetes(dto.getListaPaquetes());
        return modelo;
    }
}