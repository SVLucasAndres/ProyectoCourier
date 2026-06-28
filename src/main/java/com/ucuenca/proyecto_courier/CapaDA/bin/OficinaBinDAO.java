package com.ucuenca.proyecto_courier.CapaDA.bin;
import com.ucuenca.proyecto_courier.CapaDominio.Oficina;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.DAOBIN;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class OficinaBinDAO extends DAOBIN<Oficina> {
    public OficinaBinDAO() {
        this.rutaArchivo = "oficinas.bin";
    }

    public Optional<Oficina> buscarPorNombre(String nombre) {
        return Optional.empty();
    }

    @Override
    public void guardar(Oficina entidad) {}

    @Override
    public List<Oficina> obtenerTodos() { return new ArrayList<>(); }

    @Override
    public Optional<Oficina> buscarPorId(String id) { return Optional.empty(); }

    @Override
    public void eliminar(String id) {}
}
