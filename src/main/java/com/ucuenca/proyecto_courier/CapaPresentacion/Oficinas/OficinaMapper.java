package com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.OficinaDTO;

public class OficinaMapper {
    //DE MODELO A DTO
    public static OficinaDTO modeloToDto(OficinaModel modelo){
        return new OficinaDTO(
            modelo.getIdOficina(),
            modelo.getNombre(),
            modelo.getTelefono(),
            modelo.getDireccion()
        );
    }
    //DE DTO A MODELO
    public static OficinaModel dtoToModelo(OficinaDTO dto){
        OficinaModel modelo = new OficinaModel();

        modelo.setIdOficina(dto.getIdOficina());
        modelo.setNombre(dto.getNombre());
        modelo.setDireccion(dto.getDireccion());
        modelo.setTelefono(dto.getTelefono());

        return modelo;
    }
}
