package com.ucuenca.proyecto_courier.CapaDA;

import com.ucuenca.proyecto_courier.CapaDominio.Envio;

public class EnvioXmlDAO extends DAOXML<Envio> {

    public EnvioXmlDAO(String rutaArchivo) {
        super(rutaArchivo);
    }

    @Override
    protected String obtenerId(Envio entidad) {
        return entidad.getIdEnvio();
    }
}
