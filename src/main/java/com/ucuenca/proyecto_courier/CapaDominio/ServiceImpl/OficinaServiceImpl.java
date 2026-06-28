package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.Oficina;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.OficinaDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.OficinaService;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.DAO;
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
        if (oficinaDAO != null) {
            Oficina nuevaOficina = new Oficina(
                oficina.getIdOficina(), 
                oficina.getNombre(), 
                oficina.getDireccion(), 
                oficina.getTelefono(), 
                oficina.isActive()
            );
            oficinaDAO.guardar(nuevaOficina);
        }
    }

    @Override
    public void modificarOficina(OficinaDTO oficina) {
        if (oficinaDAO != null) {
            Optional<Oficina> opt = oficinaDAO.buscarPorId(oficina.getIdOficina());
            if (opt.isPresent()) {
                Oficina o = opt.get();
                o.setNombre(oficina.getNombre());
                o.setDireccion(oficina.getDireccion());
                o.setTelefono(oficina.getTelefono());
                o.setActive(oficina.isActive());
                oficinaDAO.guardar(o);
            }
        }
    }

    @Override
    public void archivarOficina(OficinaDTO oficina) {
        if (oficinaDAO != null) {
            Optional<Oficina> opt = oficinaDAO.buscarPorId(oficina.getIdOficina());
            if (opt.isPresent()) {
                Oficina o = opt.get();
                o.setActive(false);
                oficinaDAO.guardar(o);
            }
        }
    }

    @Override
    public OficinaDTO buscarOficinaPorID(String idOficina) {
        if (oficinaDAO != null) {
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
            }
        }
        return null;
    }

    @Override
    public OficinaDTO buscarOficinaPorNombre(String nombre) {
        if (oficinaDAO != null) {
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
        }
        return null;
    }

    @Override
    public List<OficinaDTO> mostrarListaOficinas() {
        List<OficinaDTO> lista = new ArrayList<>();
        if (oficinaDAO != null) {
            List<Oficina> todas = oficinaDAO.obtenerTodos();
            for (Oficina o : todas) {
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
}
