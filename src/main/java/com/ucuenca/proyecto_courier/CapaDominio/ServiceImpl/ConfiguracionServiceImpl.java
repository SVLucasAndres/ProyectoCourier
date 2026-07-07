package com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl;

import com.ucuenca.proyecto_courier.CapaDominio.Configuracion;
import com.ucuenca.proyecto_courier.CapaDominio.Rango;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.ConfiguracionDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.RangoDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ConfiguracionService;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.EntidadNoEncontradaException;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.OperacionInvalidaException;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.ValidacionException;
import com.ucuenca.proyecto_courier.CapaDA.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConfiguracionServiceImpl implements ConfiguracionService {
    private DAO<Configuracion> configDAO;

    public ConfiguracionServiceImpl(DAO<Configuracion> configDAO) {
        this.configDAO = configDAO;
    }

    @Override
    public ConfiguracionDTO obtenerConfiguracion() {
        if (configDAO == null) {
            throw new OperacionInvalidaException("El DAO de configuración no está inicializado.");
        }
        Optional<Configuracion> opt = configDAO.buscarPorId("GLOBAL");
        if (opt.isPresent()) {
            Configuracion c = opt.get();
            ConfiguracionDTO dto = new ConfiguracionDTO();
            dto.setImpuestoIVA(c.getImpuestoIVA());
            dto.setIdConfiguracion(c.getIdConfiguracion());
            List<RangoDTO> rangosDto = new ArrayList<>();
            if (c.getRangos() != null) {
                for (Rango r : c.getRangos()) {
                    rangosDto.add(new RangoDTO(r.getNombre(), r.getPesoMinimo(), r.getPesoMaximo(), r.getCostoPorKilogramo()));
                }
            }
            dto.setRangos(rangosDto);
            return dto;
        } else {
            throw new EntidadNoEncontradaException("La configuración GLOBAL no existe en la base de datos.");
        }
    }

    @Override
    public void guardarConfiguracion(ConfiguracionDTO configuracion) {
        if (configDAO == null) {
            throw new OperacionInvalidaException("El DAO de configuración no está inicializado.");
        }
        if (configuracion == null) {
            throw new ValidacionException("La configuración proporcionada es nula.");
        }
        List<Rango> rangos = new ArrayList<>();
        if (configuracion.getRangos() != null) {
            for (RangoDTO r : configuracion.getRangos()) {
                rangos.add(new Rango(r.getNombre(), r.getPesoMinimo(), r.getPesoMaximo(), r.getCostoPorKilogramo()));
            }
        }
        Configuracion c = new Configuracion(configuracion.getImpuestoIVA(), rangos);
        configDAO.guardar(c);
    }
}
