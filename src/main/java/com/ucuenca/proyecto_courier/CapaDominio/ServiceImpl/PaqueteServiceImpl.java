package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.*;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.OficinaDTO;
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
    private DAO<Oficina> oficinaDAO;

    public PaqueteServiceImpl(DAO<Paquete> paqueteDAO, DAO<Oficina> oficinaDao) {
        this.paqueteDAO = paqueteDAO;
        this.oficinaDAO = oficinaDao;
    }

    @Override
    public void crearPaquete(PaqueteDTO paquete) {
        if (paqueteDAO == null) {
            throw new OperacionInvalidaException("El DAO de paquete no está inicializado.");
        }
        if (paquete == null) {
            throw new ValidacionException("El paquete a crear no puede ser nulo.");
        }

        RutaSeguimiento ruta = new RutaSeguimiento();
        if (paquete.getListaOficinas() != null) {
            for (OficinaDTO o : paquete.getListaOficinas()) {

                Optional<Oficina> oficinaBd = (oficinaDAO != null) ? oficinaDAO.buscarPorId(o.getIdOficina()) : Optional.empty();

                Oficina oficinaDominio;
                // Trae nombre, dirección, teléfono, etc.
                oficinaDominio = oficinaBd.orElseGet(() -> new Oficina(o.getIdOficina(), "Oficina " + o.getIdOficina(), "", "", true));

                PuntoIntermedio punto = new PuntoIntermedio(null, null, oficinaDominio);
                ruta.agregarPaso(punto);
            }
        }

        Paquete nuevoPaquete = null;

        if (paquete instanceof CajaDTO) {
            CajaDTO cDTO = (CajaDTO) paquete;
            Caja cajaEntity = new Caja(
                    cDTO.getIdPaquete(),
                    cDTO.getPeso(),
                    cDTO.getValorContenido(),
                    cDTO.isTieneSeguro(),
                    cDTO.getPorcentajeSeguro(),
                    ruta, //Pasamos la ruta
                    cDTO.getAlto(),
                    cDTO.getAncho(),
                    cDTO.getLargo()
            );
            nuevoPaquete = cajaEntity;

        } else if (paquete instanceof SobreDTO) {
            SobreDTO sDTO = (SobreDTO) paquete;

            Sobre sobreEntity = new Sobre(
                    sDTO.getIdPaquete(),
                    sDTO.getPeso(),
                    sDTO.getValorContenido(),
                    sDTO.isTieneSeguro(),
                    sDTO.getPorcentajeSeguro(),
                    ruta, //Pasamos la ruta
                    sDTO.getTamano()
            );

            if (sDTO.getTamano() != null) {
                sobreEntity.setTamano(sDTO.getTamano());
            }

            nuevoPaquete = sobreEntity;
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

                // Mapeamos los puntos intermedios a una lista limpia de Strings
                dto.setPuntosRuta(generarPuntosRutaTexto(p));
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

                //Asignamos la lista formateada de strings al DTO
                dto.setPuntosRuta(generarPuntosRutaTexto(p));

                lista.add(dto);
            }
        }
        return lista;
    }

    private List<String> generarPuntosRutaTexto(Paquete p) {
        List<String> paradasTexto = new ArrayList<>();
        if (p.getRuta() != null && p.getRuta().getPuntosIntermedios() != null) {
            for (PuntoIntermedio punto : p.getRuta().getPuntosIntermedios()) {
                if (punto.getOficina() != null) {
                    String nombreOfi = punto.getOficina().getNombre();
                    String detallePaso = nombreOfi;

                    if (punto.getHoraLlegada() != null && punto.getHoraSalida() == null) {
                        detallePaso += " (En tránsito - Llegó: " + punto.getHoraLlegada().toLocalTime().toString().substring(0, 5) + ")";
                    } else if (punto.getHoraSalida() != null) {
                        detallePaso += " (Despachado - Salió: " + punto.getHoraSalida().toLocalTime().toString().substring(0, 5) + ")";
                    } else {
                        detallePaso += " (En Espera)";
                    }
                    paradasTexto.add(detallePaso);
                }
            }
        }
        return paradasTexto;
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