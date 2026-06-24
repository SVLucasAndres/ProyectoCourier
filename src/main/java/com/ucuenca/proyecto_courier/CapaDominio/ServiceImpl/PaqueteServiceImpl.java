package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.interfaces.PaqueteService;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;
import java.util.Date;

public class PaqueteServiceImpl implements PaqueteService {
    @Override
    public void crearPaquete(PaqueteDTO paquete) {
    }

    @Override
    public PaqueteDTO mostrarPaquete() {
        return null;
    }

    @Override
    public void registrarLlegadaPaquete(Date fecha) {
    }

    @Override
    public void registrarSalidaPaquete(Date fecha) {
    }
}
