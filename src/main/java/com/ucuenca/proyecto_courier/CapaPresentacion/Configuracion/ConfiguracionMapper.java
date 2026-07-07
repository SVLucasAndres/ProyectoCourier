package com.ucuenca.proyecto_courier.CapaPresentacion.Configuracion;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.ConfiguracionDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.RangoDTO;
import java.util.ArrayList;
import java.util.List;

public class ConfiguracionMapper {

    public static ConfiguracionModel dtoToModelo(ConfiguracionDTO dto) {
        if (dto == null) return null;

        ConfiguracionModel model = new ConfiguracionModel();
        model.setIdConfiguracion(dto.getIdConfiguracion());
        model.setImpuestoIVA(dto.getImpuestoIVA());

        List<RangoModel> listaRangosModel = new ArrayList<>();
        if (dto.getRangos() != null) {
            for (RangoDTO rDto : dto.getRangos()) {
                RangoModel rModel = new RangoModel();
                rModel.setNombre(rDto.getNombre());
                rModel.setPesoMinimo(rDto.getPesoMinimo());
                rModel.setPesoMaximo(rDto.getPesoMaximo());
                rModel.setCostoPorKilogramo(rDto.getCostoPorKilogramo());
                listaRangosModel.add(rModel);
            }
        }
        model.getRangos().setAll(listaRangosModel);
        return model;
    }

    public static ConfiguracionDTO modeloToDto(ConfiguracionModel model) {
        if (model == null) return null;

        ConfiguracionDTO dto = new ConfiguracionDTO();
        dto.setIdConfiguracion(model.getIdConfiguracion());
        dto.setImpuestoIVA(model.getImpuestoIVA());

        List<RangoDTO> listaRangosDto = new ArrayList<>();
        if (model.getRangos() != null) {
            for (RangoModel rModel : model.getRangos()) {
                RangoDTO rDto = new RangoDTO();
                rDto.setNombre(rModel.getNombre());
                rDto.setPesoMinimo(rModel.getPesoMinimo());
                rDto.setPesoMaximo(rModel.getPesoMaximo());
                rDto.setCostoPorKilogramo(rModel.getCostoPorKilogramo());
                listaRangosDto.add(rDto);
            }
        }
        dto.setRangos(listaRangosDto);
        return dto;
    }
}