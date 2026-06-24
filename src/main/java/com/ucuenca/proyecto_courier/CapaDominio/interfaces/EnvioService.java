package com.ucuenca.proyecto_courier.CapaDominio.interfaces;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;

public interface EnvioService {
    void realizarEnvio(EnvioDTO envio);
    EnvioDTO mostrarEnvio();
    double obtenerCostoTotalEnvio();
}
