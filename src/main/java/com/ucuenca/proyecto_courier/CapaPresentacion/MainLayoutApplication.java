package com.ucuenca.proyecto_courier.CapaPresentacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MainLayoutApplication extends Application {
    @Override
    public void start(Stage stage) {
        String persistenciaSeleccionada = mostrarDialogoPersistencia();

        if (persistenciaSeleccionada == null) {
            System.out.println("Ejecución cancelada por el usuario.");
            System.exit(0);
        }

        System.out.println("Iniciando componentes con: " + persistenciaSeleccionada);
        //Inicializamos DAOS y hacemos la relacion de dependencias
        GestorServicios.inicializarComponentes(persistenciaSeleccionada);

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

    private String mostrarDialogoPersistencia() {
        List<String> opciones = Arrays.asList("XML", "BIN");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("XML", opciones);
        dialog.setTitle("Configuración del Sistema");
        dialog.setHeaderText("Selección de Persistencia de Datos");
        dialog.setContentText("Elija el formato para guardar los archivos:");

        // Bloquea la app hasta que el usuario elija
        Optional<String> resultado = dialog.showAndWait();

        return resultado.orElse(null);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
