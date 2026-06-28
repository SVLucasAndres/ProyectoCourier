package com.ucuenca.proyecto_courier.CapaDominio.interfaces;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.OficinaDTO;
import java.util.List;

public interface OficinaService {
    void crearOficina(OficinaDTO oficina);
    void modificarOficina(OficinaDTO oficina);
    void archivarOficina(OficinaDTO oficina);
    OficinaDTO buscarOficinaPorID(String id);
    OficinaDTO buscarOficinaPorNombre(String nombre);
    List<OficinaDTO> mostrarListaOficinas();
}
