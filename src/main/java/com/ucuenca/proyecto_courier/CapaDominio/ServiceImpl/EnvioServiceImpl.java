package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.interfaces.EnvioService;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;

public class EnvioServiceImpl implements EnvioService {
    @Override
    public void realizarEnvio(EnvioDTO envio) {
    }

    @Override
    public EnvioDTO mostrarEnvio() {
        return null;
    }

    @Override
    public double obtenerCostoTotalEnvio() {
        return 0.0;
    }
}
