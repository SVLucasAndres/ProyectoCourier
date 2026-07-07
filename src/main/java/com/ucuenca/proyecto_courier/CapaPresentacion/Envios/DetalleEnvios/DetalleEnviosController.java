package com.ucuenca.proyecto_courier.CapaPresentacion.Envios.DetalleEnvios;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.EnvioService;
import com.ucuenca.proyecto_courier.CapaPresentacion.NavegadorVistas;
import com.ucuenca.proyecto_courier.CapaPresentacion.Envios.ContextoEnvio;
import com.ucuenca.proyecto_courier.CapaPresentacion.Envios.EnvioMapper;
import com.ucuenca.proyecto_courier.CapaPresentacion.Envios.EnvioModel;
import com.ucuenca.proyecto_courier.CapaPresentacion.GestorServicios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DetalleEnviosController {

    @FXML private Label lblIdEnvio;
    @FXML private Label lblRemitente;
    @FXML private Label lblDestinatario;
    @FXML private Label lblRapidez;
    @FXML private Label lblPago;
    @FXML private Label lblEstado;

    @FXML private TableView<PaqueteDTO> tablaPaquetesDetalle;
    @FXML private TableColumn<PaqueteDTO, String> colIdPaquete;
    @FXML private TableColumn<PaqueteDTO, String> colPeso;
    @FXML private TableColumn<PaqueteDTO, String> colValor;
    @FXML private TableColumn<PaqueteDTO, Boolean> colSeguro;

    @FXML private Button btnCancelarEnvio;

    private EnvioModel envioActual;
    private EnvioService envioService;
    private final ObservableList<PaqueteDTO> listaPaquetes = FXCollections.observableArrayList();

    private NavegadorVistas navegador;

    @FXML
    public void initialize() {
        envioService = GestorServicios.getInstance().obtenerServicioEnvio();
        envioActual = ContextoEnvio.getEnvio();

        colIdPaquete.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIdPaquete()));

        colPeso.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPeso() + " kg"));

        colValor.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty("$" + cellData.getValue().getValorContenido()));

        colSeguro.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleBooleanProperty(cellData.getValue().isTieneSeguro()));

        colSeguro.setCellFactory(column -> new TableCell<PaqueteDTO, Boolean>() {
            @Override
            protected void updateItem(Boolean tieneSeguro, boolean empty) {
                super.updateItem(tieneSeguro, empty);

                if (empty || tieneSeguro == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("");
                } else {
                    if (tieneSeguro) {
                        setText("✔");
                        setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold; -fx-alignment: CENTER; -fx-font-size: 14px;");
                    } else {
                        setText("✘");
                        setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-alignment: CENTER; -fx-font-size: 14px;");
                    }
                }
            }
        });

        // Enlazamos la lista observable a la tabla
        tablaPaquetesDetalle.setItems(listaPaquetes);



        if (envioActual != null) {
            mostrarDetallesDelEnvio();
        }
    }

    private void mostrarDetallesDelEnvio() {
        lblIdEnvio.setText(envioActual.getIdEnvio());
        lblRemitente.setText(envioActual.getIdRemitente());
        lblDestinatario.setText(envioActual.getIdDestinatario());
        lblRapidez.setText(envioActual.getRapidez());
        lblPago.setText(envioActual.getMetodoPago());

        if (envioActual.getEstadoEnvio() != null) {
            actualizarLabelEstado(envioActual.getEstadoEnvio());
        } else {
            actualizarLabelEstado("NULL");
        }

        // Cargar los paquetes hidratados del envío desde el backend
        try {
            EnvioDTO dtoCompleto = envioService.buscarEnvioPorID(envioActual.getIdEnvio());
            if (dtoCompleto != null && dtoCompleto.getListaPaquetes() != null) {
                listaPaquetes.setAll(dtoCompleto.getListaPaquetes());
            }
        } catch (Exception e) {
            System.err.println("Error al recuperar paquetes del envío: " + e.getMessage());
        }
    }

    private void actualizarLabelEstado(String estado) {
        lblEstado.setText(estado);
        if ("CANCELADO".equals(estado)) {
            lblEstado.setStyle("-fx-background-color: #fadbd8; -fx-text-fill: #c0392b; -fx-font-weight: bold;");
            btnCancelarEnvio.setDisable(true);
        } else {
            lblEstado.setStyle("-fx-background-color: #d4efdf; -fx-text-fill: #27ae60; -fx-font-weight: bold;");
        }
    }

    @FXML
    private void handleCancelarEnvio() {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que deseas cancelar este envío?", ButtonType.YES, ButtonType.NO);
        confirmacion.setTitle("Confirmar Cancelación");
        confirmacion.setHeaderText(null);

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                envioActual.setEstadoEnvio("CANCELADO");

                actualizarLabelEstado("CANCELADO");

                EnvioService servicioEnvio = GestorServicios.getInstance().obtenerServicioEnvio();
                servicioEnvio.cancelarEnvio(EnvioMapper.modeloToDto(envioActual));
            }
        });
    }

    public void setNavegador(NavegadorVistas navegador) {
        this.navegador = navegador;
    }

    @FXML
    private void handleRegresar() {
        if (navegador != null) {
            navegador.cambiarAPantalla("Envios/ListaEnvios/ListaEnviosView.fxml", "Listado de Envios");
        }
    }
}