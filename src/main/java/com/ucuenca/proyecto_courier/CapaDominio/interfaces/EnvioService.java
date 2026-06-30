package com.ucuenca.proyecto_courier.CapaDominio.interfaces;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import java.util.List;

public interface EnvioService {
    void realizarEnvio(EnvioDTO envio);
    EnvioDTO buscarEnvioPorID(String idEnvio);
    double obtenerCostoTotalEnvio(String idEnvio);
    List<EnvioDTO> mostrarListaEnvios();
}
