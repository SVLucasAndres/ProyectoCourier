package com.ucuenca.proyecto_courier.CapaPresentacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainLayoutApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            // Usamos una ruta absoluta (empieza con '/') para buscar directamente desde la raíz de resources
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainLayoutView.fxml"));

            // Cargamos el contenedor raíz
            BorderPane root = fxmlLoader.load();

            // Configurar la escena
            Scene scene = new Scene(root, 900, 600);

            // Configurar el Stage (la ventana principal)
            stage.setTitle("Sistema de Gestión de Clientes - Courier");
            stage.setScene(scene);
            stage.setMinWidth(700);
            stage.setMinHeight(500);

            stage.show();

        } catch (IOException e) {
            System.err.println("Error crítico: No se pudo cargar el archivo FXML de la vista.");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Error crítico: No se encontró el archivo FXML en la ruta especificada.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
