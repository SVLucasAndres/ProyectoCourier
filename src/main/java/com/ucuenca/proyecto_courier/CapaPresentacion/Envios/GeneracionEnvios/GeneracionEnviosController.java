package com.ucuenca.proyecto_courier.CapaPresentacion.Envios.GeneracionEnvios;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.MetodoPago;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.TipoServicio;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ClienteService;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.EnvioService;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.PaqueteService;
import com.ucuenca.proyecto_courier.CapaPresentacion.ClienteActual;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ClienteMapper;
import com.ucuenca.proyecto_courier.CapaPresentacion.GestorServicios;
import com.ucuenca.proyecto_courier.CapaPresentacion.NavegadorVistas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GeneracionEnviosController {

    @FXML private TextField txtIdEnvio;
    @FXML private ComboBox<String> cmbRemitente;
    @FXML private VBox vboxDestinatario; // Capturamos el contenedor del FXML
    @FXML private ComboBox<String> cmbDestinatario;
    @FXML private ComboBox<String> cmbRapidez;
    @FXML private ComboBox<String> cmbMetodoPago;
    @FXML private ListView<String> lstPaquetes;
    @FXML private Label textoAlerta;
    @FXML private Button btnGuardar;

    // Elementos dinámicos exclusivos para la vista del Cliente
    private TextField txtCedulaDestinatario;
    private Label lblNombreDestinatarioConfirmado;
    private String idDestinatarioValidado = "";

    private NavegadorVistas navegador;

    @FXML
    public void initialize() {
        try {
            String idAutogenerado = "ENV-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
            txtIdEnvio.setText(idAutogenerado);

            for (TipoServicio tipo : TipoServicio.values()) {
                cmbRapidez.getItems().add(tipo.name());
            }
            for (MetodoPago pago : MetodoPago.values()) {
                cmbMetodoPago.getItems().add(pago.name());
            }

            lstPaquetes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            // Determinar comportamiento según Rol
            if (!ClienteActual.isIsAdmin()) {
                reconfigurarCamposParaCliente();
            } else {
                cargarDatosDeServiciosAdmin();
            }

            configurarValidaciones();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reconfigurarCamposParaCliente() {
        // 1. Forzar Remitente como el Cliente Actual y bloquearlo
        ClienteDTO logueado = ClienteMapper.modeloToDto(ClienteActual.getClienteActual());
        cmbRemitente.getItems().add(logueado.getIdCliente() + "-" + logueado.getNombre());
        cmbRemitente.getSelectionModel().select(0);
        cmbRemitente.setDisable(true);

        // 2. Destruir visualmente el ComboBox de destinatarios por Privacidad
        vboxDestinatario.getChildren().remove(cmbDestinatario);

        // 3. Crear el buscador compacto (TextField + Botón)
        txtCedulaDestinatario = new TextField();
        txtCedulaDestinatario.setPromptText("Ingrese Cédula del destinatario...");
        txtCedulaDestinatario.setPrefHeight(35.0);
        txtCedulaDestinatario.setPrefWidth(190.0);

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setPrefHeight(35.0);
        btnBuscar.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-cursor: hand; -fx-background-radius: 5;");

        lblNombreDestinatarioConfirmado = new Label("Destinatario no validado");
        lblNombreDestinatarioConfirmado.setStyle("-fx-text-fill: #7f8c8d; -fx-font-style: italic;");

        HBox buscadorContenedor = new HBox(10, txtCedulaDestinatario, btnBuscar);

        // Inyectamos los nuevos controles dentro del contenedor del FXML
        vboxDestinatario.getChildren().addAll(buscadorContenedor, lblNombreDestinatarioConfirmado);

        // Lógica de búsqueda exacta por Cédula
        btnBuscar.setOnAction(e -> ejecutarBusquedaDestinatario());

        // Llenar solo los paquetes globales que están libres en el sistema
        try {
            PaqueteService paqueteService = GestorServicios.getInstance().obtenerServicioPaquete();
            for (PaqueteDTO p : paqueteService.mostrarPaquetesSinEnvio()) {
                lstPaquetes.getItems().add(p.getIdPaquete());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void ejecutarBusquedaDestinatario() {
        String cedulaBuscada = txtCedulaDestinatario.getText().trim();
        if (cedulaBuscada.isEmpty()) {
            lblNombreDestinatarioConfirmado.setText("Ingrese un número de cédula.");
            lblNombreDestinatarioConfirmado.setStyle("-fx-text-fill: #e74c3c;");
            idDestinatarioValidado = "";
            return;
        }

        try {
            ClienteService clienteService = GestorServicios.getInstance().obtenerServicioCliente();
            ClienteDTO encontrado = clienteService.buscarClientePorID(cedulaBuscada);

            if (encontrado != null) {
                if (encontrado.getIdCliente().equals(ClienteActual.getClienteActual().getIdCliente())) {
                    lblNombreDestinatarioConfirmado.setText("❌ No puedes enviarte a ti mismo.");
                    lblNombreDestinatarioConfirmado.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                    idDestinatarioValidado = "";
                } else {
                    idDestinatarioValidado = encontrado.getIdCliente();
                    lblNombreDestinatarioConfirmado.setText("✔ Destinatario: " + encontrado.getNombre());
                    lblNombreDestinatarioConfirmado.setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");
                    verificarFormulario();
                }
            }
        } catch (Exception ex) {
            lblNombreDestinatarioConfirmado.setText("❌ Usuario no registrado en el sistema.");
            lblNombreDestinatarioConfirmado.setStyle("-fx-text-fill: #e74c3c;");
            idDestinatarioValidado = "";
        }
    }

    private void configurarValidaciones() {
        cmbRemitente.valueProperty().addListener((obs, v, n) -> verificarFormulario());
        cmbRapidez.valueProperty().addListener((obs, v, n) -> verificarFormulario());
        cmbMetodoPago.valueProperty().addListener((obs, v, n) -> verificarFormulario());

        lstPaquetes.getSelectionModel().getSelectedItems()
                .addListener((javafx.collections.ListChangeListener<String>) c -> verificarFormulario());

        if (ClienteActual.isIsAdmin()) {
            cmbDestinatario.valueProperty().addListener((obs, v, n) -> verificarFormulario());
        } else {
            txtCedulaDestinatario.textProperty().addListener((obs, v, n) -> {
                idDestinatarioValidado = "";
                lblNombreDestinatarioConfirmado.setText("Cédula modificada. Busque de nuevo.");
                lblNombreDestinatarioConfirmado.setStyle("-fx-text-fill: #f39c12;");
                verificarFormulario();
            });
        }

        verificarFormulario();
    }

    private void cargarDatosDeServiciosAdmin() {
        try {
            ClienteService clienteService = GestorServicios.getInstance().obtenerServicioCliente();
            for (ClienteDTO c : clienteService.mostrarListaClientes()) {
                cmbRemitente.getItems().add(c.getIdCliente() + "-" + c.getNombre());
                cmbDestinatario.getItems().add(c.getIdCliente() + "-" + c.getNombre());
            }

            PaqueteService paqueteService = GestorServicios.getInstance().obtenerServicioPaquete();
            for (PaqueteDTO p : paqueteService.mostrarPaquetesSinEnvio()) {
                lstPaquetes.getItems().add(p.getIdPaquete());
            }
        } catch (Exception e) {
            System.err.println("Error al cargar catálogos en modo Admin.");
        }
    }

    @FXML
    private void handleGuardar(ActionEvent event) {
        try {
            EnvioDTO nuevoEnvio = new EnvioDTO();
            nuevoEnvio.setIdEnvio(txtIdEnvio.getText());

            String idRemitenteFormateado = cmbRemitente.getValue().split("-")[0];
            nuevoEnvio.setIdRemitente(idRemitenteFormateado);



            // Rescate del destinatario condicionado al Rol
            if (ClienteActual.isIsAdmin()) {
                String idDestinatarioFormateado = cmbDestinatario.getValue().split("-")[0];
                nuevoEnvio.setIdDestinatario(idDestinatarioFormateado);
            } else {
                nuevoEnvio.setIdDestinatario(idDestinatarioValidado);
            }

            nuevoEnvio.setRapidez(TipoServicio.valueOf(cmbRapidez.getValue()));
            nuevoEnvio.setMetodoPago(MetodoPago.valueOf(cmbMetodoPago.getValue()));

            List<PaqueteDTO> paquetesAsociados = new ArrayList<>();
            PaqueteService paqueteService = GestorServicios.getInstance().obtenerServicioPaquete();

            for (String idPaquete : lstPaquetes.getSelectionModel().getSelectedItems()) {
                PaqueteDTO pDto = paqueteService.buscarPaquetePorID(idPaquete);
                if (pDto != null) {
                    paquetesAsociados.add(pDto);
                }
            }

            nuevoEnvio.setListaPaquetes(paquetesAsociados);



            EnvioService envioService = GestorServicios.getInstance().obtenerServicioEnvio();
            envioService.realizarEnvio(nuevoEnvio,GestorServicios.getInstance().obtenerServicioConfiguracion().obtenerConfiguracion());

            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Envío generado con éxito. Código: " + nuevoEnvio.getIdEnvio());
            alerta.showAndWait();

            regresarAListado();

        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML private void handleCancelar(ActionEvent event) { regresarAListado(); }

    private void regresarAListado() {
        if (navegador != null) {
            if(ClienteActual.isIsAdmin())
                navegador.cambiarAPantalla("Envios/ListaEnvios/ListaEnviosAdminView.fxml", "Lista de Paquetes");
            else
                navegador.cambiarAPantalla("Envios/ListaEnvios/ListaEnviosUserView.fxml", "Mis Paquetes");
        }
    }

    public void setNavegador(NavegadorVistas navegador) { this.navegador = navegador; }

    private void verificarFormulario() {
        if (cmbRemitente.getValue() == null || cmbRapidez.getValue() == null || cmbMetodoPago.getValue() == null) {
            textoAlerta.setText("Complete todos los campos");
            btnGuardar.setDisable(true);
            return;
        }

        if (ClienteActual.isIsAdmin()) {
            if (cmbDestinatario.getValue() == null) {
                textoAlerta.setText("Complete todos los campos");
                btnGuardar.setDisable(true);
                return;
            }
            if (cmbRemitente.getValue().equalsIgnoreCase(cmbDestinatario.getValue())) {
                textoAlerta.setText("Remitente y Destinatario no deben ser iguales");
                btnGuardar.setDisable(true);
                return;
            }
        } else {
            if (idDestinatarioValidado.isEmpty()) {
                textoAlerta.setText("Debe buscar y validar un destinatario registrado");
                btnGuardar.setDisable(true);
                return;
            }
        }

        if (lstPaquetes.getSelectionModel().getSelectedItems().isEmpty()) {
            textoAlerta.setText("Seleccione al menos un paquete");
            btnGuardar.setDisable(true);
            return;
        }

        textoAlerta.setText("");
        btnGuardar.setDisable(false);
    }
}