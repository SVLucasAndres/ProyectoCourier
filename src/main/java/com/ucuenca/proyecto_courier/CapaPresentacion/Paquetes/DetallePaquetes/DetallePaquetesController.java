package com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.DetallePaquetes;

import com.ucuenca.proyecto_courier.CapaDominio.Caja;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.OficinaDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.OperacionInvalidaException;
import com.ucuenca.proyecto_courier.CapaDominio.Sobre;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.PaqueteService;
import com.ucuenca.proyecto_courier.CapaPresentacion.*;
import com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.List;

public class DetallePaquetesController {
    @FXML private TextField txtIdPaquete, txtPeso, txtValor, txtTamano, txtAlto, txtAncho, txtLargo;
    @FXML private CheckBox chkSeguro;
    @FXML private ListView<String> lstRutaOficinas;
    @FXML private ComboBox<String> cmbOficinas;
    @FXML private HBox hboxAccionesAdmin;
    @FXML private ComboBox<String> cmbNuevaOficina;

    @FXML private VBox vboxDimensiones;
    @FXML private VBox vboxTamano;

    @FXML private TextField txtDimensiones;
    private final ObservableList<String> rutaObservable = FXCollections.observableArrayList();
    private PaqueteService paqueteService;
    private PaqueteModel envio;

    private NavegadorVistas navegador;

    @FXML
    public void initialize() {
        paqueteService = GestorServicios.getInstance().obtenerServicioPaquete();
        lstRutaOficinas.setItems(rutaObservable);

        if (!ClienteActual.isIsAdmin()) {
            hboxAccionesAdmin.setVisible(false);
            hboxAccionesAdmin.setManaged(false);
        } else {
            cmbOficinas.getItems().addAll(
                    GestorServicios.getInstance().obtenerServicioOficina().mostrarListaOficinas()
                            .stream().map(OficinaDTO::getNombre).toList()
            );
        }
        cmbNuevaOficina.getItems().addAll(
                GestorServicios.getInstance().obtenerServicioOficina().mostrarListaOficinas()
                        .stream().map(OficinaDTO::getNombre).toList()
        );
        mostrarDetallePaquete();
    }

    @FXML
    private void handleAgregarOficina(ActionEvent event) {
        String nombreOficina = cmbNuevaOficina.getValue();
        if (nombreOficina == null) return;

        if (!rutaObservable.isEmpty()) {
            String ultimaParada = rutaObservable.get(rutaObservable.size() - 1);

            if (ultimaParada.contains(nombreOficina)) {
                mostrarMensajeAlerta("Validación",
                        "No se puede agregar la misma oficina consecutivamente.",
                        Alert.AlertType.WARNING);
                return;
            }
        }

        try {
            paqueteService.agregarPuntoRuta(envio.getIdPaquete(), nombreOficina);
            actualizarVistaRuta();
            cmbNuevaOficina.getSelectionModel().clearSelection(); // Limpiamos el combo tras éxito
        } catch (Exception e) {
            mostrarMensajeAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarDetallePaquete() {
        envio = ContextoPaquete.getEnvio();
        if (envio != null) {
            txtIdPaquete.setText(envio.getIdPaquete());
            chkSeguro.setSelected(envio.getSeguro());
            txtPeso.setText(envio.getPeso().toString());
            txtValor.setText(envio.getValor().toString());
            if (envio instanceof CajaModel) {
                CajaModel caja = (CajaModel) envio;
                vboxDimensiones.setVisible(true);
                vboxTamano.setVisible(false);
                txtDimensiones.setText(String.format("%.1f x %.1f x %.1f cm",
                        caja.getAlto(), caja.getAncho(), caja.getLargo()));
            } else if (envio instanceof SobreModel) {
                SobreModel sobre = (SobreModel) envio;
                vboxDimensiones.setVisible(false);
                vboxTamano.setVisible(true);
                txtTamano.setText(sobre.getTamano());
            }
            actualizarVistaRuta();
        }
    }

    private void actualizarVistaRuta() {
        // El servicio calcula "En espera", "Recibido" o "Despachado"
        rutaObservable.setAll(paqueteService.obtenerTextosRutaConEstados(envio.getIdPaquete()));
    }

    @FXML
    private void handleMarcarLlegada(ActionEvent event) {
        procesarMovimiento(true);
    }

    @FXML
    private void handleMarcarSalida(ActionEvent event) {
        procesarMovimiento(false);
    }

    private void procesarMovimiento(boolean esLlegada) {
        String ofi = cmbOficinas.getValue();
        if (ofi == null) return;

        try {
            paqueteService.registrarMovimientoPaquete(envio.getIdPaquete(), ofi, LocalDateTime.now(), esLlegada, "");
            actualizarVistaRuta();
        } catch (OperacionInvalidaException e) {
            // Muestra el mensaje de error al usuario
            mostrarMensajeAlerta("Secuencia Incorrecta", e.getMessage(), Alert.AlertType.WARNING);
        } catch (Exception e) {
            mostrarMensajeAlerta("Error", "No se pudo procesar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarMensajeAlerta(String titulo, String mensaje,javafx.scene.control.Alert.AlertType tipo) {
        javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void setNavegador(NavegadorVistas navegador) {
        this.navegador = navegador;
    }

    @FXML
    private void handleVolver(ActionEvent e) {
        if (navegador != null) {
            if(ClienteActual.isIsAdmin())
                navegador.cambiarAPantalla("Paquetes/ListadoPaquetes/ListadoPaquetesAdminView.fxml", "Lista de Paquetes");
            else
                navegador.cambiarAPantalla("Paquetes/ListadoPaquetes/ListadoPaquetesUserView.fxml", "Mis Paquetes");
        }
    }
}