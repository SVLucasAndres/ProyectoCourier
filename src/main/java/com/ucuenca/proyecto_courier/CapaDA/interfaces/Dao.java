package com.ucuenca.proyecto_courier.CapaDA.interfaces;

import java.util.List;

public interface Dao<T, ID> {
    void crear(T entidad);

    T leer(ID id);

    void actualizar(T entidad);

    void eliminar(ID id);

    List<T> obtenerTodos();
}
