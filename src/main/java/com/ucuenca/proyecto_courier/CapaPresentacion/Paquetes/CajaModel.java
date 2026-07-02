package com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class CajaModel extends PaqueteModel {
    private final DoubleProperty alto = new SimpleDoubleProperty();
    private final DoubleProperty ancho = new SimpleDoubleProperty();
    private final DoubleProperty largo = new SimpleDoubleProperty();

    public DoubleProperty altoProperty(){return alto;}
    public DoubleProperty anchoProperty(){return ancho;}
    public DoubleProperty largoProperty(){return largo;}

    public Double getAlto(){return alto.get();}
    public Double getAncho(){return ancho.get();}
    public Double getLargo(){return largo.get();}

    public void setAlto(double alto){this.alto.set(alto);}
    public void setAncho(double ancho){this.ancho.set(ancho);}
    public void setLargo(double largo){this.largo.set(largo);}
}
