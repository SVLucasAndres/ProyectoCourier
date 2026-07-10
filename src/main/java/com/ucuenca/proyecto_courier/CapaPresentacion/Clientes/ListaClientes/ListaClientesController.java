package com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ListaClientes;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ClienteService;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ClienteMapper;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ClienteModel;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ContextoCliente;
import com.ucuenca.proyecto_courier.CapaPresentacion.NavegadorVistas;
import com.ucuenca.proyecto_courier.GestorServicios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class ListaClientesController {
    @FXML private Button btnAgregarCliente;
    @FXML private TableView<ClienteModel> tablaClientes;
    @FXML private TableColumn<ClienteModel, String> colCedula;
    @FXML private TableColumn<ClienteModel, String> colNombre;
    @FXML private TableColumn<ClienteModel, String> colDireccion;
    @FXML private TableColumn<ClienteModel, String> colTelefono;
    @FXML private TableColumn<ClienteModel, Void> colAcciones;
    private final ObservableList<ClienteModel> listaModelos = FXCollections.observableArrayList();
    private NavegadorVistas navegador;

    @FXML
    public void initialize() {
        // Vinculamos las columnas con las propiedades de nuestro ClienteModel
        colCedula.setCellValueFactory(cellData -> cellData.getValue().idClienteProperty());
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
                    ClienteModel clienteSeleccionado = getTableView().getItems().get(getIndex());

                    if (navegador != null) {
                        ContextoCliente.setCliente(clienteSeleccionado);
                        ContextoCliente.setNavegador(navegador); //Guardamos el navegador actual

                        navegador.cambiarAPantalla("Clientes/PropiedadesCliente/PropiedadesClienteView.fxml", "Propiedades del Cliente");
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
        tablaClientes.setItems(listaModelos);

        // Cargamos los datos reales desde la capa de persistencia
        cargarDatosDesdeElServicio();
    }

    private void cargarDatosDesdeElServicio() {
        try {
            ClienteService clienteServicio = GestorServicios.getInstance().obtenerServicioCliente();

            // Pedimos los DTOs planos
            List<ClienteDTO> listaDtos = clienteServicio.mostrarListaClientes();

            // Limpiamos la lista visual por si tenía datos viejos
            listaModelos.clear();

            // Transformamos cada DTO a Modelo
            for (ClienteDTO dto : listaDtos) {
                ClienteModel modeloVisual = ClienteMapper.dtoToModelo(dto);
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
    private void handleAgregarCliente(ActionEvent event) {
        if (navegador != null) {
            navegador.cambiarAPantalla("Clientes/AgregacionClientes/AgregacionClientesView.fxml", "Agregar Cliente");
        }
    }


}