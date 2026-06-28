package com.ucuenca.proyecto_courier.CapaDA.bin;

import com.ucuenca.proyecto_courier.CapaDominio.Cliente;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.DAOBIN;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class ClienteBinDAO extends DAOBIN<Cliente> {
    public ClienteBinDAO() {
        this.rutaArchivo = "clientes.bin";
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
