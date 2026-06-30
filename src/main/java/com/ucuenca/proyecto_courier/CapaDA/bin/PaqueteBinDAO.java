package com.ucuenca.proyecto_courier.CapaDA.bin;
import com.ucuenca.proyecto_courier.CapaDominio.Paquete;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.DAOBIN;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class PaqueteBinDAO extends DAOBIN<Paquete> {
    public PaqueteBinDAO() {
        this.rutaArchivo = "paquetes.bin";
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
