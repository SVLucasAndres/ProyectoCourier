package com.ucuenca.proyecto_courier.CapaDominio.interfaces;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;
import java.util.Date;

public interface PaqueteService {
    void crearPaquete(PaqueteDTO paquete);
    PaqueteDTO mostrarPaquete(String idPaquete);
    void registrarLlegadaPaquete(String idPaquete, Date fecha);
    void registrarSalidaPaquete(String idPaquete, Date fecha);
}
