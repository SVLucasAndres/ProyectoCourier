package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.Cliente;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ClienteService;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.EntidadNoEncontradaException;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.OperacionInvalidaException;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.ValidacionException;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.DAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteServiceImpl implements ClienteService {
    private DAO<Cliente> clienteDAO;

    public ClienteServiceImpl(DAO<Cliente> clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @Override
    public void crearCliente(ClienteDTO cliente) {
        if (clienteDAO == null) {
            throw new OperacionInvalidaException("El DAO de cliente no está inicializado.");
        }
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new ValidacionException("El nombre del cliente no puede estar vacío.");
        }
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

    @Override
    public void modificarCliente(ClienteDTO cliente) {
        if (clienteDAO == null) {
            throw new OperacionInvalidaException("El DAO de cliente no está inicializado.");
        }
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new ValidacionException("El nombre del cliente no puede estar vacío.");
        }
        Optional<Cliente> opt = clienteDAO.buscarPorId(cliente.getIdCliente());
        if (opt.isPresent()) {
            Cliente c = opt.get();
            c.setNombre(cliente.getNombre());
            c.setDireccion(cliente.getDireccion());
            c.setTelefono(cliente.getTelefono());
            c.setActive(cliente.isActive());
            clienteDAO.guardar(c);
        } else {
            throw new EntidadNoEncontradaException("No se encontró el cliente con ID: " + cliente.getIdCliente());
        }
    }

    @Override
    public void archivarCliente(ClienteDTO cliente) {
        if (clienteDAO == null) {
            throw new OperacionInvalidaException("El DAO de cliente no está inicializado.");
        }
        Optional<Cliente> opt = clienteDAO.buscarPorId(cliente.getIdCliente());
        if (opt.isPresent()) {
            Cliente c = opt.get();
            c.setActive(false);
            clienteDAO.guardar(c);
        } else {
            throw new EntidadNoEncontradaException("No se encontró el cliente con ID: " + cliente.getIdCliente());
        }
    }

    @Override
    public ClienteDTO buscarClientePorID(String idCliente) {
        if (clienteDAO == null) {
            throw new OperacionInvalidaException("El DAO de cliente no está inicializado.");
        }
        Optional<Cliente> opt = clienteDAO.buscarPorId(idCliente);
        if (opt.isPresent()) {
            Cliente c = opt.get();
            return new ClienteDTO(c.getIdCliente(), c.getNombre(), c.getDireccion(), c.getTelefono(), c.isActive());
        } else {
            throw new EntidadNoEncontradaException("No se encontró el cliente con ID: " + idCliente);
        }
    }

    @Override
    public ClienteDTO buscarClientePorNombre(String nombre) {
        if (clienteDAO == null) {
            throw new OperacionInvalidaException("El DAO de cliente no está inicializado.");
        }
        List<Cliente> todos = clienteDAO.obtenerTodos();
        for (Cliente c : todos) {
            if (c.getNombre() != null && c.getNombre().equalsIgnoreCase(nombre)) {
                return new ClienteDTO(c.getIdCliente(), c.getNombre(), c.getDireccion(), c.getTelefono(), c.isActive());
            }
        }
        throw new EntidadNoEncontradaException("No se encontró el cliente con nombre: " + nombre);
    }

    @Override
    public List<ClienteDTO> mostrarListaClientes() {
        if (clienteDAO == null) {
            throw new OperacionInvalidaException("El DAO de cliente no está inicializado.");
        }
        List<ClienteDTO> lista = new ArrayList<>();
        List<Cliente> todos = clienteDAO.obtenerTodos();
        for (Cliente c : todos) {
            lista.add(new ClienteDTO(c.getIdCliente(), c.getNombre(), c.getDireccion(), c.getTelefono(), c.isActive()));
        }
        return lista;
    }
}
