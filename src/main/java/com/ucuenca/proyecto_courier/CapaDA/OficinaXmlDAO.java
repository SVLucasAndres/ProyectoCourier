package com.ucuenca.proyecto_courier.CapaDA;

import com.ucuenca.proyecto_courier.CapaDominio.Oficina;

import java.util.List;

public class OficinaXmlDAO extends DAOXML<Oficina> {

    public OficinaXmlDAO(String rutaArchivo) {
        super(rutaArchivo);
    }

    @Override
    protected String obtenerId(Oficina entidad) {
        return entidad.getIdOficina();
    }

    @Override
    public void eliminar(String id) {
        List<Oficina> lista = obtenerTodos();

        // Buscamos la oficina y en vez de borrarla, cambiamos su estado
        for (Oficina ofi : lista) {
            if (ofi.getIdOficina().equals(id)) {
                ofi.setActive(false);
                break;
            }
        }

        escribirArchivo(lista); // Guardamos la lista manteniendo la sucursal inactiva
    }
}
