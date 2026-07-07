package com.ucuenca.proyecto_courier.CapaPresentacion.Envios.ListaEnvios;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.EnvioService;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.NavegadorVistas;
import com.ucuenca.proyecto_courier.CapaPresentacion.Envios.ContextoEnvio;
import com.ucuenca.proyecto_courier.CapaPresentacion.Envios.EnvioMapper;
import com.ucuenca.proyecto_courier.CapaPresentacion.Envios.EnvioModel;
import com.ucuenca.proyecto_courier.CapaPresentacion.GestorServicios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class ListaEnviosController {
    @FXML private Button btnGenerarEnvio;
    @FXML private TableView<EnvioModel> tablaEnvios;
    @FXML private TableColumn<EnvioModel, String> colIdEnvio;
    @FXML private TableColumn<EnvioModel, String> colIdDestinatario;
    @FXML private TableColumn<EnvioModel, String> colPaquetes;
    @FXML private TableColumn<EnvioModel, String> colRapidez;
    @FXML private TableColumn<EnvioModel, String> colPago;
    @FXML private TableColumn<EnvioModel, Void> colAcciones;
    private final ObservableList<EnvioModel> listaModelos = FXCollections.observableArrayList();
    private NavegadorVistas navegador;

    @FXML
    public void initialize() {
        // Vinculamos las columnas con las propiedades de nuestro ClienteModel
        colIdEnvio.setCellValueFactory(cellData -> cellData.getValue().idEnvioProperty());
        colIdDestinatario.setCellValueFactory(cellData -> cellData.getValue().idDestinatarioProperty());
        colPaquetes.setCellValueFactory(cellData -> cellData.getValue().cantidadPaquetesProperty().asString());
        colRapidez.setCellValueFactory(cellData -> cellData.getValue().rapidezProperty());
        colPago.setCellValueFactory(cellData -> cellData.getValue().metodoPagoProperty());
        if (colAcciones == null) {
            System.err.println("colAcciones es null.");
            return;
        }

        colAcciones.setCellFactory(param -> new javafx.scene.control.TableCell<>() {
            private final javafx.scene.control.Button btnDetalle = new javafx.scene.control.Button("Ver Detalle");

            {
                btnDetalle.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand; -fx-background-radius: 3;");

                btnDetalle.setOnAction(event -> {
                    EnvioModel envioSeleccionado = getTableView().getItems().get(getIndex());

                    if (navegador != null) {
                        ContextoEnvio.setEnvio(envioSeleccionado);

                        navegador.cambiarAPantalla("Envios/DetalleEnvios/DetalleEnviosView.fxml", "Propiedades del Envio");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null); // Si la fila está vacía, no muestra nada
                } else {
                    setGraphic(btnDetalle); // Si tiene datos, incrusta el botón
                    setAlignment(javafx.geometry.Pos.CENTER); // Centra el botón en la celda
                }
            }
        });

        // Enlazamos la lista observable a la tabla visual
        tablaEnvios.setItems(listaModelos);

        // Cargamos los datos reales desde la capa de persistencia
        cargarDatosDesdeElServicio();
    }

    private void cargarDatosDesdeElServicio() {
        try {
            EnvioService envioService = GestorServicios.getInstance().obtenerServicioEnvio();

            // Pedimos los DTOs planos
            List<EnvioDTO> listaDtos = envioService.mostrarListaEnvios();

            // Limpiamos la lista visual por si tenía datos viejos
            listaModelos.clear();

            // Transformamos cada DTO a Modelo
            for (EnvioDTO dto : listaDtos) {
                EnvioModel modeloVisual = EnvioMapper.dtoToModelo(dto);
                listaModelos.add(modeloVisual);
            }

        } catch (IllegalStateException e) {
            System.err.println("Error de inicialización: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al recuperar los clientes de la persistencia.");
            e.printStackTrace();
        }
    }

    public void setNavegador(NavegadorVistas navegador) {
        this.navegador = navegador;
    }


    @FXML
    private void handleGenerarEnvio(ActionEvent event) {
        if (navegador != null) {
            navegador.cambiarAPantalla("Envios/GeneracionEnvios/GeneracionEnviosView.fxml", "Generar Envio");
        }
    }

}
