package com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.DetalleOficinas;

import com.ucuenca.proyecto_courier.CapaDominio.interfaces.OficinaService;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.NavegadorVistas;
import com.ucuenca.proyecto_courier.CapaPresentacion.GestorServicios;
import com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.ContextoOficina;
import com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.OficinaModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.Optional;

public class DetallesOficinasController {

    @FXML private TextField txtIdOficina;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;

    @FXML private Button btnVolver;
    @FXML private Button btnEliminar;
    @FXML private Button btnModificar;

    private NavegadorVistas navegador;

    public void setNavegador(NavegadorVistas navegador) {
        this.navegador = navegador;
    }

    @FXML
    public void initialize() {
        OficinaModel oficina = ContextoOficina.getOficina();

        if (oficina != null) {
            txtIdOficina.setText(oficina.getIdOficina());
            txtNombre.setText(oficina.getNombre());
            txtTelefono.setText(oficina.getTelefono());
            txtDireccion.setText(oficina.getDireccion());
        }
    }

    @FXML
    private void handleModificar(ActionEvent event) {
        if (navegador != null) {
            // Te lleva a la vista de formulario de edición (reutilizando agregación o una específica)
            navegador.cambiarAPantalla("Oficinas/ModificacionOficinas/ModificacionOficinasView.fxml", "Editar Oficina");
        }
    }

    @FXML
    private void handleEliminar(ActionEvent event) {
        var oficina = ContextoOficina.getOficina();
        if (oficina == null) return;

        // Cuadro de diálogo de confirmación (Seguridad ante todo)
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminación");
        alert.setHeaderText("¿Está seguro de eliminar esta oficina?");
        alert.setContentText("Esta acción dará de baja la sucursal '" + oficina.getNombre() + "'.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Despachar la baja al servicio del dominio
                OficinaService service = GestorServicios.getInstance().obtenerServicioOficina();
                service.archivarOficina(service.buscarOficinaPorID(oficina.getIdOficina()));

                Alert exito = new Alert(Alert.AlertType.INFORMATION, "Oficina eliminada correctamente.");
                exito.showAndWait();

                handleVolver(null); // Regresar de inmediato al listado fresco
            } catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR, "No se pudo eliminar: " + e.getMessage());
                error.showAndWait();
            }
        }
    }

    @FXML
    private void handleVolver(ActionEvent event) {
        if (navegador != null) {
            navegador.cambiarAPantalla("Oficinas/ListaOficinas/ListaOficinasView.fxml", "Gestión de Oficinas");
        }
    }
}