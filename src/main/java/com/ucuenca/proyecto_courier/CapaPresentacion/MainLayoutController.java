package com.ucuenca.proyecto_courier.CapaPresentacion;

import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.AgregacionClientesController;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ListaClientesController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.awt.*;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MainLayoutController {

    // Enlazamos el VBox del centro usando el mismo fx:id
    @FXML private VBox panelCentral;
    @FXML private Button btnInicio;
    @FXML private Button btnClientes;
    @FXML private Button btnEnvios;
    @FXML private Button btnPaquetes;
    @FXML private Button btnOficinas;
    @FXML private Button btnConfig;


    @FXML
    public void initialize() {

        // Al arrancar, cargamos la lista automáticamente en ese espacio
        cargarVista("Inicio/InicioView.fxml","Inicio");
    }
    private void cargarVista(String rutaVista, @NotNull @NonNls String nombreVista){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaVista));
            Parent vistaClientes = loader.load();

            Object controlador = loader.getController();
            if (controlador instanceof ListaClientesController) {
                ((ListaClientesController) controlador).setNavegador(this::cargarVista);
            }else if (controlador instanceof AgregacionClientesController) {
                ((AgregacionClientesController) controlador).setNavegador(this::cargarVista);
            }

            panelCentral.getChildren().clear();
            panelCentral.getChildren().add(vistaClientes);
            VBox.setVgrow(vistaClientes, javafx.scene.layout.Priority.ALWAYS);

        } catch(IOException e){
            System.out.println("ERROR DE CARGA: No se pudo incrustar la vista de " + nombreVista + ".");
            e.printStackTrace();
        }
    }
    @FXML
    private void cambiarVista(ActionEvent evento){
        Object botonPresionado = evento.getSource();
        if (botonPresionado instanceof Button) {
            actualizarBotonActivo((Button) botonPresionado);
        }
        if(botonPresionado == btnClientes){
            cargarVista("Clientes/ListaClientesView.fxml","Clientes");
        }else if(botonPresionado == btnConfig){
            cargarVista("Configuraciones/ConfiguracionView.fxml","Configuracion");
        }else if(botonPresionado == btnEnvios){
            cargarVista("Envios/ListaEnviosView.fxml","Envios");
        }else if(botonPresionado == btnInicio){
            cargarVista("Inicio/InicioView.fxml","Inicio");
        }else if(botonPresionado == btnOficinas){
            cargarVista("Oficinas/OficinasView.fxml","Oficinas");
        }else if(botonPresionado == btnPaquetes){
            cargarVista("Paquetes/PaquetesView.fxml","Paquetes");
        }
    }

    private void actualizarBotonActivo(Button botonSeleccionado) {
        // Creamos un arreglo con todos tus botones del menú
        Button[] todosLosBotones = {btnInicio, btnClientes, btnEnvios, btnPaquetes, btnOficinas, btnConfig};

        String claseActiva = "button-menu-active";

        for (Button btn : todosLosBotones) {
            if (btn != null) {
                // Removemos el estilo activo si lo tiene
                btn.getStyleClass().remove(claseActiva);
            }
        }

        botonSeleccionado.getStyleClass().add(claseActiva);
    }
}

