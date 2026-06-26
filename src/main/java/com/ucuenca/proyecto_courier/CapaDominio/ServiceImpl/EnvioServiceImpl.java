package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.Envio;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.EnvioService;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.EnvioDAO;
import com.ucuenca.proyecto_courier.CapaDominio.Cliente;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.ClienteDAO;
import java.util.ArrayList;

public class EnvioServiceImpl implements EnvioService {
    private EnvioDAO envioDAO;
    private ClienteDAO clienteDAO;

    public EnvioServiceImpl(EnvioDAO envioDAO, ClienteDAO clienteDAO) {
        this.envioDAO = envioDAO;
        this.clienteDAO = clienteDAO;
    }

    @Override
    public void realizarEnvio(EnvioDTO envio) {
        if (envioDAO != null && clienteDAO != null) {
            Cliente remitente = clienteDAO.leer(envio.getIdRemitente());
            Cliente destinatario = clienteDAO.leer(envio.getIdDestinatario());
            
            Envio nuevoEnvio = new Envio(
                envio.getIdEnvio(), 
                remitente, 
                destinatario, 
                new ArrayList<>(), 
                envio.getRapidez(), 
                envio.getMetodoPago()
            );
            envioDAO.crear(nuevoEnvio);
        }
    }

    @Override
    public EnvioDTO mostrarEnvio(String idEnvio) {
        if (envioDAO != null) {
            Envio e = envioDAO.leer(idEnvio);
            if (e != null) {
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
            Envio e = envioDAO.leer(idEnvio);
            if (e != null) {
                return e.calcularCostoTotal(null);
            }
        }
        return 0.0;
    }
}
