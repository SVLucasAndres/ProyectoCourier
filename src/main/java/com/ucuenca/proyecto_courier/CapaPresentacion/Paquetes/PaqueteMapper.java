package com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.CajaDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.SobreDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.MetodoPago;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.Tamano;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.TipoServicio;
import com.ucuenca.proyecto_courier.CapaDominio.Sobre;

public class PaqueteMapper {
    // DE MODELO A DTO
    public static PaqueteDTO modeloToDto(PaqueteModel modelo) {
        if(modelo instanceof SobreModel){
            return new SobreDTO(
                    modelo.getIdPaquete(),
                    modelo.getPeso(),
                    modelo.getValor(),
                    modelo.getSeguro(),
                    Tamano.valueOf(((SobreModel) modelo).getTamano())
            );
        }else{
            return new CajaDTO(
                    modelo.getIdPaquete(),
                    modelo.getPeso(),
                    modelo.getValor(),
                    modelo.getSeguro(),
                    ((CajaModel) modelo).getAlto(),
                    ((CajaModel) modelo).getAncho(),
                    ((CajaModel) modelo).getLargo()
            );
        }
    }
    // DE DTO A MODELO
    public static PaqueteModel dtoToModelo(PaqueteDTO dto) {
        if(dto instanceof SobreDTO){
            SobreModel modelo = new SobreModel();
            modelo.setIdPaquete(dto.getIdPaquete());
            modelo.setPeso(dto.getPeso());
            modelo.setSeguro(dto.isTieneSeguro());
            modelo.setValor(dto.getValorContenido());
            modelo.setTamano(((SobreDTO) dto).getTamano().toString());
            return modelo;
        }else{
            CajaModel modelo = new CajaModel();
            modelo.setIdPaquete(dto.getIdPaquete());
            modelo.setPeso(dto.getPeso());
            modelo.setSeguro(dto.isTieneSeguro());
            modelo.setValor(dto.getValorContenido());
            modelo.setAlto(((CajaDTO) dto).getAlto());
            modelo.setAncho(((CajaDTO) dto).getAncho());
            modelo.setLargo(((CajaDTO) dto).getLargo());
            return modelo;
        }
    }
}
