package com.ucuenca.proyecto_courier.CapaDA.xml;

import com.ucuenca.proyecto_courier.CapaDominio.Cliente;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.DAOXML;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class ClienteXmlDAO extends DAOXML<Cliente> {
    public ClienteXmlDAO() {
        this.rutaArchivo = "clientes.xml";
    }

    @Override
    public void guardar(Cliente entidad) {
    }

    @Override
    public List<Cliente> obtenerTodos() {
        return new ArrayList<>();
    }

    @Override
    public Optional<Cliente> buscarPorId(String id) {
        return Optional.empty();
    }

    @Override
    public void eliminar(String id) {
    }
}
