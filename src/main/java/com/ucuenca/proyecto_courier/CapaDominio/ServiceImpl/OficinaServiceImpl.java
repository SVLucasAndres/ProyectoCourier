package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.Oficina;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.OficinaDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.OficinaService;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.EntidadNoEncontradaException;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.OperacionInvalidaException;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.ValidacionException;
import com.ucuenca.proyecto_courier.CapaDA.DAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OficinaServiceImpl implements OficinaService {
    private DAO<Oficina> oficinaDAO;

    public OficinaServiceImpl(DAO<Oficina> oficinaDAO) {
        this.oficinaDAO = oficinaDAO;
    }

    @Override
    public void crearOficina(OficinaDTO oficina) {
        if (oficinaDAO == null) {
            throw new OperacionInvalidaException("El DAO de oficina no está inicializado.");
        }
        validarDatosOficina(oficina);
        Oficina nuevaOficina = new Oficina(
                oficina.getIdOficina(),
                oficina.getNombre(),
                oficina.getDireccion(),
                oficina.getTelefono(),
                true);
        oficinaDAO.guardar(nuevaOficina);
    }

    @Override
    public void modificarOficina(OficinaDTO oficina) {
        if (oficinaDAO == null) {
            throw new OperacionInvalidaException("El DAO de oficina no está inicializado.");
        }
        validarDatosOficina(oficina);
        Optional<Oficina> opt = oficinaDAO.buscarPorId(oficina.getIdOficina());
        if (opt.isPresent()) {
            Oficina o = opt.get();
            o.setNombre(oficina.getNombre());
            o.setDireccion(oficina.getDireccion());
            o.setTelefono(oficina.getTelefono());
            o.setActive(oficina.isActive());
            oficinaDAO.guardar(o);
        } else {
            throw new EntidadNoEncontradaException("No se encontró la oficina con ID: " + oficina.getIdOficina());
        }
    }

    @Override
    public void archivarOficina(OficinaDTO oficina) {
        if (oficinaDAO == null) {
            throw new OperacionInvalidaException("El DAO de oficina no está inicializado.");
        }
        Optional<Oficina> opt = oficinaDAO.buscarPorId(oficina.getIdOficina());
        if (opt.isPresent()) {
            Oficina o = opt.get();
            o.setActive(false);
            oficinaDAO.guardar(o);
        } else {
            throw new EntidadNoEncontradaException("No se encontró la oficina con ID: " + oficina.getIdOficina());
        }
    }

    @Override
    public OficinaDTO buscarOficinaPorID(String idOficina) {
        if (oficinaDAO == null) {
            throw new OperacionInvalidaException("El DAO de oficina no está inicializado.");
        }
        Optional<Oficina> opt = oficinaDAO.buscarPorId(idOficina);
        if (opt.isPresent()) {
            Oficina o = opt.get();
            OficinaDTO dto = new OficinaDTO();
            dto.setIdOficina(o.getIdOficina());
            dto.setNombre(o.getNombre());
            dto.setDireccion(o.getDireccion());
            dto.setTelefono(o.getTelefono());
            dto.setActive(o.isActive());
            return dto;
        } else {
            throw new EntidadNoEncontradaException("No se encontró la oficina con ID: " + idOficina);
        }
    }

    @Override
    public OficinaDTO buscarOficinaPorNombre(String nombre) {
        if (oficinaDAO == null) {
            throw new OperacionInvalidaException("El DAO de oficina no está inicializado.");
        }
        List<Oficina> todas = oficinaDAO.obtenerTodos();
        for (Oficina o : todas) {
            if (o.getNombre() != null && o.getNombre().equalsIgnoreCase(nombre)) {
                OficinaDTO dto = new OficinaDTO();
                dto.setIdOficina(o.getIdOficina());
                dto.setNombre(o.getNombre());
                dto.setDireccion(o.getDireccion());
                dto.setTelefono(o.getTelefono());
                dto.setActive(o.isActive());
                return dto;
            }
        }
        throw new EntidadNoEncontradaException("No se encontró la oficina con nombre: " + nombre);
    }

    @Override
    public List<OficinaDTO> mostrarListaOficinas() {
        if (oficinaDAO == null) {
            throw new OperacionInvalidaException("El DAO de oficina no está inicializado.");
        }
        List<OficinaDTO> lista = new ArrayList<>();
        List<Oficina> todas = oficinaDAO.obtenerTodos();
        for (Oficina o : todas) {
            if (o.isActive()) {
                OficinaDTO dto = new OficinaDTO();
                dto.setIdOficina(o.getIdOficina());
                dto.setNombre(o.getNombre());
                dto.setDireccion(o.getDireccion());
                dto.setTelefono(o.getTelefono());
                dto.setActive(o.isActive());
                lista.add(dto);
            }
        }
        return lista;
    }

    private void validarDatosOficina(OficinaDTO oficina) {
        if (oficina.getNombre() != null && !oficina.getNombre().trim().isEmpty()) {
            if (!oficina.getNombre().matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+$")) {
                throw new ValidacionException("El nombre de la oficina solo debe contener letras y números.");
            }
        }

        if (oficina.getTelefono() != null && !oficina.getTelefono().trim().isEmpty()) {
            if (!(oficina.getTelefono().matches("^\\d{10}$") || oficina.getTelefono().matches("^\\d{7}$"))) {
                throw new ValidacionException(
                        "El número de teléfono de la oficina debe tener exactamente 10 dígitos numéricos.");
            }
        }
    }
}
