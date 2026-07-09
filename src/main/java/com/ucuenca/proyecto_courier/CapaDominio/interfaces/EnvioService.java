package com.ucuenca.proyecto_courier.CapaDominio.interfaces;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.ConfiguracionDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import java.util.List;

public interface EnvioService {
    void realizarEnvio(EnvioDTO dto, ConfiguracionDTO configuracion);

    EnvioDTO buscarEnvioPorID(String idEnvio);

    double obtenerCostoTotalEnvio(String idEnvio);

    List<EnvioDTO> mostrarListaEnvios(ConfiguracionDTO configuracion);

    void cancelarEnvio(EnvioDTO envio);
}
