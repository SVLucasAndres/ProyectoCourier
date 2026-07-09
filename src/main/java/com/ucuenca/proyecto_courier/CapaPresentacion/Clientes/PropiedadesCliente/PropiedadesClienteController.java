package com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.PropiedadesCliente;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ConfiguracionService;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.EnvioService;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ClienteMapper;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ClienteModel;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ContextoCliente;
import com.ucuenca.proyecto_courier.CapaPresentacion.GestorServicios;
import com.ucuenca.proyecto_courier.CapaPresentacion.NavegadorVistas;
import com.ucuenca.proyecto_courier.CapaPresentacion.Envios.EnvioModel;
import com.ucuenca.proyecto_courier.CapaPresentacion.Envios.EnvioMapper; // Importante para mapear seguro

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PropiedadesClienteController {

    @FXML private Label lblCedula;
    @FXML private Label lblNombre;
    @FXML private Label lblDireccion;
    @FXML private Label lblTelefono;

    @FXML private TableView<EnvioModel> tablaEnvios;
    @FXML private TableColumn<EnvioModel, String> colIdEnvio;
    @FXML private TableColumn<EnvioModel, String> colDestinatario;
    @FXML private TableColumn<EnvioModel, String> colRapidez;
    @FXML private TableColumn<EnvioModel, String> colPago;

    private final ObservableList<EnvioModel> listaEnvios = FXCollections.observableArrayList();
    private NavegadorVistas navegador;
    private ClienteModel modeloCliente;

    private final ConfiguracionService servicioConfiguracion = GestorServicios.getInstance().obtenerServicioConfiguracion();

    @FXML
    public void initialize() {
        // Vinculación de columnas usando las propiedades de tu EnvioModel
        colIdEnvio.setCellValueFactory(cellData -> cellData.getValue().idEnvioProperty());
        colDestinatario.setCellValueFactory(cellData -> cellData.getValue().idDestinatarioProperty());
        colRapidez.setCellValueFactory(cellData -> cellData.getValue().rapidezProperty());
        colPago.setCellValueFactory(cellData -> cellData.getValue().metodoPagoProperty());

        tablaEnvios.setItems(listaEnvios);

        // Recuperamos el cliente que se inyectó en el contexto al dar click en la lista anterior
        this.modeloCliente = ContextoCliente.getCliente();

        if (this.modeloCliente != null) {
            mostrarDatosEnPantalla(modeloCliente);
        }
    }

    private void mostrarDatosEnPantalla(ClienteModel cliente) {
        lblCedula.setText(cliente.getIdCliente());
        lblNombre.setText(cliente.getNombre()); // Concatenamos si aplica
        lblDireccion.setText(cliente.getDireccion());
        lblTelefono.setText(cliente.getTelefono());

        // Cargamos los envíos de este cliente específico usando su cédula/ID único
        cargarHistorialEnvios(cliente.getIdCliente());
    }

    public void cargarDatosCliente(ClienteModel cliente) {
        this.modeloCliente = cliente;
        if (lblCedula != null) {
            mostrarDatosEnPantalla(cliente);
        }
    }

    private void cargarHistorialEnvios(String idCliente) {
        try {
            EnvioService envioService = GestorServicios.getInstance().obtenerServicioEnvio();
            listaEnvios.clear();

            for (EnvioDTO dto : envioService.mostrarListaEnvios(servicioConfiguracion.obtenerConfiguracion())) {
                if (dto.getIdRemitente() != null && dto.getIdRemitente().equalsIgnoreCase(idCliente)) {

                    EnvioModel model = EnvioMapper.dtoToModelo(dto);
                    listaEnvios.add(model);
                }
            }

        } catch (Exception e) {
            System.err.println("Error al cargar el historial de envíos del cliente.");
            e.printStackTrace();
        }
    }

    public void setNavegador(NavegadorVistas navegador) {
        this.navegador = navegador;
    }

    @FXML
    private void handleVolver(ActionEvent event) {
        NavegadorVistas nav = ContextoCliente.getNavegador();
        if (nav != null) {
            nav.cambiarAPantalla("Clientes/ListaClientes/ListaClientesView.fxml", "Lista Clientes");
        } else if (this.navegador != null) {
            this.navegador.cambiarAPantalla("Clientes/ListaClientes/ListaClientesView.fxml", "Lista Clientes");
        } else {
            System.err.println("Error crítico: El navegador sigue siendo null.");
        }
    }

    @FXML
    private void handleEliminarCliente(ActionEvent event) {
        if (modeloCliente == null) return;

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Seguro que deseas eliminar al cliente " + modeloCliente.getNombre() + "?\nEsta acción no se puede deshacer.",
                ButtonType.YES, ButtonType.NO);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText(null);

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    // Llamamos al servicio correspondiente
                    GestorServicios.getInstance().obtenerServicioCliente().archivarCliente(ClienteMapper.modeloToDto(modeloCliente));

                    // Volvemos a la lista tras limpiar
                    handleVolver(event);
                } catch (Exception e) {
                    Alert error = new Alert(Alert.AlertType.ERROR, "No se pudo eliminar al cliente: " + e.getMessage(), ButtonType.OK);
                    error.showAndWait();
                }
            }
        });
    }

    @FXML
    private void handleModificarCliente(ActionEvent event) {
        if (navegador != null) {
            // Pasas a la pantalla donde tienes el formulario de registro/edición de clientes
            navegador.cambiarAPantalla("Clientes/ModificarCliente/ModificarClienteView.fxml", "Modificar Cliente");
        }
    }
}