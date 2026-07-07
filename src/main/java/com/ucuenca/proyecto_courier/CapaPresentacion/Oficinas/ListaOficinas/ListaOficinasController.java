package com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.ListaOficinas;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.OficinaDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.OficinaService;
import com.ucuenca.proyecto_courier.CapaPresentacion.NavegadorVistas;
import com.ucuenca.proyecto_courier.CapaPresentacion.GestorServicios;
import com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.ContextoOficina;
import com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.OficinaMapper;
import com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.OficinaModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class ListaOficinasController {

    @FXML private TextField txtBuscar;
    @FXML private Button btnAgregar;

    // Tabla y columnas
    @FXML private TableView<OficinaModel> tblOficinas;
    @FXML private TableColumn<OficinaModel, String> colId;
    @FXML private TableColumn<OficinaModel, String> colNombre;
    @FXML private TableColumn<OficinaModel, String> colDireccion;
    @FXML private TableColumn<OficinaModel, String> colTelefono;

    @FXML private TableColumn<OficinaModel, Void> colAcciones;
    private final ObservableList<OficinaModel> listaModelos = FXCollections.observableArrayList();

    private NavegadorVistas navegador;

    /**
     * Inyección del navegador
     */
    public void setNavegador(NavegadorVistas navegador) {
        this.navegador = navegador;
    }

    @FXML
    public void initialize() {
        // Vinculamos las columnas con las propiedades de nuestro ClienteModel
        colId.setCellValueFactory(cellData -> cellData.getValue().idOficinaProperty());
        colNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colDireccion.setCellValueFactory(cellData -> cellData.getValue().direccionProperty());
        colTelefono.setCellValueFactory(cellData -> cellData.getValue().telefonoProperty());
        if (colAcciones == null) {
            System.err.println("colAcciones es null.");
            return;
        }

        colAcciones.setCellFactory(param -> new javafx.scene.control.TableCell<>() {
            private final javafx.scene.control.Button btnDetalle = new javafx.scene.control.Button("Ver Detalle");

            {
                btnDetalle.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand; -fx-background-radius: 3;");

                btnDetalle.setOnAction(event -> {
                    OficinaModel oficinaSeleccionada = getTableView().getItems().get(getIndex());

                    if (navegador != null) {
                        ContextoOficina.setOficina(oficinaSeleccionada);
                        ContextoOficina.setNavegador(navegador); //Guardamos el navegador actual

                        navegador.cambiarAPantalla("Oficinas/DetalleOficinas/DetallesOficinasView.fxml", "Propiedades de la oficina");
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
        tblOficinas.setItems(listaModelos);

        // Cargamos los datos reales desde la capa de persistencia
        cargarDatosDesdeElServicio();
    }

    private void cargarDatosDesdeElServicio() {
        try {
            OficinaService envioService = GestorServicios.getInstance().obtenerServicioOficina();

            // Pedimos los DTOs planos
            List<OficinaDTO> listaDtos = envioService.mostrarListaOficinas();

            // Limpiamos la lista visual por si tenía datos viejos
            listaModelos.clear();

            // Transformamos cada DTO a Modelo
            for (OficinaDTO dto : listaDtos) {
                OficinaModel modeloVisual = OficinaMapper.dtoToModelo(dto);
                listaModelos.add(modeloVisual);
            }

        } catch (IllegalStateException e) {
            System.err.println("Error de inicialización: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al recuperar las oficinas de la persistencia.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAgregarOficina(ActionEvent event) {
        if (this.navegador != null) {
            // Ventana de añadir oficina
            navegador.cambiarAPantalla("Oficinas/AgregacionOficinas/AgregacionOficinasView.fxml", "Registrar Nueva Oficina");
        } else {
            System.err.println("Error: El navegador de vistas no fue inyectado.");
        }
    }
}