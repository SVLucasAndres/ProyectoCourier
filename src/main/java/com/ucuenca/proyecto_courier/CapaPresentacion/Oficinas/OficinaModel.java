package com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OficinaModel {
    private final StringProperty idOficina = new SimpleStringProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty direccion = new SimpleStringProperty();
    private final StringProperty telefono = new SimpleStringProperty();

    //Property getters
    public StringProperty idOficinaProperty(){return idOficina;}
    public StringProperty nombreProperty(){return nombre;}
    public StringProperty direccionProperty(){return direccion;}
    public StringProperty telefonoProperty(){return telefono;}

    //Getters y Setter estandar
    public String getIdOficina(){return idOficina.get();}
    public void setIdOficina(String value){idOficina.set(value);}

    public String getNombre(){return nombre.get();}
    public void setNombre(String value){nombre.set(value);}

    public String getDireccion(){return direccion.get();}
    public void setDireccion(String value){direccion.set(value);}

    public String getTelefono(){return telefono.get();}
    public void setTelefono(String value){telefono.set(value);}
}
