package com.ucuenca.proyecto_courier.CapaPresentacion.Clientes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClienteModel {
    private final StringProperty idCliente = new SimpleStringProperty("");
    private final StringProperty nombre = new SimpleStringProperty("");
    private final StringProperty direccion = new SimpleStringProperty("");
    private final StringProperty telefono = new SimpleStringProperty("");

    //BINDING de los datos
    public StringProperty idClienteProperty() { return idCliente; }
    public StringProperty nombreProperty() { return nombre; }
    public StringProperty direccionProperty() { return direccion; }
    public StringProperty telefonoProperty() { return telefono; }

    public String getIdCliente() { return idCliente.get(); }
    public void setIdCliente(String id) { this.idCliente.set(id); }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String nom) { this.nombre.set(nom); }

    public String getDireccion() { return direccion.get(); }
    public void setDireccion(String dir) { this.direccion.set(dir); }

    public String getTelefono() { return telefono.get(); }
    public void setTelefono(String tel) { this.telefono.set(tel); }
}

class ContextoCliente {
    private static ClienteModel clienteSeleccionado;
    private static NavegadorVistas navegadorGlobal;

    public static void setCliente(ClienteModel cliente) {
        clienteSeleccionado = cliente;
    }

    public static ClienteModel getCliente() {
        return clienteSeleccionado;
    }

    public static void setNavegador(NavegadorVistas nav) {
        navegadorGlobal = nav;
    }

    public static NavegadorVistas getNavegador() {
        return navegadorGlobal;
    }
}