package com.ucuenca.proyecto_courier.CapaDominio.interfaces;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.ConfiguracionDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface PaqueteService {

    void crearPaquete(PaqueteDTO paquete, ConfiguracionDTO configuracion);

    PaqueteDTO buscarPaquetePorID(String idPaquete);
    List<PaqueteDTO> mostrarListaPaquetes();
    List<PaqueteDTO> mostrarPaquetesSinEnvio();
    void registrarMovimientoPaquete(String idPaquete, String nombreOficina, java.time.LocalDateTime fechaHora, boolean esLlegada, String textoFormateado);


    void agregarPuntoRuta(String idPaquete, String nombreOficina);

    List<PaqueteDTO> obtenerPaquetesPorDestinatario(String idDestinatario);

    List<PaqueteDTO> obtenerPaquetesPorRemitente(String idRemitente);

    List<String> obtenerTextosRutaConEstados(String idPaquete);
}
