package com.ucuenca.proyecto_courier.CapaPresentacion.Configuracion;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.ConfiguracionDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ConfiguracionService; // Ajusta según tu interfaz real
import com.ucuenca.proyecto_courier.CapaPresentacion.GestorServicios;
import com.ucuenca.proyecto_courier.CapaPresentacion.NavegadorVistas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ConfiguracionController {

    @FXML private TextField txtIva;
    @FXML private Label lblErrorIva;

    // Tabla para visualizar los Rangos asociados de la lista
    @FXML private TableView<Object> tablaRangos; // Reemplaza Object por tu clase 'Rango' real si aplica
    @FXML private TableColumn<Object, String> colIdRango;
    @FXML private TableColumn<Object, Double> colDesde;
    @FXML private TableColumn<Object, Double> colHasta;
    @FXML private TableColumn<Object, Double> colPrecio;

    @FXML private Button btnGuardar;

    private NavegadorVistas navegador;
    private ConfiguracionService configuracionService;
    private String idConfiguracionActual;

    @FXML
    public void initialize() {
        // Enlazar columnas de la tabla de Rangos (ajusta los nombres de las propiedades de tu clase Rango)
        colIdRango.setCellValueFactory(new PropertyValueFactory<>("idRango"));
        colDesde.setCellValueFactory(new PropertyValueFactory<>("minimo"));
        colHasta.setCellValueFactory(new PropertyValueFactory<>("maximo"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("valor"));

        // Cargar datos iniciales desde el sistema
        try {
            // Asumiendo que obtienes el servicio global desde tu Gestor
            // configuracionService = GestorServicios.getInstance().obtenerServicioConfiguracion();

            cargarConfiguracionGlobal();
        } catch (Exception e) {
            System.err.println("Error al conectar con el servicio de configuración: " + e.getMessage());
        }

        // Validación en tiempo real para el campo de IVA
        txtIva.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                lblErrorIva.setText("⚠️ El campo IVA es obligatorio.");
                btnGuardar.setDisable(true);
            } else {
                try {
                    double valorIva = Double.parseDouble(newValue.trim());
                    if (valorIva < 0 || valorIva > 100) {
                        lblErrorIva.setText("⚠️ Debe estar entre 0% y 100%.");
                        btnGuardar.setDisable(true);
                    } else {
                        lblErrorIva.setText("");
                        btnGuardar.setDisable(false);
                    }
                } catch (NumberFormatException e) {
                    lblErrorIva.setText("⚠️ Ingrese un número decimal válido.");
                    btnGuardar.setDisable(true);
                }
            }
        });
    }

    private void cargarConfiguracionGlobal() {

        ConfiguracionDTO dto = configuracionService.obtenerConfiguracion();
        if (dto != null) {
            this.idConfiguracionActual = dto.getIdConfiguracion();
            txtIva.setText(String.valueOf(dto.getImpuestoIVA()));
            tablaRangos.getItems().setAll(dto.getRangos());
        }
    }

    @FXML
    private void handleGuardar(ActionEvent event) {
        try {
            ConfiguracionService configuracionService = GestorServicios.getInstance().obtenerServicioConfiguracion();
            double nuevoIva = Double.parseDouble(txtIva.getText().trim());

            configuracionService.(idConfiguracionActual, nuevoIva);

            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Configuración del sistema actualizada correctamente.", ButtonType.OK);
            alerta.setTitle("Éxito");
            alerta.setHeaderText(null);
            alerta.showAndWait();

        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR, "No se pudo guardar la configuración: " + e.getMessage(), ButtonType.OK);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.showAndWait();
        }
    }

    public void setNavegador(NavegadorVistas navegador) {
        this.navegador = navegador;
    }
}