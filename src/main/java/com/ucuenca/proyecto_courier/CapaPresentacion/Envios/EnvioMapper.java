package com.ucuenca.proyecto_courier.CapaPresentacion.Envios;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.CajaDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.EstadoEnvio;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.MetodoPago;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.TipoServicio;

import java.util.ArrayList;
import java.util.List;

public class EnvioMapper {

    // MODELO A DTO
    public static EnvioDTO modeloToDto(EnvioModel modelo) {
        EnvioDTO dto = new EnvioDTO(
                modelo.getIdRemitente(),
                modelo.getIdEnvio(),
                modelo.getIdDestinatario(),
                TipoServicio.valueOf(modelo.getRapidez()),
                MetodoPago.valueOf(modelo.getMetodoPago()),
                EstadoEnvio.valueOf(modelo.getEstadoEnvio())
        );

        List<PaqueteDTO> listaPaquetesDto = new ArrayList<>();
        if (modelo.getListaIdPaquetes() != null) {
            for (String idPack : modelo.getListaIdPaquetes()) {
                PaqueteDTO pDto = new CajaDTO();
                pDto.setIdPaquete(idPack);
                listaPaquetesDto.add(pDto);
            }
        }
        dto.setListaPaquetes(listaPaquetesDto);

        return dto;
    }

    // DTO A MODELO
    public static EnvioModel dtoToModelo(EnvioDTO dto) {
        EnvioModel modelo = new EnvioModel();
        modelo.setIdDestinatario(dto.getIdDestinatario());
        modelo.setIdRemitente(dto.getIdRemitente());
        modelo.setIdEnvio(dto.getIdEnvio());
        modelo.setRapidez(dto.getRapidez().toString());
        modelo.setMetodoPago(dto.getMetodoPago().toString());
        modelo.setEstadoEnvio(dto.getEstadoEnvio().toString());

        List<String> ids = new ArrayList<>();
        if (dto.getListaPaquetes() != null) {
            for (PaqueteDTO p : dto.getListaPaquetes()) {
                ids.add(p.getIdPaquete());
            }
            modelo.setListaIdPaquetes(ids);
            modelo.setCantidadPaquetes(dto.getListaPaquetes().size());
        } else {
            modelo.setListaIdPaquetes(ids);
            modelo.setCantidadPaquetes(0);
        }
        return modelo;
    }
}