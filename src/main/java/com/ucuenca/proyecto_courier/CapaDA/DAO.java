package com.ucuenca.proyecto_courier.CapaDA;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    void guardar(T entidad);
    List<T> obtenerTodos();
    Optional<T> buscarPorId(String id);
    void eliminar(String id);
}
