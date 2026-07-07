package com.ucuenca.proyecto_courier.CapaPresentacion.Configuracion;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RangoModel {
    private final StringProperty nombre = new SimpleStringProperty();
    private final DoubleProperty pesoMinimo = new SimpleDoubleProperty();
    private final DoubleProperty pesoMaximo = new SimpleDoubleProperty();
    private final DoubleProperty costoPorKilogramo = new SimpleDoubleProperty();

    public StringProperty nombreProperty() { return nombre; }
    public String getNombre() { return nombre.get(); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }

    public DoubleProperty pesoMinimoProperty() { return pesoMinimo; }
    public double getPesoMinimo() { return pesoMinimo.get(); }
    public void setPesoMinimo(double pesoMinimo) { this.pesoMinimo.set(pesoMinimo); }

    public DoubleProperty pesoMaximoProperty() { return pesoMaximo; }
    public double getPesoMaximo() { return pesoMaximo.get(); }
    public void setPesoMaximo(double pesoMaximo) { this.pesoMaximo.set(pesoMaximo); }

    public DoubleProperty costoPorKilogramoProperty() { return costoPorKilogramo; }
    public double getCostoPorKilogramo() { return costoPorKilogramo.get(); }
    public void setCostoPorKilogramo(double costoPorKilogramo) { this.costoPorKilogramo.set(costoPorKilogramo); }
}