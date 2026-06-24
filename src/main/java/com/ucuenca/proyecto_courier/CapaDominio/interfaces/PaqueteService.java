package com.ucuenca.proyecto_courier.CapaDominio.interfaces;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;
import java.util.Date;

public interface PaqueteService {
    void crearPaquete(PaqueteDTO paquete);
    PaqueteDTO mostrarPaquete();
    void registrarLlegadaPaquete(Date fecha);
    void registrarSalidaPaquete(Date fecha);
}
