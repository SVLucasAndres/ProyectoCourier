package com.ucuenca.proyecto_courier.CapaPresentacion.Envios.GeneracionEnvios;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.MetodoPago;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.TipoServicio;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ClienteService;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.EnvioService;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.PaqueteService;
import com.ucuenca.proyecto_courier.CapaPresentacion.GestorServicios;
import com.ucuenca.proyecto_courier.CapaPresentacion.NavegadorVistas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GeneracionEnviosController {

    @FXML private TextField txtIdEnvio;
    @FXML private ComboBox<String> cmbRemitente;
    @FXML private ComboBox<String> cmbDestinatario;
    @FXML private ComboBox<String> cmbRapidez;
    @FXML private ComboBox<String> cmbMetodoPago;
    @FXML private ListView<String> lstPaquetes;

    private NavegadorVistas navegador;

    @FXML private Label textoAlerta;
    @FXML private Button btnGuardar;

    @FXML
    public void initialize() {
        try {
            // Autogenerar ID único corto para el Envío
            String idAutogenerado = "ENV-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
            txtIdEnvio.setText(idAutogenerado);

            // Cargar ComboBoxes de Enums
            for (TipoServicio tipo : TipoServicio.values()) {
                cmbRapidez.getItems().add(tipo.name());
            }
            for (MetodoPago pago : MetodoPago.values()) {
                cmbMetodoPago.getItems().add(pago.name());
            }

            // Configurar el ListView para SELECCIÓN MÚLTIPLE
            lstPaquetes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            // Llenar los datos desde las listas del sistema
            cargarDatosDeServicios();
            configurarValidaciones();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarValidaciones() {
        // Ejecutamos validacion cada cambio
        Runnable verificarFormulario = () -> {

            //Validar Campos Vacíos
            if (cmbRemitente.getValue() == null || cmbDestinatario.getValue() == null ||
                    cmbRapidez.getValue() == null || cmbMetodoPago.getValue() == null) {

                textoAlerta.setText("Complete todos los campos");
                btnGuardar.setDisable(true);
                return;
            }

            // Validar que Remitente y Destinatario no sean la misma persona
            if (cmbRemitente.getValue().equalsIgnoreCase(cmbDestinatario.getValue())) {
                textoAlerta.setText("Remitente y Destinatario no deben ser igual");
                btnGuardar.setDisable(true);
                return;
            }

            // Validar Selección de Paquetes
            if (lstPaquetes.getSelectionModel().getSelectedItems().isEmpty()) {
                textoAlerta.setText("Seleccione al menos un paquete");
                btnGuardar.setDisable(true);
                return;
            }

            // No hay error y habilitamos btn
            textoAlerta.setText("");
            btnGuardar.setDisable(false);
        };

        // Cambios en los ComboBoxes
        cmbRemitente.valueProperty().addListener((obs, viejo, nuevo) -> verificarFormulario.run());
        cmbDestinatario.valueProperty().addListener((obs, viejo, nuevo) -> verificarFormulario.run());
        cmbRapidez.valueProperty().addListener((obs, viejo, nuevo) -> verificarFormulario.run());
        cmbMetodoPago.valueProperty().addListener((obs, viejo, nuevo) -> verificarFormulario.run());

        // Escuchar cambios en la selección del ListView de paquetes
        lstPaquetes.getSelectionModel().getSelectedItems().addListener((javafx.collections.ListChangeListener<String>) c -> {
            verificarFormulario.run();
        });

        // Ejecución inicial para que empiece bloqueado (ya que arranca vacío)
        verificarFormulario.run();
    }

    private void cargarDatosDeServicios() {
        try {
            // Llenar Clientes
            ClienteService clienteService = GestorServicios.getInstance().obtenerServicioCliente();
            for (ClienteDTO c : clienteService.mostrarListaClientes()) {
                // Metemos la cédula para cumplir el requisito de tu DTO
                cmbRemitente.getItems().add(c.getIdCliente() + "-" + c.getNombre());
                cmbDestinatario.getItems().add(c.getIdCliente() + "-" + c.getNombre());
            }

            // Llenar Paquetes
            PaqueteService paqueteService = GestorServicios.getInstance().obtenerServicioPaquete();
            for (PaqueteDTO p : paqueteService.mostrarPaquetesSinEnvio()) {
                // Mostramos el ID del paquete en la lista visual
                lstPaquetes.getItems().add(p.getIdPaquete());
            }

        } catch (Exception e) {
            System.err.println("Error al cargar catálogos en la generación de envíos.");
        }
    }

    @FXML
    private void handleGuardar(ActionEvent event) {
        try {
            // Validaciones básicas de interfaz
            if (cmbRemitente.getValue() == null || cmbDestinatario.getValue() == null ||
                    cmbRapidez.getValue() == null || cmbMetodoPago.getValue() == null ||
                    lstPaquetes.getSelectionModel().getSelectedItems().isEmpty()) {

                throw new Exception("Por favor, complete todos los campos obligatorios y seleccione al menos un paquete.");
            }

            // Construcción del EnvioDTO
            EnvioDTO nuevoEnvio = new EnvioDTO();
            nuevoEnvio.setIdEnvio(txtIdEnvio.getText());
            String idRemitenteFormateado = cmbRemitente.getValue().split("-")[0];
            String idDestinatarioFormateado = cmbDestinatario.getValue().split("-")[0];

            nuevoEnvio.setIdRemitente(idRemitenteFormateado);
            nuevoEnvio.setIdDestinatario(idDestinatarioFormateado);

            // Transformación de los Strings elegidos a Enums
            nuevoEnvio.setRapidez(TipoServicio.valueOf(cmbRapidez.getValue()));
            nuevoEnvio.setMetodoPago(MetodoPago.valueOf(cmbMetodoPago.getValue()));

            // Rescatamos las claves de los paquetes seleccionados por el usuario
            List<PaqueteDTO> paquetesAsociados = new ArrayList<>();
            PaqueteService paqueteService = GestorServicios.getInstance().obtenerServicioPaquete();

            for (String idPaquete : lstPaquetes.getSelectionModel().getSelectedItems()) {
                PaqueteDTO pDto = paqueteService.buscarPaquetePorID(idPaquete);
                if (pDto != null) {
                    paquetesAsociados.add(pDto);
                }
            }
            nuevoEnvio.setListaPaquetes(paquetesAsociados);

            // Enviamos el DTO al servicio de negocio para su inserción y cálculo de costo
            EnvioService envioService = GestorServicios.getInstance().obtenerServicioEnvio();
            envioService.realizarEnvio(nuevoEnvio);

            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Envío generado exitosamente con el código: " + nuevoEnvio.getIdEnvio());
            alerta.showAndWait();

            regresarAListado();

        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        regresarAListado();
    }

    private void regresarAListado() {
        if (navegador != null) {
            navegador.cambiarAPantalla("Envios/ListaEnvios/ListaEnviosView.fxml", "Lista Envíos");
        }
    }

    public void setNavegador(NavegadorVistas navegador) {
        this.navegador = navegador;
    }
}