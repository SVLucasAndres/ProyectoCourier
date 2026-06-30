package com.ucuenca.proyecto_courier.CapaDA;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class DAOXML<T> implements DAO<T> {
    private String rutaArchivo;

    public DAOXML(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    protected String getRutaArchivo() {
        return rutaArchivo;
    }

    protected abstract String obtenerId(T entidad);

    @Override
    public void guardar(T entidad) {
        List<T> lista = obtenerTodos();
        // Si ya existe, actualizar; si no, agregar
        boolean encontrado = false;
        for (int i = 0; i < lista.size(); i++) {
            if (obtenerId(lista.get(i)).equals(obtenerId(entidad))) {
                lista.set(i, entidad);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            lista.add(entidad);
        }
        escribirArchivo(lista);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> obtenerTodos() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists() || archivo.length() == 0) {
            return new ArrayList<>();
        }
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(archivo)))) {
            Object obj = decoder.readObject();
            if (obj instanceof List) {
                return (List<T>) obj;
            }
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<T> buscarPorId(String id) {
        List<T> lista = obtenerTodos();
        for (T entidad : lista) {
            if (obtenerId(entidad).equals(id)) {
                return Optional.of(entidad);
            }
        }
        return Optional.empty();
    }

    @Override
    public void eliminar(String id) {
        List<T> lista = obtenerTodos();
        lista.removeIf(entidad -> obtenerId(entidad).equals(id));
        escribirArchivo(lista);
    }

    private void escribirArchivo(List<T> lista) {
        File archivo = new File(rutaArchivo);
        // Crear directorios padre si no existen
        if (archivo.getParentFile() != null && !archivo.getParentFile().exists()) {
            archivo.getParentFile().mkdirs();
        }
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(archivo)))) {
            encoder.writeObject(lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
