package com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes;

import javafx.beans.property.*;

public abstract class PaqueteModel {
    private final StringProperty idPaquete = new SimpleStringProperty();
    private final BooleanProperty seguro = new SimpleBooleanProperty();
    private final DoubleProperty peso = new SimpleDoubleProperty();
    private final DoubleProperty valor = new SimpleDoubleProperty();

    // Property getters
    public StringProperty idPaqueteProperty() { return idPaquete; }
    public BooleanProperty seguroProperty() { return seguro; }
    public DoubleProperty pesoProperty() { return peso; }
    public DoubleProperty valorProperty() { return valor; }

    // Getters y Setters estándar
    public String getIdPaquete() { return idPaquete.get(); }
    public void setIdPaquete(String value) { idPaquete.set(value); }

    public Double getValor() { return valor.get(); }
    public void setValor(Double value) { valor.set(value); }

    public Boolean getSeguro() { return seguro.get(); }
    public void setSeguro(Boolean value) { seguro.set(value); }

    public Double getPeso() {

        return peso.get();
    }
    public void setPeso(Double value) { peso.set(value); }

}
