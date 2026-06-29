package com.ucuenca.proyecto_courier.CapaDominio;

import java.io.Serializable;

public class Configuracion implements Serializable {
    private String idConfiguracion;
    // Add basic properties that a configuration might have
    private String clave;
    private String valor;

    public Configuracion() {
    }

    public String getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(String idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
