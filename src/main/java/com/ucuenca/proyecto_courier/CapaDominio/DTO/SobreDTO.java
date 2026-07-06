package com.ucuenca.proyecto_courier.CapaDominio.DTO;

import com.ucuenca.proyecto_courier.CapaDominio.Enums.Tamano;

import java.util.List;

public class SobreDTO extends PaqueteDTO {
    private Tamano tamano;

    public SobreDTO() {}

    public SobreDTO(String idPaquete, double peso, double valorContenido, boolean tieneSeguro, Tamano tamano, List<OficinaDTO> listaOficinas) {
        super(idPaquete,peso,valorContenido,tieneSeguro,listaOficinas);
        this.tamano = tamano;
    }

    public Tamano getTamano() { return tamano; }
    public void setTamano(Tamano tamano) { this.tamano = tamano; }
}
