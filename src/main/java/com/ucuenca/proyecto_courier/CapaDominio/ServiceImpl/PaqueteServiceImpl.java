package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.Paquete;
import com.ucuenca.proyecto_courier.CapaDominio.Caja;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.PaqueteService;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.PaqueteDAO;
import java.util.Date;

public class PaqueteServiceImpl implements PaqueteService {
    private PaqueteDAO paqueteDAO;

    public PaqueteServiceImpl(PaqueteDAO paqueteDAO) {
        this.paqueteDAO = paqueteDAO;
    }

    @Override
    public void crearPaquete(PaqueteDTO paquete) {
        if (paqueteDAO != null) {
            Paquete nuevoPaquete = new Caja(
                paquete.getIdPaquete(), 
                paquete.getPeso(), 
                paquete.getValorContenido(), 
                paquete.isTieneSeguro(), 
                paquete.getPorcentajeSeguro(), 
                null, 0, 0, 0
            );
            paqueteDAO.crear(nuevoPaquete);
        }
    }

    @Override
    public PaqueteDTO mostrarPaquete() {
        return null;
    }

    @Override
    public void registrarLlegadaPaquete(Date fecha) {
        // Lógica de llegada
    }

    @Override
    public void registrarSalidaPaquete(Date fecha) {
        // Lógica de salida
    }
}
