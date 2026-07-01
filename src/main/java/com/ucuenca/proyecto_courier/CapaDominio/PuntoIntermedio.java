package com.ucuenca.proyecto_courier.CapaDominio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PuntoIntermedio {
    private String nombreOficina;
    private LocalDateTime horaLlegada;
    private LocalDateTime horaSalida;
    private Oficina oficina;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public PuntoIntermedio() {
    }

    public PuntoIntermedio(String nombreOficina, LocalDateTime horaLlegada, LocalDateTime horaSalida, Oficina oficina) {
        this.nombreOficina = nombreOficina;
        this.horaLlegada = horaLlegada;
        this.horaSalida = horaSalida;
        this.oficina = oficina;
    }
    public String getNombreOficina() { return nombreOficina; }
    public void setNombreOficina(String nombreOficina) { this.nombreOficina = nombreOficina; }

    public LocalDateTime getHoraLlegada() { return horaLlegada; }
    public void setHoraLlegada(LocalDateTime horaLlegada) { this.horaLlegada = horaLlegada; }

    public LocalDateTime getHoraSalida() { return horaSalida; }
    public void setHoraSalida(LocalDateTime horaSalida) { this.horaSalida = horaSalida; }

    public Oficina getOficina() { return oficina; }
    public void setOficina(Oficina oficina) { this.oficina = oficina; }


    // Problema deserializacion en XML ENCODER para fechas
    public String getHoraLlegadaXml() {
        return (this.horaLlegada != null) ? this.horaLlegada.format(FORMATTER) : null;
    }

    public void setHoraLlegadaXml(String horaStr) {
        if (horaStr != null && !horaStr.isEmpty()) {
            this.horaLlegada = LocalDateTime.parse(horaStr, FORMATTER);
        } else {
            this.horaLlegada = null;
        }
    }
    public String getHoraSalidaXml() {
        return (this.horaSalida != null) ? this.horaSalida.format(FORMATTER) : null;
    }

    public void setHoraSalidaXml(String horaStr) {
        if (horaStr != null && !horaStr.isEmpty()) {
            this.horaSalida = LocalDateTime.parse(horaStr, FORMATTER);
        } else {
            this.horaSalida = null;
        }
    }
}