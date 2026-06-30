package com.ucuenca.proyecto_courier.CapaDominio.interfaces;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;
import java.util.Date;
import java.util.List;

public interface PaqueteService {
    void crearPaquete(PaqueteDTO paquete);
    PaqueteDTO buscarPaquetePorID(String idPaquete);
    void registrarLlegadaPaquete(String idPaquete, Date fecha);
    void registrarSalidaPaquete(String idPaquete, Date fecha);
    List<PaqueteDTO> mostrarListaPaquetes();
}
