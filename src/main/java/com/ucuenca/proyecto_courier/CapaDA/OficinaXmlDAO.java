package com.ucuenca.proyecto_courier.CapaDA;

import com.ucuenca.proyecto_courier.CapaDominio.Oficina;

public class OficinaXmlDAO extends DAOXML<Oficina> {

    public OficinaXmlDAO(String rutaArchivo) {
        super(rutaArchivo);
    }

    @Override
    protected String obtenerId(Oficina entidad) {
        return entidad.getIdOficina();
    }
}
