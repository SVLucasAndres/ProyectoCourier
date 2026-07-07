package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.*;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.CajaDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.SobreDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.EnvioService;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.EntidadNoEncontradaException;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.OperacionInvalidaException;
import com.ucuenca.proyecto_courier.CapaDA.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnvioServiceImpl implements EnvioService {
    private DAO<Envio> envioDAO;
    private DAO<Cliente> clienteDAO;
    private DAO<Configuracion> configDAO;
    private DAO<Paquete> paqueteDAO;

    public EnvioServiceImpl(DAO<Envio> envioDAO, DAO<Cliente> clienteDAO, DAO<Configuracion> configDAO) {
        this.envioDAO = envioDAO;
        this.clienteDAO = clienteDAO;
        this.configDAO = configDAO;
        this.paqueteDAO = paqueteDAO;
    }

    private List<Rango> obtenerRangos() {
        if (configDAO != null) {
            Optional<Configuracion> opt = configDAO.buscarPorId("GLOBAL");
            if (opt.isPresent() && opt.get().getRangos() != null) {
                return opt.get().getRangos();
            }
        }
        return new ArrayList<>();
    }

    private double obtenerIVA() {
        if (configDAO != null) {
            Optional<Configuracion> opt = configDAO.buscarPorId("GLOBAL");
            if (opt.isPresent()) {
                return opt.get().getImpuestoIVA();
            }
        }
        return 0.0;
    }

    @Override
    public void realizarEnvio(EnvioDTO dto) {
        if (envioDAO == null) {
            throw new OperacionInvalidaException("El DAO de envío no está inicializado.");
        }

        Envio envioDominio = new Envio();
        envioDominio.setIdEnvio(dto.getIdEnvio());
        envioDominio.setIdRemitente(dto.getIdRemitente());
        envioDominio.setIdDestinatario(dto.getIdDestinatario());
        envioDominio.setRapidez(dto.getRapidez());
        envioDominio.setMetodoPago(dto.getMetodoPago());

        List<String> idsPaquetes = new ArrayList<>();
        if (dto.getListaPaquetes() != null) {
            for (PaqueteDTO pDto : dto.getListaPaquetes()) {
                idsPaquetes.add(pDto.getIdPaquete());
            }
        }
        envioDominio.setListaIdPaquetes(idsPaquetes);

        envioDAO.guardar(envioDominio);
    }

    @Override
    public EnvioDTO buscarEnvioPorID(String idEnvio) {
        if (envioDAO == null) {
            throw new OperacionInvalidaException("El DAO de envío no está inicializado.");
        }
        Optional<Envio> opt = envioDAO.buscarPorId(idEnvio);
        if (opt.isPresent()) {
            Envio e = opt.get();

            List<Paquete> paquetesDominio = new ArrayList<>();
            List<PaqueteDTO> paquetesDtos = new ArrayList<>();

            if (e.getListaIdPaquetes() != null && paqueteDAO != null) {
                for (String idPack : e.getListaIdPaquetes()) {
                    Optional<Paquete> paqueteBd = paqueteDAO.buscarPorId(idPack);
                    if (paqueteBd.isPresent()) {
                        Paquete p = paqueteBd.get();
                        paquetesDominio.add(p);
                        paquetesDtos.add(convertirPaqueteADto(p));
                    }
                }
            }
            e.setListaPaquetes(paquetesDominio);

            EnvioDTO dto = new EnvioDTO();
            dto.setIdEnvio(e.getIdEnvio());
            dto.setIdRemitente(e.getIdRemitente());
            dto.setIdDestinatario(e.getIdDestinatario());
            dto.setRapidez(e.getRapidez());
            dto.setMetodoPago(e.getMetodoPago());
            dto.setListaPaquetes(paquetesDtos);
            dto.setCostoTotal(e.calcularCostoTotal(obtenerRangos(), obtenerIVA()));

            return dto;
        } else {
            throw new EntidadNoEncontradaException("No se encontró el envío con ID: " + idEnvio);
        }
    }

    @Override
    public double obtenerCostoTotalEnvio(String idEnvio) {
        EnvioDTO dto = buscarEnvioPorID(idEnvio);
        return dto.getCostoTotal();
    }

    @Override
    public List<EnvioDTO> mostrarListaEnvios() {
        if (envioDAO == null) {
            return new ArrayList<>();
        }

        List<Envio> enviosDePersistencia = envioDAO.obtenerTodos();
        List<EnvioDTO> listaDtos = new ArrayList<>();

        for (Envio e : enviosDePersistencia) {
            List<Paquete> paquetesDominio = new ArrayList<>();
            List<PaqueteDTO> paquetesDtos = new ArrayList<>();

            if (e.getListaIdPaquetes() != null && paqueteDAO != null) {
                for (String idPack : e.getListaIdPaquetes()) {
                    Optional<Paquete> paqueteBd = paqueteDAO.buscarPorId(idPack);
                    if (paqueteBd.isPresent()) {
                        Paquete p = paqueteBd.get();
                        paquetesDominio.add(p);
                        paquetesDtos.add(convertirPaqueteADto(p));
                    }
                }
            }
            e.setListaPaquetes(paquetesDominio);

            EnvioDTO dto = new EnvioDTO();
            dto.setIdEnvio(e.getIdEnvio());
            dto.setIdRemitente(e.getIdRemitente());
            dto.setIdDestinatario(e.getIdDestinatario());
            dto.setRapidez(e.getRapidez());
            dto.setMetodoPago(e.getMetodoPago());
            dto.setListaPaquetes(paquetesDtos);
            dto.setCostoTotal(e.calcularCostoTotal(obtenerRangos(), obtenerIVA()));

            listaDtos.add(dto);
        }

        return listaDtos;
    }
    private PaqueteDTO convertirPaqueteADto(Paquete p) {
        if (p instanceof Sobre){
            PaqueteDTO pDto = new SobreDTO();
            pDto.setIdPaquete(p.getIdPaquete());
            pDto.setPeso(p.getPeso());
            pDto.setTieneSeguro(p.isTieneSeguro()); // O getTieneSeguro() según tu entidad
            return pDto;
        }else{
            PaqueteDTO pDto = new CajaDTO();
            pDto.setIdPaquete(p.getIdPaquete());
            pDto.setPeso(p.getPeso());
            pDto.setTieneSeguro(p.isTieneSeguro()); // O getTieneSeguro() según tu entidad
            return pDto;
        }

    }
}