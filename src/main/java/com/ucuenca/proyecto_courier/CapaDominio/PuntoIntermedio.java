package com.ucuenca.proyecto_courier.CapaDominio;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PuntoIntermedio implements Serializable {
    private LocalDateTime horaLlegada;
    private LocalDateTime horaSalida;
    private Oficina oficina;

    private String llegadaTexto;
    private String salidaTexto;

    @Serial
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public PuntoIntermedio() {
    }

    public PuntoIntermedio(LocalDateTime horaLlegada, LocalDateTime horaSalida, Oficina oficina) {
        this.horaLlegada = horaLlegada;
        this.horaSalida = horaSalida;
        this.oficina = oficina;
    }

    public LocalDateTime getHoraLlegada() {
        return (this.llegadaTexto != null) ? LocalDateTime.parse(this.llegadaTexto) : null;
    }

    public LocalDateTime getHoraSalida() {
        return (this.salidaTexto != null) ? LocalDateTime.parse(this.salidaTexto) : null;
    }

    public Oficina getOficina() { return oficina; }
    public void setOficina(Oficina oficina) { this.oficina = oficina; }


    public String getLlegadaTexto() {
        return (this.horaLlegada != null) ? this.horaLlegada.format(FORMATTER) : null;
    }

    public String getSalidaTexto() {
        return (this.horaSalida != null) ? this.horaSalida.format(FORMATTER) : null;
    }

    public void setLlegadaTexto(String horaStr) {
        this.llegadaTexto = horaStr;
        if (horaStr != null && !horaStr.isEmpty()) {
            this.horaLlegada = LocalDateTime.parse(horaStr, FORMATTER);
        } else {
            this.horaLlegada = null;
        }
    }

    public void setSalidaTexto(String horaStr) {
        this.salidaTexto = horaStr;
        if (horaStr != null && !horaStr.isEmpty()) {
            this.horaSalida = LocalDateTime.parse(horaStr, FORMATTER);
        } else {
            this.horaSalida = null;
        }
    }
}