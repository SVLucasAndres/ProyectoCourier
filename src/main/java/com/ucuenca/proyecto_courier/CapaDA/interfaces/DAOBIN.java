package com.ucuenca.proyecto_courier.CapaDA.interfaces;

import java.util.List;
import java.util.Optional;

public abstract class DAOBIN<T> implements DAO<T> {
    protected String rutaArchivo;

    @Override
    public abstract void guardar(T entidad);

    @Override
    public abstract List<T> obtenerTodos();

    @Override
    public abstract Optional<T> buscarPorId(String id);

    @Override
    public abstract void eliminar(String id);
}
