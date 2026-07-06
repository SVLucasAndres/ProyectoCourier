package com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.ModificacionOficinas;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.OficinaDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.ValidacionException;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.EntidadNoEncontradaException;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.OficinaService;
import com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.OficinaMapper;
import com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.OficinaModel;
import com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.ContextoOficina;
import com.ucuenca.proyecto_courier.CapaPresentacion.GestorServicios;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.NavegadorVistas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ModificacionOficinasController {

    @FXML private TextField txtIdOficina; // Bloqueado en el FXML (disable="true")
    @FXML private TextField txtNombre;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;

    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;

    private final OficinaModel oficinaModel = new OficinaModel();

    private NavegadorVistas navigator;

    @FXML
    public void initialize() {
        // 1. Vinculación bidireccional entre la UI y el Modelo de presentación
        txtIdOficina.textProperty().bindBidirectional(oficinaModel.idOficinaProperty());
        txtNombre.textProperty().bindBidirectional(oficinaModel.nombreProperty());
        txtDireccion.textProperty().bindBidirectional(oficinaModel.direccionProperty());
        txtTelefono.textProperty().bindBidirectional(oficinaModel.telefonoProperty());

        // 2. Cargamos la oficina seleccionada directamente desde el Contexto
        cargarOficinaDesdeContexto();
    }

    private void cargarOficinaDesdeContexto() {
        try {
            // Recuperamos la oficina que el usuario seleccionó en la tabla de la pantalla anterior
            OficinaDTO oficinaSeleccionada = OficinaMapper.modeloToDto(ContextoOficina.getOficina());

            if (oficinaSeleccionada != null) {
                // Poblamos el modelo (gracias al bind, se reflejará en los campos automáticamente)
                oficinaModel.setIdOficina(oficinaSeleccionada.getIdOficina());
                oficinaModel.setNombre(oficinaSeleccionada.getNombre());
                oficinaModel.setDireccion(oficinaSeleccionada.getDireccion());
                oficinaModel.setTelefono(oficinaSeleccionada.getTelefono());

                // Habilitamos los campos alternos por si estaban bloqueados
                setCamposEdicionDeshabilitados(false);
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se seleccionó ninguna oficina para modificar.");
                setCamposEdicionDeshabilitados(true);
            }
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al cargar los datos de la oficina: " + e.getMessage());
            setCamposEdicionDeshabilitados(true);
        }
    }

    private void setCamposEdicionDeshabilitados(boolean deshabilitar) {
        txtNombre.setDisable(deshabilitar);
        txtDireccion.setDisable(deshabilitar);
        txtTelefono.setDisable(deshabilitar);
        btnGuardar.setDisable(deshabilitar);
    }

    @FXML
    private void handleActualizar(ActionEvent event) {
        try {
            OficinaDTO oficinaDTO = OficinaMapper.modeloToDto(oficinaModel);
            oficinaDTO.setActive(true);
            OficinaService servicioOficina = GestorServicios.getInstance().obtenerServicioOficina();

            // Enviamos a la Capa de Negocio
            servicioOficina.modificarOficina(oficinaDTO);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Oficina modificada correctamente.");

            regresarAListado();

        } catch (ValidacionException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Validación", e.getMessage());
        } catch (EntidadNoEncontradaException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "No Encontrado", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar la oficina: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        regresarAListado();
    }

    public void setNavegador(NavegadorVistas navigator){this.navigator = navigator;}

    private void regresarAListado() {
        if (navigator != null) {
            navigator.cambiarAPantalla("Oficinas/ListaOficinas/ListaOficinasView.fxml", "Lista Oficinas");
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