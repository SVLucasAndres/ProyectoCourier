package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.Cliente;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ClienteService;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.DAO;
import java.util.ArrayList;
import java.util.Optional;

public class ClienteServiceImpl implements ClienteService {
    private DAO<Cliente> clienteDAO;

    public ClienteServiceImpl(DAO<Cliente> clienteDAO) {
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
            clienteDAO.guardar(nuevoCliente);
        }
    }

    @Override
    public void modificarCliente(ClienteDTO cliente) {
        if (clienteDAO != null) {
            Optional<Cliente> opt = clienteDAO.buscarPorId(cliente.getIdCliente());
            if (opt.isPresent()) {
                Cliente c = opt.get();
                c.setNombre(cliente.getNombre());
                c.setDireccion(cliente.getDireccion());
                c.setTelefono(cliente.getTelefono());
                c.setActive(cliente.isActive());
                clienteDAO.guardar(c);
            }
        }
    }

    @Override
    public void archivarCliente(ClienteDTO cliente) {
        if (clienteDAO != null) {
            Optional<Cliente> opt = clienteDAO.buscarPorId(cliente.getIdCliente());
            if (opt.isPresent()) {
                Cliente c = opt.get();
                c.setActive(false);
                clienteDAO.guardar(c);
            }
        }
    }

    @Override
    public ClienteDTO mostrarCliente(String idCliente) {
        if (clienteDAO != null) {
            Optional<Cliente> opt = clienteDAO.buscarPorId(idCliente);
            if (opt.isPresent()) {
                Cliente c = opt.get();
                return new ClienteDTO(c.getIdCliente(), c.getNombre(), c.getDireccion(), c.getTelefono(), c.isActive());
            }
        }
        return null;
    }
}
