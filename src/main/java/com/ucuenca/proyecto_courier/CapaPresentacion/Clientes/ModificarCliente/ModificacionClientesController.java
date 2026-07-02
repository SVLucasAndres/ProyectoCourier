package com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ModificarCliente;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.ValidacionException;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.EntidadNoEncontradaException;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ClienteService;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ClienteMapper;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ClienteModel;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ContextoCliente;
import com.ucuenca.proyecto_courier.CapaPresentacion.GestorServicios;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.NavegadorVistas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ModificacionClientesController {

    @FXML private TextField txtCedula;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;

    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;

    private final ClienteModel clienteModel = new ClienteModel();

    @FXML
    public void initialize() {
        // Binding
        txtCedula.textProperty().bindBidirectional(clienteModel.idClienteProperty());
        txtNombre.textProperty().bindBidirectional(clienteModel.nombreProperty());
        txtDireccion.textProperty().bindBidirectional(clienteModel.direccionProperty());
        txtTelefono.textProperty().bindBidirectional(clienteModel.telefonoProperty());

        // Campos deshabilitados
        setCamposDeshabilitados(true);

        // Controla lo q el usuario escribe
        txtCedula.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                limpiarCamposAlternos();
                setCamposDeshabilitados(true);
                return;
            }

            // Buscamos en tiempo real si la cédula digitada ya existe
            verificarYAutocompletar(newValue.trim());
        });
    }

    private void verificarYAutocompletar(String cedula) {
        try {
            ClienteService servicioCliente = GestorServicios.getInstance().obtenerServicioCliente();
            ClienteDTO clienteExistente = servicioCliente.buscarClientePorID(cedula);

            if (clienteExistente != null) {
                // El cliente fue encontrado...
                clienteModel.setNombre(clienteExistente.getNombre());
                clienteModel.setDireccion(clienteExistente.getDireccion());
                clienteModel.setTelefono(clienteExistente.getTelefono());

                // ... habilitamos los campos
                setCamposDeshabilitados(false);
            } else {
                // Si cambia el texto y ya no coincide con nadie, volvemos a bloquear y limpiar
                limpiarCamposAlternos();
                setCamposDeshabilitados(true);
            }
        } catch (Exception e) {
            // Manejo silencioso mientras el usuario escribe a medio camino
            setCamposDeshabilitados(true);
        }
    }

    private void setCamposDeshabilitados(boolean deshabilitar) {
        txtNombre.setDisable(deshabilitar);
        txtDireccion.setDisable(deshabilitar);
        txtTelefono.setDisable(deshabilitar);
        btnGuardar.setDisable(deshabilitar); // Evita enviar datos vacíos o erróneos
    }

    private void limpiarCamposAlternos() {
        clienteModel.setNombre("");
        clienteModel.setDireccion("");
        clienteModel.setTelefono("");
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

    @FXML
    private void handleCancelar(ActionEvent event) {
        regresarAListado();
    }

    private void regresarAListado() {
        // Rescatamos el navegador
        NavegadorVistas nav = ContextoCliente.getNavegador();
        if (nav != null) {
            nav.cambiarAPantalla("Clientes/ListaClientes/ListaClientesView.fxml", "Lista Clientes");
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