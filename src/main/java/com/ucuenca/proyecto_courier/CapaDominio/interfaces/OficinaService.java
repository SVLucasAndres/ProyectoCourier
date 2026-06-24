package com.ucuenca.proyecto_courier.CapaDominio.interfaces;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.OficinaDTO;

public interface OficinaService {
    void crearOficina(OficinaDTO oficina);
    void modificarOficina(OficinaDTO oficina);
    void archivarOficina(OficinaDTO oficina);
    OficinaDTO mostrarOficina();
}
