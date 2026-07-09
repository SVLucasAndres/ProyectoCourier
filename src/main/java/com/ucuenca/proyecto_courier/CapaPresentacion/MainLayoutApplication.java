package com.ucuenca.proyecto_courier.CapaPresentacion;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.AutenticacionExcepcion;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.EntidadNoEncontradaException;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ClienteService;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ClienteMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MainLayoutApplication extends Application {

    private NavegadorVistas navegador;

    @Override
    public void start(Stage stage) {
        String persistenciaSeleccionada = mostrarDialogoPersistencia();

        if (persistenciaSeleccionada == null) {
            System.out.println("Ejecución cancelada por el usuario.");
            System.exit(0);
        }

        System.out.println("Iniciando componentes con: " + persistenciaSeleccionada);
        GestorServicios.inicializarComponentes(persistenciaSeleccionada);

        mostrarLoginDialog(stage);
    }

    private String mostrarDialogoPersistencia() {
        List<String> opciones = Arrays.asList("XML", "BIN");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("XML", opciones);
        dialog.setTitle("Configuración del Sistema");
        dialog.setHeaderText("Selección de Persistencia de Datos");
        dialog.setContentText("Elija el formato para guardar los archivos:");

        Optional<String> resultado = dialog.showAndWait();
        return resultado.orElse(null);
    }

    public void mostrarLoginDialog(Stage stage) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Iniciar Sesión - Courier System");
        dialog.setHeaderText("Ingrese sus credenciales de acceso para continuar:");

        ButtonType buttonTypeOk = new ButtonType("Ingresar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 50, 10, 50));

        ComboBox<String> cmbRol = new ComboBox<>();
        cmbRol.getItems().addAll("Administrador", "Cliente");
        cmbRol.setPromptText("Seleccione su rol");
        cmbRol.setMaxWidth(Double.MAX_VALUE);

        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Usuario o Correo");

        PasswordField txtContrasena = new PasswordField();
        txtContrasena.setPromptText("Contraseña o Cédula");

        grid.add(new Label("Tipo de Usuario:"), 0, 0);
        grid.add(cmbRol, 1, 0);
        grid.add(new Label("Usuario:"), 0, 1);
        grid.add(txtUsuario, 1, 1);
        grid.add(new Label("Contraseña:"), 0, 2);
        grid.add(txtContrasena, 1, 2);

        dialog.getDialogPane().setContent(grid);

        final Button btnIngresar = (Button) dialog.getDialogPane().lookupButton(buttonTypeOk);
        btnIngresar.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            String rol = cmbRol.getValue();
            String usuario = txtUsuario.getText().trim();
            String contrasena = txtContrasena.getText().trim();

            if (rol == null || usuario.isEmpty() || contrasena.isEmpty()) {
                mostrarAlerta(Alert.AlertType.ERROR, "Campos Incompletos", "Todos los campos son obligatorios.");
                event.consume();
                return;
            }
            ClienteActual.setIsAdmin(false);
            // Validación según el rol seleccionado
            if (rol.equals("Administrador")) {
                ClienteActual.setIsAdmin(true);
                if (!(usuario.equals("admin") && contrasena.equals("admin123"))) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error de Autenticación", "Credenciales de Administrador incorrectas.");
                    event.consume();
                }
            } else if (rol.equals("Cliente")) {
                ClienteActual.setIsAdmin(false);
                try {
                    verificarCredencial(usuario, contrasena);
                } catch (EntidadNoEncontradaException | AutenticacionExcepcion e) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error de Autenticación", e.getMessage());
                    event.consume(); // Cancela el cierre si hay credenciales inválidas
                }
            }
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeOk) {
                return cmbRol.getValue();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(rolSeleccionado -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainLayoutView.fxml"));
                BorderPane root = fxmlLoader.load();

                // Aquí puedes obtener el controlador si necesitas pasarle el rol
                MainLayoutController controller = fxmlLoader.getController();
                if (controller != null) {
                    controller.configurarMenuSegunRol(rolSeleccionado);
                }

                Scene scene = new Scene(root, 900, 600);
                stage.setTitle("Sistema de Gestión - Courier (" + rolSeleccionado + ")");
                stage.setScene(scene);
                stage.setMinWidth(700);
                stage.setMinHeight(500);
                stage.show();

            } catch (IOException e) {
                System.err.println("Error crítico: No se pudo cargar el archivo FXML de la vista.");
                e.printStackTrace();
            }
        });
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ClienteDTO verificarCredencial(String usuario, String pass) throws EntidadNoEncontradaException, AutenticacionExcepcion {
        ClienteService servicioCliente = GestorServicios.getInstance().obtenerServicioCliente();

        ClienteDTO dto = servicioCliente.buscarClientePorID(usuario);
        if (dto == null) {
            throw new EntidadNoEncontradaException("Este usuario no existe en el sistema.");
        }

        ClienteDTO clienteAutenticado = servicioCliente.validarLogin(dto, pass);

        // Si no arrojó excepción el paso anterior, seteamos la sesión global
        ClienteActual.setClienteActual(ClienteMapper.dtoToModelo(clienteAutenticado));

        return clienteAutenticado;
    }
}