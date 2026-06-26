package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.Oficina;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.OficinaDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.OficinaService;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.OficinaDAO;

public class OficinaServiceImpl implements OficinaService {
    private OficinaDAO oficinaDAO;

    public OficinaServiceImpl(OficinaDAO oficinaDAO) {
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
            oficinaDAO.crear(nuevaOficina);
        }
    }

    @Override
    public void modificarOficina(OficinaDTO oficina) {
        if (oficinaDAO != null) {
            Oficina o = oficinaDAO.leer(oficina.getIdOficina());
            if (o != null) {
                o.setNombre(oficina.getNombre());
                o.setDireccion(oficina.getDireccion());
                o.setTelefono(oficina.getTelefono());
                o.setActive(oficina.isActive());
                oficinaDAO.actualizar(o);
            }
        }
    }

    @Override
    public void archivarOficina(OficinaDTO oficina) {
        if (oficinaDAO != null) {
            Oficina o = oficinaDAO.leer(oficina.getIdOficina());
            if (o != null) {
                o.setActive(false);
                oficinaDAO.actualizar(o);
            }
        }
    }

    @Override
    public OficinaDTO mostrarOficina(String idOficina) {
        if (oficinaDAO != null) {
            Oficina o = oficinaDAO.leer(idOficina);
            if (o != null) {
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
}
