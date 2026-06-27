package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.Paquete;
import com.ucuenca.proyecto_courier.CapaDominio.Caja;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.PaqueteService;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.PaqueteDAO;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    public PaqueteDTO mostrarPaquete(String idPaquete) {
        if (paqueteDAO != null) {
            Paquete p = paqueteDAO.leer(idPaquete);
            if (p != null) {
                PaqueteDTO dto = new PaqueteDTO();
                dto.setIdPaquete(p.getIdPaquete());
                dto.setPeso(p.getPeso());
                dto.setValorContenido(p.getValorContenido());
                dto.setTieneSeguro(p.isTieneSeguro());
                dto.setPorcentajeSeguro(p.getPorcentajeSeguro());
                return dto;
            }
        }
        return null;
    }

    @Override
    public void registrarLlegadaPaquete(String idPaquete, Date fecha) {
        if (paqueteDAO != null) {
            Paquete p = paqueteDAO.leer(idPaquete);
            if (p != null && p.getRuta() != null && p.getRuta().getPuntosIntermedios() != null) {
                var puntos = p.getRuta().getPuntosIntermedios();
                if (!puntos.isEmpty()) {
                    var ultimoPunto = puntos.get(puntos.size() - 1);
                    ultimoPunto.setHoraLlegada(LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault()));
                    paqueteDAO.actualizar(p);
                }
            }
        }
    }

    @Override
    public void registrarSalidaPaquete(String idPaquete, Date fecha) {
        if (paqueteDAO != null) {
            Paquete p = paqueteDAO.leer(idPaquete);
            if (p != null && p.getRuta() != null && p.getRuta().getPuntosIntermedios() != null) {
                var puntos = p.getRuta().getPuntosIntermedios();
                if (!puntos.isEmpty()) {
                    var ultimoPunto = puntos.get(puntos.size() - 1);
                    ultimoPunto.setHoraSalida(LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault()));
                    paqueteDAO.actualizar(p);
                }
            }
        }
    }
}
