package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.Cliente;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ClienteService;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.ClienteDAO;
import java.util.ArrayList;

public class ClienteServiceImpl implements ClienteService {
    private ClienteDAO clienteDAO;

    public ClienteServiceImpl(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @Override
    public void crearCliente(ClienteDTO cliente) {
        if (clienteDAO != null) {
            Cliente nuevoCliente = new Cliente(
                cliente.getIdCliente(), 
                cliente.getNombre(), 
                cliente.getDireccion(), 
                cliente.getTelefono(), 
                new ArrayList<>(), 
                cliente.isActive()
            );
            clienteDAO.crear(nuevoCliente);
        }
    }

    @Override
    public void modificarCliente(ClienteDTO cliente) {
        if (clienteDAO != null) {
            Cliente c = clienteDAO.leer(cliente.getIdCliente());
            if (c != null) {
                c.setNombre(cliente.getNombre());
                c.setDireccion(cliente.getDireccion());
                c.setTelefono(cliente.getTelefono());
                c.setActive(cliente.isActive());
                clienteDAO.actualizar(c);
            }
        }
    }

    @Override
    public void archivarCliente(ClienteDTO cliente) {
        if (clienteDAO != null) {
            Cliente c = clienteDAO.leer(cliente.getIdCliente());
            if (c != null) {
                c.setActive(false);
                clienteDAO.actualizar(c);
            }
        }
    }

    @Override
    public ClienteDTO mostrarCliente(String idCliente) {
        if (clienteDAO != null) {
            Cliente c = clienteDAO.leer(idCliente);
            if (c != null) {
                return new ClienteDTO(c.getIdCliente(), c.getNombre(), c.getDireccion(), c.getTelefono(), c.isActive());
            }
        }
        return null;
    }
}
