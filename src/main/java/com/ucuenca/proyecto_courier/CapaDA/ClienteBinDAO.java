package com.ucuenca.proyecto_courier.CapaDA;

import com.ucuenca.proyecto_courier.CapaDominio.Cliente;

public class ClienteBinDAO extends DAOBIN<Cliente> {

    public ClienteBinDAO(String rutaArchivo) {
        super(rutaArchivo);
    }

    @Override
    protected String obtenerId(Cliente entidad) {
        return entidad.getIdCliente();
    }

    public java.util.List<Cliente> buscarPorNombre(String nombre) {
        java.util.List<Cliente> todos = obtenerTodos();
        java.util.List<Cliente> resultado = new java.util.ArrayList<>();
        for (Cliente c : todos) {
            if (c.getNombre() != null && c.getNombre().equalsIgnoreCase(nombre)) {
                resultado.add(c);
            }
        }
        return resultado;
    }
}
