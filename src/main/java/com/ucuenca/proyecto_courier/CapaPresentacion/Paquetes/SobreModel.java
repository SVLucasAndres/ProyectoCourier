package com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SobreModel extends PaqueteModel{
    private final StringProperty tamano = new SimpleStringProperty();

    public StringProperty tamanoProperty(){return tamano;}

    public String getTamano(){return tamano.get();}

    public void setTamano(String tamano){this.tamano.set(tamano);}
}
