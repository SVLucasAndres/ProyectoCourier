package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.*;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.*;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.PaqueteService;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.EntidadNoEncontradaException;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.OperacionInvalidaException;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.ValidacionException;
import com.ucuenca.proyecto_courier.CapaDA.DAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaqueteServiceImpl implements PaqueteService {
    private DAO<Paquete> paqueteDAO;
    private DAO<Envio> envioDAO;
    private DAO<Oficina> oficinaDAO;

    public PaqueteServiceImpl(DAO<Paquete> paqueteDAO, DAO<Envio> envioDAO, DAO<Oficina> oficinaDao) {
        this.paqueteDAO = paqueteDAO;
        this.oficinaDAO = oficinaDao;
        this.envioDAO = envioDAO;
    }

    @Override
    public void crearPaquete(PaqueteDTO paquete, ConfiguracionDTO configuracion) {
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
                Oficina oficinaDominio = oficinaBd.orElseGet(() -> new Oficina(o.getIdOficina(), "Oficina " + o.getIdOficina(), "", "", true));
                PuntoIntermedio punto = new PuntoIntermedio(null, null, oficinaDominio);
                ruta.agregarPaso(punto);
            }
        }

        Paquete nuevoPaquete = null;

        if (paquete instanceof CajaDTO) {
            CajaDTO cDTO = (CajaDTO) paquete;
            nuevoPaquete = new Caja(
                    cDTO.getIdPaquete(),
                    cDTO.getPeso(),
                    cDTO.getValorContenido(),
                    cDTO.isTieneSeguro(),
                    configuracion.getPorcentajeSeguro(),
                    ruta,
                    cDTO.getAlto(),
                    cDTO.getAncho(),
                    cDTO.getLargo()
            );

            for(RangoDTO r:configuracion.getRangos()){
                double minimo = r.getPesoMinimo();
                double maximo = r.getPesoMaximo();
                if(nuevoPaquete.getPeso() <= maximo && nuevoPaquete.getPeso() >=minimo){
                    nuevoPaquete.setValorContenido(nuevoPaquete.getValorContenido() + (nuevoPaquete.getPeso() * r.getCostoPorKilogramo()));
                }
            }
        } else if (paquete instanceof SobreDTO) {
            SobreDTO sDTO = (SobreDTO) paquete;
            Sobre sobreEntity = new Sobre(
                    sDTO.getIdPaquete(),
                    sDTO.getPeso(),
                    sDTO.getValorContenido(),
                    sDTO.isTieneSeguro(),
                    configuracion.getPorcentajeSeguro(),
                    ruta,
                    sDTO.getTamano()
            );
            if (sDTO.getTamano() != null) {
                sobreEntity.setTamano(sDTO.getTamano());
            }
            nuevoPaquete = sobreEntity;
        }

        if (nuevoPaquete != null) {
            nuevoPaquete.setPorcentajeSeguro(configuracion.getPorcentajeSeguro());
            boolean valorSeguro = nuevoPaquete.isTieneSeguro();
            nuevoPaquete.setTieneSeguro(false);
            nuevoPaquete.setTieneSeguro(valorSeguro);
            double costoSeguro = nuevoPaquete.calcularCostoSeguro();
            nuevoPaquete.setValorContenido(nuevoPaquete.getValorContenido() + costoSeguro);
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
            return mapearAModeloDTO(opt.get());
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
        for (Paquete p : paqueteDAO.obtenerTodos()) {
            PaqueteDTO dto = mapearAModeloDTO(p);
            if (dto != null) {
                lista.add(dto);
            }
        }
        return lista;
    }

    @Override
    public List<PaqueteDTO> mostrarPaquetesSinEnvio() {
        if (paqueteDAO == null || envioDAO == null) {
            throw new OperacionInvalidaException("Los DAO no están inicializados.");
        }

        List<Envio> envios = envioDAO.obtenerTodos();
        java.util.Set<String> idsOcupados = envios.stream()
                .filter(e -> e.getListaIdPaquetes() != null)
                .flatMap(e -> e.getListaIdPaquetes().stream())
                .collect(java.util.stream.Collectors.toSet());

        List<PaqueteDTO> paquetesLibres = new ArrayList<>();
        for (Paquete p : paqueteDAO.obtenerTodos()) {
            if (!idsOcupados.contains(p.getIdPaquete())) {
                PaqueteDTO dto = mapearAModeloDTO(p);
                if (dto != null) {
                    paquetesLibres.add(dto);
                }
            }
        }
        return paquetesLibres;
    }

    @Override
    public void registrarMovimientoPaquete(String idPaquete, String nombreOficina, LocalDateTime fechaHora, boolean esLlegada, String textoFormateado) {
        Optional<Paquete> paqueteBuscado = paqueteDAO.buscarPorId(idPaquete);

        if (paqueteBuscado.isPresent()) {
            Paquete paqueteActual = paqueteBuscado.get();
            List<PuntoIntermedio> puntos = paqueteActual.getRuta().getPuntosIntermedios();

            // --- VALIDACIÓN DE SECUENCIA ---
            for (int i = 0; i < puntos.size(); i++) {
                PuntoIntermedio p = puntos.get(i);

                if (p.getOficina().getNombre().contains(nombreOficina)) {
                    // Si es el primer punto, siempre se permite
                    if (i > 0) {
                        PuntoIntermedio anterior = puntos.get(i - 1);
                        if (anterior.getHoraSalida() == null) {
                            throw new OperacionInvalidaException("No se puede registrar movimiento en " + nombreOficina +
                                    ". El punto anterior (" + anterior.getOficina().getNombre() + ") aún no ha sido despachado.");
                        }
                    }

                    // Procesar el movimiento encontrado
                    String fechaStr = fechaHora.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    if (esLlegada) p.setLlegadaTexto(fechaStr);
                    else p.setSalidaTexto(fechaStr);

                    paqueteDAO.guardar(paqueteActual);
                    return; // Éxito
                }
            }
        }
    }

    @Override
    public void agregarPuntoRuta(String idPaquete, String nombreOficina) {
        Paquete p = paqueteDAO.buscarPorId(idPaquete).orElseThrow();

        // Buscar la oficina en el sistema (DAO de oficinas)
        Oficina oficina = oficinaDAO.obtenerTodos().stream()
                .filter(o -> o.getNombre().equals(nombreOficina))
                .findFirst()
                .orElseThrow(() -> new EntidadNoEncontradaException("Oficina no encontrada"));

        // Crear el nuevo punto (sin llegada ni salida aún)
        PuntoIntermedio nuevoPunto = new PuntoIntermedio(null, null, oficina);

        // Agregar a la ruta
        p.getRuta().agregarPaso(nuevoPunto);

        // Persistir
        paqueteDAO.guardar(p);
    }

    public List<PaqueteDTO> obtenerPaquetesPorDestinatario(String idDestinatario) {
        if (paqueteDAO == null || envioDAO == null) {
            throw new OperacionInvalidaException("Los DAO no están inicializados.");
        }
        java.util.Set<String> idsPaquetesDelDestinatario = new java.util.HashSet<>();
        for (Envio e : envioDAO.obtenerTodos()) {
            if (e.getIdDestinatario() != null && e.getIdDestinatario().equalsIgnoreCase(idDestinatario.trim())) {
                if (e.getListaIdPaquetes() != null) {
                    idsPaquetesDelDestinatario.addAll(e.getListaIdPaquetes());
                }
            }
        }

        List<PaqueteDTO> resultado = new ArrayList<>();
        for (Paquete p : paqueteDAO.obtenerTodos()) {
            if (idsPaquetesDelDestinatario.contains(p.getIdPaquete())) {
                PaqueteDTO dto = mapearAModeloDTO(p);
                if (dto != null) {
                    resultado.add(dto);
                }
            }
        }
        return resultado;
    }

    @Override
    public List<PaqueteDTO> obtenerPaquetesPorRemitente(String idRemitente) {
        if (paqueteDAO == null || envioDAO == null) {
            throw new OperacionInvalidaException("Los DAO no están inicializados.");
        }

        java.util.Set<String> idsPaquetesDelRemitente = new java.util.HashSet<>();
        for (Envio e : envioDAO.obtenerTodos()) {
            if (e.getIdRemitente() != null && e.getIdRemitente().equalsIgnoreCase(idRemitente.trim())) {
                if (e.getListaIdPaquetes() != null) {
                    idsPaquetesDelRemitente.addAll(e.getListaIdPaquetes());
                }
            }
        }

        List<PaqueteDTO> resultado = new ArrayList<>();
        for (Paquete p : paqueteDAO.obtenerTodos()) {
            if (idsPaquetesDelRemitente.contains(p.getIdPaquete())) {
                PaqueteDTO dto = mapearAModeloDTO(p);
                if (dto != null) {
                    resultado.add(dto);
                }
            }
        }
        return resultado;
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

    private PaqueteDTO mapearAModeloDTO(Paquete p) {
        if (p == null) return null;

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
            dto.setPuntosRuta(generarPuntosRutaTexto(p));
        }

        return dto;
    }

    @Override
    public List<String> obtenerTextosRutaConEstados(String idPaquete) {
        Paquete p = paqueteDAO.buscarPorId(idPaquete).orElseThrow();
        List<String> lista = new ArrayList<>();
        for (var punto : p.getRuta().getPuntosIntermedios()) {
            String estado;
            if (punto.getHoraLlegada() != null && punto.getHoraSalida() != null) {
                estado = "Despachado";
            } else if (punto.getHoraLlegada() != null) {
                estado = "Recibido";
            } else {
                estado = "En espera";
            }
            lista.add(punto.getOficina().getNombre() + " [" + estado + "]");
        }
        return lista;
    }

}