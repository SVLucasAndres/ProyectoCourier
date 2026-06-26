package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.Envio;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.EnvioService;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.EnvioDAO;
import java.util.ArrayList;

public class EnvioServiceImpl implements EnvioService {
    private EnvioDAO envioDAO;

    public EnvioServiceImpl(EnvioDAO envioDAO) {
        this.envioDAO = envioDAO;
    }

    @Override
    public void realizarEnvio(EnvioDTO envio) {
        if (envioDAO != null) {
            Envio nuevoEnvio = new Envio(
                envio.getIdEnvio(), 
                null, 
                null, 
                new ArrayList<>(), 
                envio.getRapidez(), 
                envio.getMetodoPago()
            );
            envioDAO.crear(nuevoEnvio);
        }
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
