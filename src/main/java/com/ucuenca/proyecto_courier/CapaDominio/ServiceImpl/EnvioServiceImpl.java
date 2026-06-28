package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.Envio;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.EnvioService;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.DAO;
import com.ucuenca.proyecto_courier.CapaDominio.Cliente;
import java.util.ArrayList;
import java.util.Optional;

public class EnvioServiceImpl implements EnvioService {
    private DAO<Envio> envioDAO;
    private DAO<Cliente> clienteDAO;

    public EnvioServiceImpl(DAO<Envio> envioDAO, DAO<Cliente> clienteDAO) {
        this.envioDAO = envioDAO;
        this.clienteDAO = clienteDAO;
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
    public EnvioDTO mostrarEnvio(String idEnvio) {
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
                dto.setCostoTotal(e.calcularCostoTotal(null));
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
                return opt.get().calcularCostoTotal(null);
            }
        }
        return 0.0;
    }
}
