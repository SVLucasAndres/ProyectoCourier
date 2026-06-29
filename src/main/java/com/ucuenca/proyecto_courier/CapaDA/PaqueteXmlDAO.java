package com.ucuenca.proyecto_courier.CapaDA;

import com.ucuenca.proyecto_courier.CapaDominio.Paquete;

public class PaqueteXmlDAO extends DAOXML<Paquete> {

    public PaqueteXmlDAO(String rutaArchivo) {
        super(rutaArchivo);
    }

    @Override
    protected String obtenerId(Paquete entidad) {
        return entidad.getIdPaquete();
    }
}
