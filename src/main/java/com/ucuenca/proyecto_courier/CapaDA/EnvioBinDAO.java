package com.ucuenca.proyecto_courier.CapaDA;

import com.ucuenca.proyecto_courier.CapaDominio.Envio;

public class EnvioBinDAO extends DAOBIN<Envio> {

    public EnvioBinDAO(String rutaArchivo) {
        super(rutaArchivo);
    }

    @Override
    protected String obtenerId(Envio entidad) {
        return entidad.getIdEnvio();
    }
}
