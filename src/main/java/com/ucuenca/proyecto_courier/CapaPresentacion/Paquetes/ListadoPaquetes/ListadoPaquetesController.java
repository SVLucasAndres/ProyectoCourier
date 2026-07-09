package com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.ListadoPaquetes;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.PaqueteService;
import com.ucuenca.proyecto_courier.CapaPresentacion.ClienteActual;
import com.ucuenca.proyecto_courier.CapaPresentacion.NavegadorVistas;
import com.ucuenca.proyecto_courier.CapaPresentacion.GestorServicios;
import com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.ContextoPaquete;
import com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.PaqueteMapper;
import com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.PaqueteModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;

public class ListadoPaquetesController {

    // --- COMPONENTES VISTA ADMINISTRADOR ---
    @FXML private TableView<PaqueteModel> tablaPaquetes;
    @FXML private TableColumn<PaqueteModel, Boolean> colSeguro;
    @FXML private TableColumn<PaqueteModel, String> colIdPaquete;
    @FXML private TableColumn<PaqueteModel, String> colPeso;
    @FXML private TableColumn<PaqueteModel, String> colValor;
    @FXML private TableColumn<PaqueteModel, Void> colAcciones;

    // --- COMPONENTES VISTA CLIENTE ---
    @FXML private TableView<PaqueteModel> tablaEnviados;
    @FXML private TableColumn<PaqueteModel, String> colIdEnviado;
    @FXML private TableColumn<PaqueteModel, String> colValorEnviado;
    @FXML private TableColumn<PaqueteModel, Void> colAccionesEnviado;

    @FXML private TableView<PaqueteModel> tablaRecibidos;
    @FXML private TableColumn<PaqueteModel, String> colIdRecibido;
    @FXML private TableColumn<PaqueteModel, String> colValorRecibido;
    @FXML private TableColumn<PaqueteModel, Void> colAccionesRecibido;

    private NavegadorVistas navegador;

    @FXML
    public void initialize() {
        // ¿Se cargó la vista de Administrador?
        if (tablaPaquetes != null) {
            inicializarVistaAdmin();
        }
        // ¿Se cargó la vista de Cliente?
        else if (tablaEnviados != null && tablaRecibidos != null) {
            inicializarVistaCliente();
        }
    }

    private void inicializarVistaAdmin() {
        colSeguro.setCellValueFactory(cellData -> cellData.getValue().seguroProperty());
        colSeguro.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : (item ? "✔" : ""));
                setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold; -fx-alignment: CENTER;");
            }
        });

        colIdPaquete.setCellValueFactory(cellData -> cellData.getValue().idPaqueteProperty());
        colPeso.setCellValueFactory(cellData -> cellData.getValue().pesoProperty().asString());
        colValor.setCellValueFactory(cellData -> cellData.getValue().valorProperty().asString());

        configurarColumnaAcciones(colAcciones);

        cargarDatosAdmin();
    }

    private void inicializarVistaCliente() {
        // Configurar Tabla Enviados
        colIdEnviado.setCellValueFactory(cellData -> cellData.getValue().idPaqueteProperty());
        colValorEnviado.setCellValueFactory(cellData -> cellData.getValue().valorProperty().asString());
        configurarColumnaAcciones(colAccionesEnviado);

        // Configurar Tabla Recibidos
        colIdRecibido.setCellValueFactory(cellData -> cellData.getValue().idPaqueteProperty());
        colValorRecibido.setCellValueFactory(cellData -> cellData.getValue().valorProperty().asString());
        configurarColumnaAcciones(colAccionesRecibido);

        cargarDatosCliente();
    }

    private void configurarColumnaAcciones(TableColumn<PaqueteModel, Void> columna) {
        if (columna == null) return;

        columna.setCellFactory(param -> new TableCell<>() {
            private final Button btnDetalle = new Button("Ver Detalle");
            {
                btnDetalle.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand; -fx-background-radius: 3;");
                btnDetalle.setOnAction(event -> {
                    PaqueteModel paqueteSeleccionado = getTableView().getItems().get(getIndex());
                    if (navegador != null) {
                        ContextoPaquete.setEnvio(paqueteSeleccionado);
                        ContextoPaquete.setNavegador(navegador);
                        navegador.cambiarAPantalla("Paquetes/DetallePaquetes/DetallePaquetesView.fxml", "Propiedades del Paquete");
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnDetalle);
                setAlignment(Pos.CENTER);
            }
        });
    }

    private void cargarDatosAdmin() {
        try {
            PaqueteService paqueteService = GestorServicios.getInstance().obtenerServicioPaquete();
            ObservableList<PaqueteModel> listaAdmin = FXCollections.observableArrayList();
            for (PaqueteDTO dto : paqueteService.mostrarListaPaquetes()) {
                listaAdmin.add(PaqueteMapper.dtoToModelo(dto));
            }
            tablaPaquetes.setItems(listaAdmin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarDatosCliente() {
        try {
            PaqueteService paqueteService = GestorServicios.getInstance().obtenerServicioPaquete();
            String idCliente = ClienteActual.getClienteActual().getIdCliente();

            // Llenar Enviados
            ObservableList<PaqueteModel> enviados = FXCollections.observableArrayList();
            for (PaqueteDTO dto : paqueteService.obtenerPaquetesPorRemitente(idCliente)) {
                enviados.add(PaqueteMapper.dtoToModelo(dto));
            }
            tablaEnviados.setItems(enviados);

            // Llenar Recibidos
            ObservableList<PaqueteModel> recibidos = FXCollections.observableArrayList();
            for (PaqueteDTO dto : paqueteService.obtenerPaquetesPorDestinatario(idCliente)) {
                recibidos.add(PaqueteMapper.dtoToModelo(dto));
            }
            tablaRecibidos.setItems(recibidos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNavegador(NavegadorVistas navegador) {
        this.navegador = navegador;
    }

    @FXML
    private void handleCrearPaquete(ActionEvent event) {
        if (navegador != null) {
            navegador.cambiarAPantalla("Paquetes/GeneracionPaquetes/GeneracionPaquetesView.fxml", "Generar Paquetes");
        }
    }
}