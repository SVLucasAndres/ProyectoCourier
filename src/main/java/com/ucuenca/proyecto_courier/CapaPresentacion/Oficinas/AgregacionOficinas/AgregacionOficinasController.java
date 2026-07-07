
package com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.AgregacionOficinas;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.OficinaDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.OficinaService;
import com.ucuenca.proyecto_courier.CapaPresentacion.NavegadorVistas;
import com.ucuenca.proyecto_courier.CapaPresentacion.GestorServicios;
import com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.OficinaMapper; // Mapeador idéntico al de Clientes
import com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.OficinaModel;  // Tu modelo reactivo de UI

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.UUID;

public class AgregacionOficinasController {

    @FXML private TextField txtIdOficina;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    @FXML private Label textoAlerta;
    @FXML private Button btnGuardar;

    private NavegadorVistas navegador;

    //Instanciamos el modelo
    private final OficinaModel oficinaModel = new OficinaModel();

    public void setNavegador(NavegadorVistas navegador) {
        this.navegador = navegador;
    }

    @FXML
    public void initialize() {
        // Vinculación Bidireccional estricta
        txtIdOficina.textProperty().bindBidirectional(oficinaModel.idOficinaProperty());
        txtNombre.textProperty().bindBidirectional(oficinaModel.nombreProperty());
        txtDireccion.textProperty().bindBidirectional(oficinaModel.direccionProperty());
        txtTelefono.textProperty().bindBidirectional(oficinaModel.telefonoProperty());

        txtIdOficina.setEditable(false);

        String idGenerado = "OFI-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        oficinaModel.setIdOficina(idGenerado);

        // Listeners reactivos atados a las propiedades del MODELO
        oficinaModel.nombreProperty().addListener((o, v, n) -> verificarValidaciones());
        oficinaModel.direccionProperty().addListener((o, v, n) -> verificarValidaciones());
        oficinaModel.telefonoProperty().addListener((o, v, n) -> verificarValidaciones());

        // Validación inicial
        verificarValidaciones();
    }

    private void verificarValidaciones() {
        String nombre = oficinaModel.getNombre() != null ? oficinaModel.getNombre().trim() : "";
        String direccion = oficinaModel.getDireccion() != null ? oficinaModel.getDireccion().trim() : "";
        String telefono = oficinaModel.getTelefono() != null ? oficinaModel.getTelefono().trim() : "";

        // Comprobar campos vacíos
        if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty()) {
            textoAlerta.setText("Llene todos los campos (*)");
            btnGuardar.setDisable(true);
            return;
        }

        // Validación de teléfono ecuatoriano / caracteres válidos
        if (!telefono.matches("[0-9\\s\\-]+")) {
            textoAlerta.setText("El teléfono no es válido.");
            btnGuardar.setDisable(true);
            return;
        }

        // Si todo está correcto
        textoAlerta.setText("");
        btnGuardar.setDisable(false);
    }

    @FXML
    private void handleGuardar(ActionEvent event) {
        try {
            // Mapeo A DTO
            OficinaDTO dto = OficinaMapper.modeloToDto(oficinaModel);

            // Despachamos a la Capa de Negocio
            OficinaService servicio = GestorServicios.getInstance().obtenerServicioOficina();
            servicio.crearOficina(dto);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "¡Oficina registrada correctamente!");

            handleCancelar(null); // Retornar de inmediato al listado

        } catch (com.ucuenca.proyecto_courier.CapaDominio.Excepciones.ValidacionException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Validación", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        if (navegador != null) {
            navegador.cambiarAPantalla("Oficinas/ListaOficinas/ListaOficinasView.fxml", "Gestión de Oficinas");
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