package com.ucuenca.proyecto_courier.CapaPresentacion.Configuracion;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConfiguracionModel {
    private final StringProperty idConfiguracion = new SimpleStringProperty();
    private final DoubleProperty impuestoIVA = new SimpleDoubleProperty();
    private final ObservableList<RangoModel> rangos = FXCollections.observableArrayList();

    public StringProperty idConfiguracionProperty() { return idConfiguracion; }
    public String getIdConfiguracion() { return idConfiguracion.get(); }
    public void setIdConfiguracion(String id) { this.idConfiguracion.set(id); }

    public DoubleProperty impuestoIVAProperty() { return impuestoIVA; }
    public double getImpuestoIVA() { return impuestoIVA.get(); }
    public void setImpuestoIVA(double iva) { this.impuestoIVA.set(iva); }

    public ObservableList<RangoModel> getRangos() { return rangos; }
    public void setRangos(java.util.List<RangoModel> lista) { this.rangos.setAll(lista); }
}