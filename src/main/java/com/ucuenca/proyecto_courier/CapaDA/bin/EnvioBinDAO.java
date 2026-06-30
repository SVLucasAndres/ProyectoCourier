package com.ucuenca.proyecto_courier.CapaDA.bin;
import com.ucuenca.proyecto_courier.CapaDominio.Envio;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.DAOBIN;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class EnvioBinDAO extends DAOBIN<Envio> {
    public EnvioBinDAO() {
        this.rutaArchivo = "envios.bin";
    }

    @Override
    public void guardar(Envio entidad) {}

    @Override
    public List<Envio> obtenerTodos() { return new ArrayList<>(); }

    @Override
    public Optional<Envio> buscarPorId(String id) { return Optional.empty(); }

    @Override
    public void eliminar(String id) {}
}
