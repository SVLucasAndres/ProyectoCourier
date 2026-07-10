package com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ModificarCliente;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.ValidacionException;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.EntidadNoEncontradaException;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ClienteService;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ClienteMapper;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ClienteModel;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ContextoCliente;
import com.ucuenca.proyecto_courier.GestorServicios;
import com.ucuenca.proyecto_courier.CapaPresentacion.NavegadorVistas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModificacionClientesController {

    @FXML private TextField txtCedula;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;

    @FXML private Label lblErrorNombre;
    @FXML private Label lblErrorDireccion;
    @FXML private Label lblErrorTelefono;

    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;

    private final ClienteModel clienteModel = new ClienteModel();

    private NavegadorVistas navegador;

    @FXML
    public void initialize() {
        // Binding
        txtCedula.textProperty().bindBidirectional(clienteModel.idClienteProperty());
        txtNombre.textProperty().bindBidirectional(clienteModel.nombreProperty());
        txtDireccion.textProperty().bindBidirectional(clienteModel.direccionProperty());
        txtTelefono.textProperty().bindBidirectional(clienteModel.telefonoProperty());

        clienteModel.setIdCliente(ContextoCliente.getCliente().getIdCliente());
        clienteModel.setNombre(ContextoCliente.getCliente().getNombre());
        clienteModel.setTelefono(ContextoCliente.getCliente().getTelefono());
        clienteModel.setDireccion(ContextoCliente.getCliente().getDireccion());

        txtCedula.setDisable(true);

        // Controla lo q el usuario escribe
        txtCedula.textProperty().addListener((observable, oldValue, newValue) -> {
            verificarYAutocompletar(newValue.trim());
        });
        configurarValidadoresEnTiempoReal();
    }

    private void configurarValidadoresEnTiempoReal() {
        // Monitoreo del Nombre
        txtNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                lblErrorNombre.setText("Nombre obligatorio.");
            } else if (newValue.trim().length() < 3) {
                lblErrorNombre.setText("Demasiado corto.");
            } else {
                lblErrorNombre.setText("");
            }
            validarFormularioCompleto();
        });

        // Monitoreo de la Dirección
        txtDireccion.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                lblErrorDireccion.setText("⚠️ Dirección obligatoria.");
            } else {
                lblErrorDireccion.setText("");
            }
            validarFormularioCompleto();
        });

        // Monitoreo del Teléfono
        txtTelefono.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                lblErrorTelefono.setText("Teléfono obligatorio.");
            } else if (!newValue.trim().matches("\\d+")) {
                lblErrorTelefono.setText("Solo números.");
            } else if (newValue.trim().length() < 7 || newValue.trim().length() > 10) {
                lblErrorTelefono.setText("Inválido (7-10 dígitos).");
            } else {
                lblErrorTelefono.setText("");
            }
            validarFormularioCompleto();
        });
    }

    private void validarFormularioCompleto() {
        boolean tieneErrores = !lblErrorNombre.getText().isEmpty()
                || !lblErrorDireccion.getText().isEmpty()
                || !lblErrorTelefono.getText().isEmpty();

        boolean camposVacios = txtNombre.getText().trim().isEmpty()
                || txtDireccion.getText().trim().isEmpty()
                || txtTelefono.getText().trim().isEmpty();

        // Deshabilita el botón si algo está mal o incompleto
        btnGuardar.setDisable(tieneErrores || camposVacios);
    }

    private void verificarYAutocompletar(String cedula) {
        try {
            ClienteService servicioCliente = GestorServicios.getInstance().obtenerServicioCliente();
            ClienteDTO clienteExistente = servicioCliente.buscarClientePorID(cedula);
            btnGuardar.setDisable(false);
            clienteModel.setNombre(clienteExistente.getNombre());
            clienteModel.setDireccion(clienteExistente.getDireccion());
            clienteModel.setTelefono(clienteExistente.getTelefono());
        } catch (Exception e) {
            // Manejo silencioso mientras el usuario escribe a medio camino
            btnGuardar.setDisable(true);
        }
    }

    @FXML
    private void handleGuardar(ActionEvent event) {
        try {
            ClienteDTO clienteDTO = ClienteMapper.modeloToDto(clienteModel);
            ClienteService servicioCliente = GestorServicios.getInstance().obtenerServicioCliente();

            // Guardamos los cambios invocando estrictamente a actualizar
            servicioCliente.modificarCliente(clienteDTO);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Cliente modificado correctamente.");

            regresarAListado();

        } catch (ValidacionException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Validación", e.getMessage());
        } catch (EntidadNoEncontradaException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "No Encontrado", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar: " + e.getMessage());
        }
    }

    public void setNavegador(NavegadorVistas navegador) {
        this.navegador = navegador;
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        regresarAListado();
    }

    private void regresarAListado() {
        if (navegador != null) {
            navegador.cambiarAPantalla("Clientes/ListaClientes/ListaClientesView.fxml", "Lista Clientes");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}