package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.Paquete;
import com.ucuenca.proyecto_courier.CapaDominio.Caja;
import com.ucuenca.proyecto_courier.CapaDominio.Sobre;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.CajaDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.SobreDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.PaqueteService;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.EntidadNoEncontradaException;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.OperacionInvalidaException;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.ValidacionException;
import com.ucuenca.proyecto_courier.CapaDA.DAO;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PaqueteServiceImpl implements PaqueteService {
    private DAO<Paquete> paqueteDAO;

    public PaqueteServiceImpl(DAO<Paquete> paqueteDAO) {
        this.paqueteDAO = paqueteDAO;
    }

    @Override
    public void crearPaquete(PaqueteDTO paquete) {
        if (paqueteDAO == null) {
            throw new OperacionInvalidaException("El DAO de paquete no está inicializado.");
        }
        if (paquete == null) {
            throw new ValidacionException("El paquete a crear no puede ser nulo.");
        }
        Paquete nuevoPaquete = null;
        if (paquete instanceof CajaDTO) {
            CajaDTO cDTO = (CajaDTO) paquete;
            nuevoPaquete = new Caja(
                cDTO.getIdPaquete(), 
                cDTO.getPeso(), 
                cDTO.getValorContenido(), 
                cDTO.isTieneSeguro(), 
                cDTO.getPorcentajeSeguro(), 
                null, 
                cDTO.getAlto(), 
                cDTO.getAncho(), 
                cDTO.getLargo()
            );
        } else if (paquete instanceof SobreDTO) {
            SobreDTO sDTO = (SobreDTO) paquete;
            nuevoPaquete = new Sobre(
                sDTO.getIdPaquete(), 
                sDTO.getPeso(), 
                sDTO.getValorContenido(), 
                sDTO.isTieneSeguro(), 
                sDTO.getPorcentajeSeguro(), 
                null, 
                sDTO.getTamano()
            );
        }
        if (nuevoPaquete != null) {
            paqueteDAO.guardar(nuevoPaquete);
        } else {
            throw new ValidacionException("Tipo de paquete desconocido.");
        }
    }

    @Override
    public PaqueteDTO buscarPaquetePorID(String idPaquete) {
        if (paqueteDAO == null) {
            throw new OperacionInvalidaException("El DAO de paquete no está inicializado.");
        }
        Optional<Paquete> opt = paqueteDAO.buscarPorId(idPaquete);
        if (opt.isPresent()) {
            Paquete p = opt.get();
            PaqueteDTO dto = null;
            if (p instanceof Caja) {
                Caja c = (Caja) p;
                CajaDTO cajaDTO = new CajaDTO();
                cajaDTO.setAlto(c.getAlto());
                cajaDTO.setAncho(c.getAncho());
                cajaDTO.setLargo(c.getLargo());
                dto = cajaDTO;
            } else if (p instanceof Sobre) {
                Sobre s = (Sobre) p;
                SobreDTO sobreDTO = new SobreDTO();
                sobreDTO.setTamano(s.getTamano());
                dto = sobreDTO;
            }
            
            if (dto != null) {
                dto.setIdPaquete(p.getIdPaquete());
                dto.setPeso(p.getPeso());
                dto.setValorContenido(p.getValorContenido());
                dto.setTieneSeguro(p.isTieneSeguro());
                dto.setPorcentajeSeguro(p.getPorcentajeSeguro());
            }
            return dto;
        } else {
            throw new EntidadNoEncontradaException("No se encontró el paquete con ID: " + idPaquete);
        }
    }

    @Override
    public List<PaqueteDTO> mostrarListaPaquetes() {
        if (paqueteDAO == null) {
            throw new OperacionInvalidaException("El DAO de paquete no está inicializado.");
        }
        List<PaqueteDTO> lista = new ArrayList<>();
        List<Paquete> todos = paqueteDAO.obtenerTodos();
        for (Paquete p : todos) {
            PaqueteDTO dto = null;
            if (p instanceof Caja) {
                Caja c = (Caja) p;
                CajaDTO cajaDTO = new CajaDTO();
                cajaDTO.setAlto(c.getAlto());
                cajaDTO.setAncho(c.getAncho());
                cajaDTO.setLargo(c.getLargo());
                dto = cajaDTO;
            } else if (p instanceof Sobre) {
                Sobre s = (Sobre) p;
                SobreDTO sobreDTO = new SobreDTO();
                sobreDTO.setTamano(s.getTamano());
                dto = sobreDTO;
            }
            
            if (dto != null) {
                dto.setIdPaquete(p.getIdPaquete());
                dto.setPeso(p.getPeso());
                dto.setValorContenido(p.getValorContenido());
                dto.setTieneSeguro(p.isTieneSeguro());
                dto.setPorcentajeSeguro(p.getPorcentajeSeguro());
                lista.add(dto);
            }
        }
        return lista;
    }

    @Override
    public void registrarLlegadaPaquete(String idPaquete, Date fecha) {
        if (paqueteDAO == null) {
            throw new OperacionInvalidaException("El DAO de paquete no está inicializado.");
        }
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
            } else {
                throw new OperacionInvalidaException("El paquete no tiene una ruta válida asignada.");
            }
        } else {
            throw new EntidadNoEncontradaException("No se encontró el paquete con ID: " + idPaquete);
        }
    }

    @Override
    public void registrarSalidaPaquete(String idPaquete, Date fecha) {
        if (paqueteDAO == null) {
            throw new OperacionInvalidaException("El DAO de paquete no está inicializado.");
        }
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
            } else {
                throw new OperacionInvalidaException("El paquete no tiene una ruta válida asignada.");
            }
        } else {
            throw new EntidadNoEncontradaException("No se encontró el paquete con ID: " + idPaquete);
        }
    }
}
