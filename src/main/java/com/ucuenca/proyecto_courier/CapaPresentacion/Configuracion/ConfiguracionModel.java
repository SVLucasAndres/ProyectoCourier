package com.ucuenca.proyecto_courier.CapaPresentacion.Configuracion;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class ConfiguracionModel {
    private final StringProperty idConfiguracion = new SimpleStringProperty();
    private final DoubleProperty impuestoIVA = new SimpleDoubleProperty();
    private final ObservableList<RangoModel> rangos = FXCollections.observableArrayList();

    // Propiedades adicionales
    private final DoubleProperty porcentajeSeguro = new SimpleDoubleProperty();
    private final DoubleProperty tasaEntregaInmediata = new SimpleDoubleProperty();
    private final DoubleProperty tasaEntregaSegundoDia = new SimpleDoubleProperty();
    private final DoubleProperty tasaEntregaNormal = new SimpleDoubleProperty();

    // ID Configuración
    public StringProperty idConfiguracionProperty() { return idConfiguracion; }
    public String getIdConfiguracion() { return idConfiguracion.get(); }
    public void setIdConfiguracion(String id) { this.idConfiguracion.set(id); }

    // IVA
    public DoubleProperty impuestoIVAProperty() { return impuestoIVA; }
    public double getImpuestoIVA() { return impuestoIVA.get(); }
    public void setImpuestoIVA(double iva) { this.impuestoIVA.set(iva); }

    // Rangos (Lista observable)
    public ObservableList<RangoModel> getRangos() { return rangos; }
    public void setRangos(List<RangoModel> lista) { this.rangos.setAll(lista); }

    // Porcentaje Seguro
    public DoubleProperty porcentajeSeguroProperty() { return porcentajeSeguro; }
    public double getPorcentajeSeguro() { return porcentajeSeguro.get(); }
    public void setPorcentajeSeguro(double porcentaje) { this.porcentajeSeguro.set(porcentaje); }

    // Tasa Entrega Inmediata
    public DoubleProperty tasaEntregaInmediataProperty() { return tasaEntregaInmediata; }
    public double getTasaEntregaInmediata() { return tasaEntregaInmediata.get(); }
    public void setTasaEntregaInmediata(double tasa) { this.tasaEntregaInmediata.set(tasa); }

    // Tasa Entrega Segundo Día
    public DoubleProperty tasaEntregaSegundoDiaProperty() { return tasaEntregaSegundoDia; }
    public double getTasaEntregaSegundoDia() { return tasaEntregaSegundoDia.get(); }
    public void setTasaEntregaSegundoDia(double tasa) { this.tasaEntregaSegundoDia.set(tasa); }

    // Tasa Entrega Normal
    public DoubleProperty tasaEntregaNormalProperty() { return tasaEntregaNormal; }
    public double getTasaEntregaNormal() { return tasaEntregaNormal.get(); }
    public void setTasaEntregaNormal(double tasa) { this.tasaEntregaNormal.set(tasa); }
}