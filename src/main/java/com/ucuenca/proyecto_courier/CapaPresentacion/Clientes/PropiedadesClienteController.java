package com.ucuenca.proyecto_courier.CapaPresentacion.Clientes;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteEnviosDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ClienteService;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.EnvioService;
import com.ucuenca.proyecto_courier.CapaPresentacion.GestorServicios;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.NavegadorVistas;
import com.ucuenca.proyecto_courier.CapaPresentacion.Envios.EnvioModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

public class PropiedadesClienteController {
    // Dentro de DetalleClienteController.java

    @FXML private Label lblCedula;
    @FXML private Label lblNombre;
    @FXML private Label lblDireccion;
    @FXML private Label lblTelefono;

    @FXML
    private TableView<EnvioModel> tablaEnvios;
    @FXML private TableColumn<EnvioModel, String> colIdEnvio;
    @FXML private TableColumn<EnvioModel, String> colDestinatario;
    @FXML private TableColumn<EnvioModel, String> colRapidez;
    @FXML private TableColumn<EnvioModel, String> colPago;

    private final ObservableList<ObservableList> listaEnviosModel = FXCollections.observableArrayList();
    private final ObservableList<EnvioModel> listaEnvios = FXCollections.observableArrayList();
    private NavegadorVistas navegador;

    private ClienteModel modeloCliente;

    @FXML
    public void initialize() {
        colIdEnvio.setCellValueFactory(cellData -> cellData.getValue().idEnvioProperty());
        colDestinatario.setCellValueFactory(cellData -> cellData.getValue().idDestinatarioProperty());
        colRapidez.setCellValueFactory(cellData -> cellData.getValue().rapidezProperty());
        colPago.setCellValueFactory(cellData -> cellData.getValue().metodoPagoProperty());

        tablaEnvios.setItems(listaEnvios);

        this.modeloCliente = ContextoCliente.getCliente();

        if(this.modeloCliente != null){
            mostrarDatosEnPantalla(modeloCliente);
        }
    }

    private void mostrarDatosEnPantalla(ClienteModel cliente) {
        lblCedula.setText(cliente.getIdCliente());
        lblNombre.setText(cliente.getNombre());
        lblDireccion.setText(cliente.getDireccion());
        lblTelefono.setText(cliente.getTelefono());

        // Buscamos y cargamos los envíos de este cliente específico
        cargarHistorialEnvios(cliente.getIdCliente());
    }

    public void cargarDatosCliente(ClienteModel cliente) {
        this.modeloCliente = cliente;

        // Por si acaso el controlador ya se inicializó antes, forzamos la pintada
        if (lblCedula != null) {
            mostrarDatosEnPantalla(cliente);
        }
    }

    private void cargarHistorialEnvios(String idCliente) {
        try {
            EnvioService envioService = GestorServicios.getInstance().obtenerServicioEnvio();

            // Recuperamos los DTOs desde el servicio
            List<EnvioDTO> enviosAsociadosACliente = new ArrayList<>();
            for (EnvioDTO e : envioService.mostrarListaEnvios()) {
                if (e.getIdRemitente() != null && e.getIdRemitente().equalsIgnoreCase(idCliente)) {
                    enviosAsociadosACliente.add(e);
                }
            }

            listaEnvios.clear();

            for (EnvioDTO dto : enviosAsociadosACliente) {
                EnvioModel model = new EnvioModel();

                model.setIdEnvio(dto.getIdEnvio());

                model.setIdDestinatario(dto.getIdDestinatario());

                // Contamos los elementos de la lista usando el getter que añadimos
                int nroPaquetes = (dto.getListaPaquetes() != null) ? dto.getListaPaquetes().size() : 0;
                model.setCantidadPaquetes(nroPaquetes);

                // Mapeamos los Enums a String para la vista
                model.setRapidez(dto.getRapidez() != null ? dto.getRapidez().toString() : "");
                model.setMetodoPago(dto.getMetodoPago() != null ? dto.getMetodoPago().toString() : "");

                listaEnvios.add(model);
            }

        } catch (Exception e) {
            System.err.println("Error al cargar el historial de envíos desde EnvioDTO.");
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
            nav.cambiarAPantalla("Clientes/ListaClientesView.fxml", "Lista Clientes");
        } else {
            System.out.println("Error crítico: El navegador sigue siendo null en el contexto.");
        }
    }
}
