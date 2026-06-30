package com.ucuenca.proyecto_courier.CapaPresentacion;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class MainLayoutController {

    // Enlazamos el VBox del centro usando el mismo fx:id
    @FXML private VBox panelCentral;

    @FXML
    public void initialize() {

        // Al arrancar, cargamos la lista automáticamente en ese espacio
        cargarVistaClientes();
    }

    private void cargarVistaClientes(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Clientes/ListaClientesView.fxml"));
            Parent vistaClientes = loader.load();
            panelCentral.getChildren().clear();
            panelCentral.getChildren().add(vistaClientes);
            VBox.setVgrow(vistaClientes, javafx.scene.layout.Priority.ALWAYS);

        } catch(IOException e){
            System.out.println("No se pudo incrustar la vista de clientes.");
            e.printStackTrace();
        }
    }
}
