package com.ucuenca.proyecto_courier.CapaDA;

import com.ucuenca.proyecto_courier.CapaDominio.Oficina;

public class OficinaBinDAO extends DAOBIN<Oficina> {

    public OficinaBinDAO(String rutaArchivo) {
        super(rutaArchivo);
    }

    @Override
    protected String obtenerId(Oficina entidad) {
        return entidad.getIdOficina();
    }

    public java.util.Optional<Oficina> buscarPorNombre(String nombre) {
        java.util.List<Oficina> todas = obtenerTodos();
        for (Oficina o : todas) {
            if (o.getNombre() != null && o.getNombre().equalsIgnoreCase(nombre)) {
                return java.util.Optional.of(o);
            }
        }
        return java.util.Optional.empty();
    }
}
