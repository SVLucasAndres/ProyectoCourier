package com.ucuenca.proyecto_courier.CapaPresentacion;

import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ClienteModel;

public class ClienteActual {
    private static ClienteModel clienteActual = new ClienteModel();
    private static ClienteActual instancia;
    private static boolean isAdmin;
    private ClienteActual(){}

    public static ClienteActual getInstance() {
        if (instancia == null) {
            instancia = new ClienteActual();
        }
        return instancia;
    }

    public static void setClienteActual(ClienteModel cliente){
        clienteActual = cliente;
    }

    public static ClienteModel getClienteActual() {
        return clienteActual;
    }

    public static boolean isIsAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(boolean isAdmin) {
        ClienteActual.isAdmin = isAdmin;
    }
}
