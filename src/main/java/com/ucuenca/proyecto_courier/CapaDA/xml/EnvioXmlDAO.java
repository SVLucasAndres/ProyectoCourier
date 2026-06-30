package com.ucuenca.proyecto_courier.CapaDA.xml;
import com.ucuenca.proyecto_courier.CapaDominio.Envio;
import com.ucuenca.proyecto_courier.CapaDA.interfaces.DAOXML;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class EnvioXmlDAO extends DAOXML<Envio> {
    public EnvioXmlDAO() {
        this.rutaArchivo = "envios.xml";
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
