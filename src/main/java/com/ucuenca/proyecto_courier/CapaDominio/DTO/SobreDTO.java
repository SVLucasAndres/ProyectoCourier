package com.ucuenca.proyecto_courier.CapaDominio.DTO;

import com.ucuenca.proyecto_courier.CapaDominio.Enums.Tamano;

public class SobreDTO extends PaqueteDTO {
    private Tamano tamano;

    public SobreDTO() {}

    public Tamano getTamano() { return tamano; }
    public void setTamano(Tamano tamano) { this.tamano = tamano; }
}
