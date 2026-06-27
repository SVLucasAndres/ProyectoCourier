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

    public String getNombreOficina() { return nombreOficina; }
    public void setNombreOficina(String nombreOficina) { this.nombreOficina = nombreOficina; }

    public LocalDateTime getHoraLlegada() { return horaLlegada; }
    public void setHoraLlegada(LocalDateTime horaLlegada) { this.horaLlegada = horaLlegada; }

    public LocalDateTime getHoraSalida() { return horaSalida; }
    public void setHoraSalida(LocalDateTime horaSalida) { this.horaSalida = horaSalida; }

    public Oficina getOficina() { return oficina; }
    public void setOficina(Oficina oficina) { this.oficina = oficina; }
}
