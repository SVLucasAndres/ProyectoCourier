package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.Envio;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.EnvioService;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.DAO;
import com.ucuenca.proyecto_courier.CapaDominio.Cliente;
import com.ucuenca.proyecto_courier.CapaDominio.Configuracion;
import com.ucuenca.proyecto_courier.CapaDominio.Rango;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnvioServiceImpl implements EnvioService {
    private DAO<Envio> envioDAO;
    private DAO<Cliente> clienteDAO;
    private DAO<Configuracion> configDAO;

    public EnvioServiceImpl(DAO<Envio> envioDAO, DAO<Cliente> clienteDAO, DAO<Configuracion> configDAO) {
        this.envioDAO = envioDAO;
        this.clienteDAO = clienteDAO;
        this.configDAO = configDAO;
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

    @Override
    public void realizarEnvio(EnvioDTO envio) {
        if (envioDAO != null && clienteDAO != null) {
            Optional<Cliente> optRemitente = clienteDAO.buscarPorId(envio.getIdRemitente());
            Optional<Cliente> optDestinatario = clienteDAO.buscarPorId(envio.getIdDestinatario());
            
            Cliente remitente = optRemitente.orElse(null);
            Cliente destinatario = optDestinatario.orElse(null);
            
            Envio nuevoEnvio = new Envio(
                envio.getIdEnvio(), 
                remitente, 
                destinatario, 
                new ArrayList<>(), 
                envio.getRapidez(), 
                envio.getMetodoPago()
            );
            envioDAO.guardar(nuevoEnvio);
        }
    }

    @Override
    public EnvioDTO buscarEnvioPorID(String idEnvio) {
        if (envioDAO != null) {
            Optional<Envio> opt = envioDAO.buscarPorId(idEnvio);
            if (opt.isPresent()) {
                Envio e = opt.get();
                EnvioDTO dto = new EnvioDTO();
                dto.setIdEnvio(e.getIdEnvio());
                if (e.getRemitente() != null) dto.setIdRemitente(e.getRemitente().getIdCliente());
                if (e.getDestinatario() != null) dto.setIdDestinatario(e.getDestinatario().getIdCliente());
                dto.setRapidez(e.getRapidez());
                dto.setMetodoPago(e.getMetodoPago());
                dto.setCostoTotal(e.calcularCostoTotal(obtenerRangos()));
                return dto;
            }
        }
        return null;
    }

    @Override
    public double obtenerCostoTotalEnvio(String idEnvio) {
        if (envioDAO != null) {
            Optional<Envio> opt = envioDAO.buscarPorId(idEnvio);
            if (opt.isPresent()) {
                return opt.get().calcularCostoTotal(obtenerRangos());
            }
        }
        return 0.0;
    }

    @Override
    public List<EnvioDTO> mostrarListaEnvios() {
        List<EnvioDTO> lista = new ArrayList<>();
        if (envioDAO != null) {
            List<Envio> todos = envioDAO.obtenerTodos();
            for (Envio e : todos) {
                EnvioDTO dto = new EnvioDTO();
                dto.setIdEnvio(e.getIdEnvio());
                if (e.getRemitente() != null) dto.setIdRemitente(e.getRemitente().getIdCliente());
                if (e.getDestinatario() != null) dto.setIdDestinatario(e.getDestinatario().getIdCliente());
                dto.setRapidez(e.getRapidez());
                dto.setMetodoPago(e.getMetodoPago());
                dto.setCostoTotal(e.calcularCostoTotal(obtenerRangos()));
                lista.add(dto);
            }
        }
        return lista;
    }
}
