package com.ucuenca.proyecto_courier.CapaDominio;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RutaSeguimiento implements Serializable {
    private List<PuntoIntermedio> puntosIntermedios;

    public RutaSeguimiento() {
        this.puntosIntermedios = new ArrayList<>();
    }

    public void agregarPaso(PuntoIntermedio puntoIntermedio) {
        if (this.puntosIntermedios == null) {
            this.puntosIntermedios = new ArrayList<>();
        }
        this.puntosIntermedios.add(puntoIntermedio);
    }

    public List<PuntoIntermedio> getPuntosIntermedios() {
        return puntosIntermedios;
    }

    public void setPuntosIntermedios(List<PuntoIntermedio> puntosIntermedios) {
        this.puntosIntermedios = puntosIntermedios;
    }

    public static class PuntoIntermedio implements Serializable {
        private String nombreOficina;
        private LocalDateTime horaLlegada;
        private LocalDateTime horaSalida;
        private Oficina oficina;

        public PuntoIntermedio() {
        }

        public String getNombreOficina() {
            return nombreOficina;
        }

        public void setNombreOficina(String nombreOficina) {
            this.nombreOficina = nombreOficina;
        }

        public LocalDateTime getHoraLlegada() {
            return horaLlegada;
        }

        public void setHoraLlegada(LocalDateTime horaLlegada) {
            this.horaLlegada = horaLlegada;
        }

        public LocalDateTime getHoraSalida() {
            return horaSalida;
        }

        public void setHoraSalida(LocalDateTime horaSalida) {
            this.horaSalida = horaSalida;
        }

        public Oficina getOficina() {
            return oficina;
        }

        public void setOficina(Oficina oficina) {
            this.oficina = oficina;
        }
    }
}
