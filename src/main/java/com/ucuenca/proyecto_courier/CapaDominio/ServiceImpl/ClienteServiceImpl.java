package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.Cliente;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteEnviosDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.RangoDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Envio;
import com.ucuenca.proyecto_courier.CapaDominio.Rango;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ClienteService;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.EntidadNoEncontradaException;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.OperacionInvalidaException;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.ValidacionException;
import com.ucuenca.proyecto_courier.CapaDA.DAO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ConfiguracionService;

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
        }else if(cliente.getTelefono() == null || cliente.getTelefono().trim().isEmpty()){
            throw new ValidacionException("El campo de teléfono no puede estar vacio");
        }else if(cliente.getDireccion() == null || cliente.getDireccion().trim().isEmpty()){
            throw new ValidacionException("El campo de dirección no puede estar vacio");
        }else if (cliente.getIdCliente()==null || cliente.getIdCliente().trim().isEmpty()){
            throw new ValidacionException("El campo de cédula no puede estar vacio");
        }else if (clienteDAO.buscarPorId(cliente.getIdCliente()).isPresent()) {
            throw new ValidacionException("El cliente ya existe en el sistema");
        }
        Cliente nuevoCliente = new Cliente(
            cliente.getIdCliente(), 
            cliente.getNombre(), 
            cliente.getDireccion(), 
            cliente.getTelefono(), 
            new ArrayList<>(), 
            true
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
            c.setIsActive(cliente.isActive());
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
            c.setIsActive(false);
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
            return new ClienteDTO(c.getIdCliente(), c.getNombre(), c.getDireccion(), c.getTelefono(), c.isIsActive());
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
                return new ClienteDTO(c.getIdCliente(), c.getNombre(), c.getDireccion(), c.getTelefono(), c.isIsActive());
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
            if (c.isIsActive()) {
                lista.add(new ClienteDTO(c.getIdCliente(), c.getNombre(), c.getDireccion(), c.getTelefono(), c.isIsActive()));

            }
        }
        return lista;
    }

    @Override
    public ClienteEnviosDTO obtenerListadoPaquetesPorIdCliente(String id, List<Rango> rangos) {

        Cliente clienteEncontrado;
        if (clienteDAO.buscarPorId(id).isEmpty())
            return null;
        clienteEncontrado = clienteDAO.buscarPorId(id).get();
        List<EnvioDTO> enviosPorCliente = new ArrayList<>();
        for(Envio e:clienteEncontrado.getListaEnvios()){
            EnvioDTO envio = new EnvioDTO();
            envio.setIdEnvio(e.getIdEnvio());
            envio.setIdDestinatario(e.getDestinatario());
            envio.setIdRemitente(e.getRemitente());
            envio.setRapidez(e.getRapidez());
            envio.setMetodoPago(e.getMetodoPago());
            envio.setCostoTotal(e.calcularCostoTotal(rangos));
            enviosPorCliente.add(envio);
        }
        return new ClienteEnviosDTO(
                clienteEncontrado.getIdCliente(),
                clienteEncontrado.getNombre(),
                clienteEncontrado.getDireccion(),
                clienteEncontrado.getTelefono(),
                clienteEncontrado.isIsActive(),
                enviosPorCliente
        );
    }
}
