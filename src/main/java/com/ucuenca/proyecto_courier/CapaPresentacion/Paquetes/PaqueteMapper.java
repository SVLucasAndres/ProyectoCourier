package com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.CajaDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.OficinaDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.SobreDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.Tamano;


import com.ucuenca.proyecto_courier.GestorServicios;
import com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.OficinaMapper;
import com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.OficinaModel;
import javafx.beans.binding.ListBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class PaqueteMapper {

    // DE MODELO A DTO (En tu PaqueteMapper)
    public static PaqueteDTO modeloToDto(PaqueteModel modelo) {
        List<OficinaDTO> listaOficinas = new ArrayList<>();

        if (modelo.getListaOficinasTexto() != null) {
            for (String idOficina : modelo.getListaOficinasTexto()) {
                OficinaDTO oDto = new OficinaDTO();
                oDto.setIdOficina(idOficina);
                listaOficinas.add(oDto);
            }
        }

        if (modelo instanceof SobreModel) {
            String tamanoStr = ((SobreModel) modelo).getTamano();

            Tamano tamanoEnum = (tamanoStr != null && !tamanoStr.trim().isEmpty())
                    ? Tamano.valueOf(tamanoStr.trim())
                    : Tamano.MEDIANO;

            return new SobreDTO(
                    modelo.getIdPaquete(),
                    modelo.getPeso(),
                    modelo.getValor(),
                    modelo.getSeguro(),
                    tamanoEnum,
                    listaOficinas
            );
        } else {
            return new CajaDTO(
                    modelo.getIdPaquete(),
                    modelo.getPeso(),
                    modelo.getValor(),
                    modelo.getSeguro(),
                    ((CajaModel) modelo).getAlto(),
                    ((CajaModel) modelo).getAncho(),
                    ((CajaModel) modelo).getLargo(),
                    listaOficinas
            );
        }
    }
    // DE DTO A MODELO
    public static PaqueteModel dtoToModelo(PaqueteDTO dto) {
        PaqueteModel modelo;

        if (dto instanceof SobreDTO) {
            SobreModel sobreModel = new SobreModel();
            sobreModel.setIdPaquete(dto.getIdPaquete());
            sobreModel.setPeso(dto.getPeso());
            sobreModel.setSeguro(dto.isTieneSeguro());
            sobreModel.setValor(dto.getValorContenido());

            if (((SobreDTO) dto).getTamano() != null) {
                sobreModel.setTamano(((SobreDTO) dto).getTamano().toString());
            } else {
                sobreModel.setTamano(Tamano.MEDIANO.toString());
            }
            modelo = sobreModel;

        } else {
            CajaModel cajaModel = new CajaModel();
            cajaModel.setIdPaquete(dto.getIdPaquete());
            cajaModel.setPeso(dto.getPeso());
            cajaModel.setSeguro(dto.isTieneSeguro());
            cajaModel.setValor(dto.getValorContenido());
            cajaModel.setAlto(((CajaDTO) dto).getAlto());
            cajaModel.setAncho(((CajaDTO) dto).getAncho());
            cajaModel.setLargo(((CajaDTO) dto).getLargo());
            modelo = cajaModel;
        }

        if (dto.getPuntosRuta() != null && modelo.listaOficinasTextoProperty() != null) {
            modelo.listaOficinasTextoProperty().setAll(dto.getPuntosRuta());
        }

        return modelo;
    }
}