package com.ucuenca.proyecto_courier.CapaPresentacion.Envios;

import javafx.beans.property.*;

public class EnvioModel {
    private final StringProperty idEnvio = new SimpleStringProperty();
    private final StringProperty idDestinatario = new SimpleStringProperty();
    private final StringProperty idRemitente = new SimpleStringProperty();
    private final StringProperty rapidez = new SimpleStringProperty();
    private final StringProperty metodoPago = new SimpleStringProperty();
    private final IntegerProperty cantidadPaquetes = new SimpleIntegerProperty();

    // Property getters
    public StringProperty idEnvioProperty() { return idEnvio; }
    public StringProperty idDestinatarioProperty() { return idDestinatario; }
    public StringProperty idRemitenteProperty() { return idRemitente; }
    public StringProperty rapidezProperty() { return rapidez; }
    public StringProperty metodoPagoProperty() { return metodoPago; }
    public IntegerProperty cantidadPaquetesProperty() { return cantidadPaquetes; }

    // Getters y Setters estándar
    public String getIdEnvio() { return idEnvio.get(); }
    public void setIdEnvio(String value) { idEnvio.set(value); }

    public String getIdDestinatario() { return idDestinatario.get(); }
    public void setIdDestinatario(String value) { idDestinatario.set(value); }

    public String getIdRemitente() { return idRemitente.get(); }
    public void setIdRemitente(String value) { idRemitente.set(value); }

    public String getRapidez() {

        return rapidez.get();
    }
    public void setRapidez(String value) { rapidez.set(value); }

    public String getMetodoPago() { return metodoPago.get(); }
    public void setMetodoPago(String value) { metodoPago.set(value); }

    public int getCantidadPaquetes() { return cantidadPaquetes.get(); }
    public void setCantidadPaquetes(int value) { cantidadPaquetes.set(value); }
}