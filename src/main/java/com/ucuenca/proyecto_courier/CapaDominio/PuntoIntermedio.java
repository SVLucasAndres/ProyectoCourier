package com.ucuenca.proyecto_courier.CapaDominio;

import java.time.LocalDateTime;

public class PuntoIntermedio {
    private String nombreOficina;
    private LocalDateTime horaLlegada;
    private LocalDateTime horaSalida;
    private Oficina oficina; // Representa la relación de agregación con Oficina

    public PuntoIntermedio(String nombreOficina, LocalDateTime horaLlegada, LocalDateTime horaSalida, Oficina oficina) {
        this.nombreOficina = nombreOficina;
        this.horaLlegada = horaLlegada;
        this.horaSalida = horaSalida;
        this.oficina = oficina;
    }
}
