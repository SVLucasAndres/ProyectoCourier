package com.ucuenca.proyecto_courier.CapaDA;

import com.ucuenca.proyecto_courier.CapaDominio.Configuracion;

public class ConfiguracionXmlDAO extends DAOXML<Configuracion> {

    public ConfiguracionXmlDAO(String rutaArchivo) {
        super(rutaArchivo);
    }

    @Override
    protected String obtenerId(Configuracion entidad) {
        return entidad.getIdConfiguracion();
    }
}
