package com.ucuenca.proyecto_courier.CapaDA.bin;

import com.ucuenca.proyecto_courier.CapaDominio.Configuracion;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.DAOBIN;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConfiguracionBinDAO extends DAOBIN<Configuracion> {

    public ConfiguracionBinDAO() {
        this.rutaArchivo = "configuracion.bin";
    }

    @Override
    public void guardar(Configuracion entidad) {
        // TODO: Implementar escritura BIN que sobrescriba el archivo
    }

    @Override
    public List<Configuracion> obtenerTodos() {
        // TODO: Implementar lectura BIN
        return new ArrayList<>();
    }

    @Override
    public Optional<Configuracion> buscarPorId(String id) {
        List<Configuracion> todos = obtenerTodos();
        if (!todos.isEmpty()) {
            return Optional.of(todos.get(0));
        }
        return Optional.empty();
    }

    @Override
    public void eliminar(String id) {
        // Acción prohibida
        System.out.println("Error: No se puede eliminar la configuracion global.");
    }
}
