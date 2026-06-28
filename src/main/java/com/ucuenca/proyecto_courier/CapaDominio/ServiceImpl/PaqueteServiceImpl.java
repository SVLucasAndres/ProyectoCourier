package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.Paquete;
import com.ucuenca.proyecto_courier.CapaDominio.Caja;
import com.ucuenca.proyecto_courier.CapaDominio.Sobre;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.PaqueteService;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.DAO;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class PaqueteServiceImpl implements PaqueteService {
    private DAO<Paquete> paqueteDAO;

    public PaqueteServiceImpl(DAO<Paquete> paqueteDAO) {
        this.paqueteDAO = paqueteDAO;
    }

    @Override
    public void crearPaquete(PaqueteDTO paquete) {
        if (paqueteDAO != null) {
            Paquete nuevoPaquete = null;
            if ("CAJA".equalsIgnoreCase(paquete.getTipo())) {
                nuevoPaquete = new Caja(
                    paquete.getIdPaquete(), 
                    paquete.getPeso(), 
                    paquete.getValorContenido(), 
                    paquete.isTieneSeguro(), 
                    paquete.getPorcentajeSeguro(), 
                    null, 
                    paquete.getAlto(), 
                    paquete.getAncho(), 
                    paquete.getLargo()
                );
            } else if ("SOBRE".equalsIgnoreCase(paquete.getTipo())) {
                nuevoPaquete = new Sobre(
                    paquete.getIdPaquete(), 
                    paquete.getPeso(), 
                    paquete.getValorContenido(), 
                    paquete.isTieneSeguro(), 
                    paquete.getPorcentajeSeguro(), 
                    null, 
                    paquete.getTamano()
                );
            }
            if (nuevoPaquete != null) {
                paqueteDAO.guardar(nuevoPaquete);
            }
        }
    }

    @Override
    public PaqueteDTO mostrarPaquete(String idPaquete) {
        if (paqueteDAO != null) {
            Optional<Paquete> opt = paqueteDAO.buscarPorId(idPaquete);
            if (opt.isPresent()) {
                Paquete p = opt.get();
                PaqueteDTO dto = new PaqueteDTO();
                dto.setIdPaquete(p.getIdPaquete());
                dto.setPeso(p.getPeso());
                dto.setValorContenido(p.getValorContenido());
                dto.setTieneSeguro(p.isTieneSeguro());
                dto.setPorcentajeSeguro(p.getPorcentajeSeguro());
                
                if (p instanceof Caja) {
                    Caja c = (Caja) p;
                    dto.setTipo("CAJA");
                    dto.setAlto(c.getAlto());
                    dto.setAncho(c.getAncho());
                    dto.setLargo(c.getLargo());
                } else if (p instanceof Sobre) {
                    Sobre s = (Sobre) p;
                    dto.setTipo("SOBRE");
                    dto.setTamano(s.getTamano());
                }
                return dto;
            }
        }
        return null;
    }

    @Override
    public void registrarLlegadaPaquete(String idPaquete, Date fecha) {
        if (paqueteDAO != null) {
            Optional<Paquete> opt = paqueteDAO.buscarPorId(idPaquete);
            if (opt.isPresent()) {
                Paquete p = opt.get();
                if (p.getRuta() != null && p.getRuta().getPuntosIntermedios() != null) {
                    var puntos = p.getRuta().getPuntosIntermedios();
                    if (!puntos.isEmpty()) {
                        var ultimoPunto = puntos.get(puntos.size() - 1);
                        ultimoPunto.setHoraLlegada(LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault()));
                        paqueteDAO.guardar(p);
                    }
                }
            }
        }
    }

    @Override
    public void registrarSalidaPaquete(String idPaquete, Date fecha) {
        if (paqueteDAO != null) {
            Optional<Paquete> opt = paqueteDAO.buscarPorId(idPaquete);
            if (opt.isPresent()) {
                Paquete p = opt.get();
                if (p.getRuta() != null && p.getRuta().getPuntosIntermedios() != null) {
                    var puntos = p.getRuta().getPuntosIntermedios();
                    if (!puntos.isEmpty()) {
                        var ultimoPunto = puntos.get(puntos.size() - 1);
                        ultimoPunto.setHoraSalida(LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault()));
                        paqueteDAO.guardar(p);
                    }
                }
            }
        }
    }
}
