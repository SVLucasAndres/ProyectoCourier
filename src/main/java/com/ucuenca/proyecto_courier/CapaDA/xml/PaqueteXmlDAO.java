package com.ucuenca.proyecto_courier.CapaDA.xml;
import com.ucuenca.proyecto_courier.CapaDominio.Paquete;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.DAOXML;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class PaqueteXmlDAO extends DAOXML<Paquete> {
    public PaqueteXmlDAO() {
        this.rutaArchivo = "paquetes.xml";
    }

    @Override
    public void guardar(Paquete entidad) {}

    @Override
    public List<Paquete> obtenerTodos() { return new ArrayList<>(); }

    @Override
    public Optional<Paquete> buscarPorId(String id) { return Optional.empty(); }

    @Override
    public void eliminar(String id) {}
}
