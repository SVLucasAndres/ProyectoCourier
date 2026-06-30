package com.ucuenca.proyecto_courier.CapaDominio.interfaces;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.ConfiguracionDTO;

public interface ConfiguracionService {
    ConfiguracionDTO obtenerConfiguracion();
    void guardarConfiguracion(ConfiguracionDTO configuracion);
}
