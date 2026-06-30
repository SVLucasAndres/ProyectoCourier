package com.ucuenca.proyecto_courier.CapaDominio.DTO;

import java.util.List;

public class ClienteEnviosDTO extends ClienteDTO {
    private List<EnvioDTO> listaEnvios;

    public ClienteEnviosDTO() {
        super();
    }

    public ClienteEnviosDTO(String idCliente, String nombre, String direccion, String telefono, boolean active, List<EnvioDTO> listaEnvios) {
        super(idCliente, nombre, direccion, telefono, active);
        this.listaEnvios = listaEnvios;
    }

    public List<EnvioDTO> getListaEnvios() {
        return listaEnvios;
    }

    public void setListaEnvios(List<EnvioDTO> listaEnvios) {
        this.listaEnvios = listaEnvios;
    }
}
