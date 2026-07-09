package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.Cliente;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteEnviosDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.ConfiguracionDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Envio;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.AutenticacionExcepcion;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ClienteService;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.EntidadNoEncontradaException;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.OperacionInvalidaException;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.ValidacionException;
import com.ucuenca.proyecto_courier.CapaDA.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteServiceImpl implements ClienteService {
    private DAO<Cliente> clienteDAO;

    public ClienteServiceImpl(DAO<Cliente> clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @Override
    public void crearCliente(ClienteDTO cliente, String contrasena) {
        if (clienteDAO == null) {
            throw new OperacionInvalidaException("El DAO de cliente no está inicializado.");
        }
        validarDatosCliente(cliente);
        
        if (cliente.getIdCliente() != null && clienteDAO.buscarPorId(cliente.getIdCliente()).isPresent()) {
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
        nuevoCliente.setContrasena(contrasena);
        clienteDAO.guardar(nuevoCliente);
    }

    @Override
    public void modificarCliente(ClienteDTO cliente) {
        if (clienteDAO == null) {
            throw new OperacionInvalidaException("El DAO de cliente no está inicializado.");
        }
        validarDatosCliente(cliente);
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
    public ClienteEnviosDTO obtenerListadoPaquetesPorIdCliente(String id, ConfiguracionDTO configuracion) {

        Cliente clienteEncontrado;
        if (clienteDAO.buscarPorId(id).isEmpty())
            return null;
        clienteEncontrado = clienteDAO.buscarPorId(id).get();
        List<EnvioDTO> enviosPorCliente = new ArrayList<>();
        for(Envio e:clienteEncontrado.getListaEnvios()){
            EnvioDTO envio = new EnvioDTO();
            envio.setIdEnvio(e.getIdEnvio());
            envio.setIdDestinatario(e.getIdDestinatario());
            envio.setIdRemitente(e.getIdRemitente());
            envio.setRapidez(e.getRapidez());
            envio.setMetodoPago(e.getMetodoPago());
            envio.setCostoTotal(e.getCostoTotal());
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

    private void validarDatosCliente(ClienteDTO cliente) {
        if (cliente.getNombre() != null && !cliente.getNombre().trim().isEmpty()) {
            if (!cliente.getNombre().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
                throw new ValidacionException("El nombre del cliente solo debe contener letras.");
            }
        }
        
        if (cliente.getTelefono() != null && !cliente.getTelefono().trim().isEmpty()) {
            if (!cliente.getTelefono().matches("^\\d{10}$")) {
                throw new ValidacionException("El número de teléfono debe tener exactamente 10 dígitos numéricos.");
            }
        }
        
        if (cliente.getIdCliente() != null && !cliente.getIdCliente().trim().isEmpty()) {
            if (!cliente.getIdCliente().matches("^\\d{10}$")) {
                throw new ValidacionException("La cédula debe tener exactamente 10 dígitos numéricos.");
            }
            if (!esCedulaValida(cliente.getIdCliente())) {
                throw new ValidacionException("La cédula ingresada es incorrecta o falsa.");
            }
        }
    }

    private boolean esCedulaValida(String cedula) {
        try {
            int provincia = Integer.parseInt(cedula.substring(0, 2));
            if (provincia < 1 || (provincia > 24 && provincia != 30)) {
                return false;
            }
            int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
            if (tercerDigito >= 6) {
                return false;
            }
            
            int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
            int suma = 0;
            for (int i = 0; i < 9; i++) {
                int valor = Integer.parseInt(cedula.substring(i, i + 1)) * coeficientes[i];
                suma += (valor >= 10) ? valor - 9 : valor;
            }
            
            int digitoVerificador = Integer.parseInt(cedula.substring(9, 10));
            int decenas = (suma / 10 + 1) * 10;
            int calculado = decenas - suma;
            if (calculado == 10) calculado = 0;
            
            return calculado == digitoVerificador;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    @Override
    public ClienteDTO validarLogin(ClienteDTO cliente, String contrasena){
        Optional<Cliente> clienteAuth = clienteDAO.buscarPorId(cliente.getIdCliente());
        if(clienteAuth.isPresent()){
            if(clienteAuth.get().getContrasena().equals(contrasena)){
                return cliente;
            }else {
                throw new AutenticacionExcepcion("Credenciales incorrectas");
            }
        }else{
            throw new EntidadNoEncontradaException("Este usuario no existe en el sistema.");

        }

    }
}
