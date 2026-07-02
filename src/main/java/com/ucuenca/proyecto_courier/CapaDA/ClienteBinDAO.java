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

}
