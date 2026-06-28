package com.ucuenca.proyecto_courier.CapaDA.xml;
import com.ucuenca.proyecto_courier.CapaDominio.Oficina;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.DAOXML;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class OficinaXmlDAO extends DAOXML<Oficina> {
    public OficinaXmlDAO() {
        this.rutaArchivo = "oficinas.xml";
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
