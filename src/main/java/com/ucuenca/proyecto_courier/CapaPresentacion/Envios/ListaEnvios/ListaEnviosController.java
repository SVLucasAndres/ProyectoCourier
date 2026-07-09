package com.ucuenca.proyecto_courier.CapaPresentacion.Envios.ListaEnvios;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ConfiguracionService;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.EnvioService;
import com.ucuenca.proyecto_courier.CapaPresentacion.ClienteActual;
import com.ucuenca.proyecto_courier.CapaPresentacion.NavegadorVistas;
import com.ucuenca.proyecto_courier.CapaPresentacion.Envios.ContextoEnvio;
import com.ucuenca.proyecto_courier.CapaPresentacion.Envios.EnvioMapper;
import com.ucuenca.proyecto_courier.CapaPresentacion.Envios.EnvioModel;
import com.ucuenca.proyecto_courier.CapaPresentacion.GestorServicios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ListaEnviosController {

    @FXML private Button btnGenerarEnvio;

    // --- COMPONENTES VISTA ADMINISTRADOR ---
    @FXML private TableView<EnvioModel> tablaEnvios;
    @FXML private TableColumn<EnvioModel, String> colIdEnvio;
    @FXML private TableColumn<EnvioModel, String> colIdDestinatario;
    @FXML private TableColumn<EnvioModel, String> colPaquetes;
    @FXML private TableColumn<EnvioModel, String> colRapidez;
    @FXML private TableColumn<EnvioModel, String> colPago;
    @FXML private TableColumn<EnvioModel, Void> colAcciones;

    // --- COMPONENTES VISTA CLIENTE ---
    @FXML private TableView<EnvioModel> tablaEnviosRealizados;
    @FXML private TableColumn<EnvioModel, String> colIdRealizado;
    @FXML private TableColumn<EnvioModel, String> colRapidezRealizado;
    @FXML private TableColumn<EnvioModel, Void> colAccionesRealizado;

    @FXML private TableView<EnvioModel> tablaEnviosRecibidos;
    @FXML private TableColumn<EnvioModel, String> colIdRecibido;
    @FXML private TableColumn<EnvioModel, String> colRapidezRecibido;
    @FXML private TableColumn<EnvioModel, Void> colAccionesRecibido;

    private NavegadorVistas navegador;

    private final ConfiguracionService servicioConfiguracion = GestorServicios.getInstance().obtenerServicioConfiguracion();

    @FXML
    public void initialize() {
        // ¿Estamos en la pantalla de Administrador?
        if (tablaEnvios != null) {
            inicializarVistaAdmin();
        }
        // ¿Estamos en la de Cliente?
        else if (tablaEnviosRealizados != null && tablaEnviosRecibidos != null) {
            inicializarVistaCliente();
        }
    }

    private void inicializarVistaAdmin() {
        colIdEnvio.setCellValueFactory(cellData -> cellData.getValue().idEnvioProperty());
        colIdDestinatario.setCellValueFactory(cellData -> cellData.getValue().idDestinatarioProperty());
        colPaquetes.setCellValueFactory(cellData -> cellData.getValue().cantidadPaquetesProperty().asString());
        colRapidez.setCellValueFactory(cellData -> cellData.getValue().rapidezProperty());
        colPago.setCellValueFactory(cellData -> cellData.getValue().metodoPagoProperty());

        configurarColumnaAcciones(colAcciones);
        cargarDatosAdmin();
    }

    private void inicializarVistaCliente() {
        // Enlazar columnas de Envíos Realizados (Enviados)
        colIdRealizado.setCellValueFactory(cellData -> cellData.getValue().idEnvioProperty());
        colRapidezRealizado.setCellValueFactory(cellData -> cellData.getValue().rapidezProperty());
        configurarColumnaAcciones(colAccionesRealizado);

        // Enlazar columnas de Envíos Recibidos
        colIdRecibido.setCellValueFactory(cellData -> cellData.getValue().idEnvioProperty());
        colRapidezRecibido.setCellValueFactory(cellData -> cellData.getValue().rapidezProperty());
        configurarColumnaAcciones(colAccionesRecibido);

        cargarDatosCliente();
    }

    private void configurarColumnaAcciones(TableColumn<EnvioModel, Void> columna) {
        if (columna == null) return;

        columna.setCellFactory(param -> new TableCell<>() {
            private final Button btnDetalle = new Button("Ver Detalle");
            {
                btnDetalle.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand; -fx-background-radius: 3;");
                btnDetalle.setOnAction(event -> {
                    EnvioModel envioSeleccionado = getTableView().getItems().get(getIndex());
                    if (navegador != null) {
                        ContextoEnvio.setEnvio(envioSeleccionado);
                        navegador.cambiarAPantalla("Envios/DetalleEnvios/DetalleEnviosView.fxml", "Propiedades del Envío");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnDetalle);
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    private void cargarDatosAdmin() {
        try {
            EnvioService envioService = GestorServicios.getInstance().obtenerServicioEnvio();
            ObservableList<EnvioModel> listaModelos = FXCollections.observableArrayList();

            for (EnvioDTO dto : envioService.mostrarListaEnvios(servicioConfiguracion.obtenerConfiguracion())) {
                listaModelos.add(EnvioMapper.dtoToModelo(dto));
            }
            tablaEnvios.setItems(listaModelos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarDatosCliente() {
        try {
            EnvioService envioService = GestorServicios.getInstance().obtenerServicioEnvio();
            String idLogueado = ClienteActual.getClienteActual().getIdCliente();

            ObservableList<EnvioModel> realizados = FXCollections.observableArrayList();
            ObservableList<EnvioModel> recibidos = FXCollections.observableArrayList();

            // Filtrar el listado general en base al rol que cumple el usuario logueado en cada envío
            for (EnvioDTO dto : envioService.mostrarListaEnvios(servicioConfiguracion.obtenerConfiguracion())) {
                EnvioModel modelo = EnvioMapper.dtoToModelo(dto);

                if (dto.getIdRemitente() != null && dto.getIdRemitente().equalsIgnoreCase(idLogueado)) {
                    realizados.add(modelo);
                }
                if (dto.getIdDestinatario() != null && dto.getIdDestinatario().equalsIgnoreCase(idLogueado)) {
                    recibidos.add(modelo);
                }
            }

            tablaEnviosRealizados.setItems(realizados);
            tablaEnviosRecibidos.setItems(recibidos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNavegador(NavegadorVistas navegador) {
        this.navegador = navegador;
    }

    @FXML
    private void handleGenerarEnvio(ActionEvent event) {
        if (navegador != null) {
            navegador.cambiarAPantalla("Envios/GeneracionEnvios/GeneracionEnviosView.fxml", "Generar Envío");
        }
    }
}