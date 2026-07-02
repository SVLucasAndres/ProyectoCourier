package com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.ListadoPaquetes;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.EnvioService;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.PaqueteService;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.NavegadorVistas;
import com.ucuenca.proyecto_courier.CapaPresentacion.Envios.ContextoEnvio;
import com.ucuenca.proyecto_courier.CapaPresentacion.Envios.EnvioMapper;
import com.ucuenca.proyecto_courier.CapaPresentacion.Envios.EnvioModel;
import com.ucuenca.proyecto_courier.CapaPresentacion.GestorServicios;
import com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.ContextoPaquete;
import com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.PaqueteMapper;
import com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.PaqueteModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class ListadoPaquetesController {

    @FXML
    private Button btnCrearPaquete;
    @FXML private TableView<PaqueteModel> tablaPaquetes;
    @FXML private TableColumn<PaqueteModel, Boolean> colSeguro;
    @FXML private TableColumn<PaqueteModel, String> colIdPaquete;
    @FXML private TableColumn<PaqueteModel, String> colPeso;
    @FXML private TableColumn<PaqueteModel, String> colValor;
    @FXML private TableColumn<PaqueteModel, Void> colAcciones;
    private final ObservableList<PaqueteModel> listaModelos = FXCollections.observableArrayList();
    private NavegadorVistas navegador;

    @FXML
    public void initialize() {
        // Vinculamos las columnas con las propiedades de nuestro PaqueteModel
        colSeguro.setCellValueFactory(cellData -> cellData.getValue().seguroProperty());

        colSeguro.setCellFactory(column -> new TableCell<PaqueteModel, Boolean>() {
            @Override
            protected void updateItem(Boolean tieneSeguro, boolean empty) {
                super.updateItem(tieneSeguro, empty);

                if (empty || tieneSeguro == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (tieneSeguro) {
                        setText("✔");
                        setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold; -fx-alignment: CENTER; -fx-font-size: 14px;");
                    } else {
                        setText("");
                        setStyle("");
                    }
                }
            }
        });

        colIdPaquete.setCellValueFactory(cellData -> cellData.getValue().idPaqueteProperty());
        colPeso.setCellValueFactory(cellData -> cellData.getValue().pesoProperty().asString());
        colValor.setCellValueFactory(cellData -> cellData.getValue().valorProperty().asString());
        if (colAcciones == null) {
            System.err.println("colAcciones es null.");
            return;
        }

        colAcciones.setCellFactory(param -> new javafx.scene.control.TableCell<>() {
            private final javafx.scene.control.Button btnDetalle = new javafx.scene.control.Button("Ver Detalle");

            {
                btnDetalle.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand; -fx-background-radius: 3;");

                btnDetalle.setOnAction(event -> {
                    PaqueteModel envioSeleccionado = getTableView().getItems().get(getIndex());

                    if (navegador != null) {
                        ContextoPaquete.setEnvio(envioSeleccionado);
                        ContextoPaquete.setNavegador(navegador); //Guardamos el navegador actual

                        navegador.cambiarAPantalla("Paquetes/DetallePaquetes/DetallePaquetesView.fxml", "Propiedades del Paquete");
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
        tablaPaquetes.setItems(listaModelos);

        // Cargamos los datos reales desde la capa de persistencia
        cargarDatosDesdeElServicio();
    }

    private void cargarDatosDesdeElServicio() {
        try {
            PaqueteService paqueteService = GestorServicios.getInstance().obtenerServicioPaquete();

            // Pedimos los DTOs planos
            List<PaqueteDTO> listaDtos = paqueteService.mostrarListaPaquetes();

            // Limpiamos la lista visual por si tenía datos viejos
            listaModelos.clear();

            // Transformamos cada DTO a Modelo
            for (PaqueteDTO dto : listaDtos) {
                PaqueteModel modeloVisual = PaqueteMapper.dtoToModelo(dto);
                listaModelos.add(modeloVisual);
            }

        } catch (IllegalStateException e) {
            System.err.println("Error de inicialización: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al recuperar los paquetes de la persistencia.");
            e.printStackTrace();
        }
    }

    public void setNavegador(NavegadorVistas navegador) {
        this.navegador = navegador;
    }


    @FXML
    private void handleCrearPaquete(ActionEvent event) {
        if (navegador != null) {
            navegador.cambiarAPantalla("Envios/GeneracionEnvios/GeneracionEnviosView.fxml", "Generar Envio");
        }
    }
}
