package com.ucuenca.proyecto_courier.CapaDA.xml;

import com.ucuenca.proyecto_courier.CapaDominio.Configuracion;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.DAOXML;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConfiguracionXmlDAO extends DAOXML<Configuracion> {

    public ConfiguracionXmlDAO() {
        this.rutaArchivo = "configuracion.xml";
    }

    @Override
    public void guardar(Configuracion entidad) {
        // TODO: Implementar escritura XML que sobrescriba el archivo
    }

    @Override
    public List<Configuracion> obtenerTodos() {
        // TODO: Implementar lectura XML
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
