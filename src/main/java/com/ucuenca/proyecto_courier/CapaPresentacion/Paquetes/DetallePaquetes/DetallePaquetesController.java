package com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.DetallePaquetes;

import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.NavegadorVistas;
import com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.ContextoPaquete;
import com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.PaqueteModel;
import com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.SobreModel;
import com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.CajaModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DetallePaquetesController {

    @FXML private TextField txtIdPaquete;
    @FXML private TextField txtPeso;
    @FXML private TextField txtValor;
    @FXML private CheckBox chkSeguro;

    // Paneles dinámicos de visualización
    @FXML private VBox paneSobre;
    @FXML private HBox paneCaja;

    // Campos específicos
    @FXML private TextField txtTamano;
    @FXML private TextField txtAlto;
    @FXML private TextField txtAncho;
    @FXML private TextField txtLargo;

    @FXML private ListView<String> lstRutaOficinas;
    @FXML private Button btnVolver;

    private final ObservableList<String> rutaOficinas = FXCollections.observableArrayList();

    private NavegadorVistas navegador;

    @FXML
    public void initialize() {
        // Vincular la lista visual reactiva de Strings
        lstRutaOficinas.setItems(rutaOficinas);
        mostrarDetallePaquete();
    }

    private void mostrarDetallePaquete() {
        PaqueteModel envio = ContextoPaquete.getEnvio();

        if (envio == null) {
            System.err.println("Error: No se encontró ningún paquete seleccionado en el contexto.");
            return;
        }

        txtIdPaquete.setText(envio.getIdPaquete());
        txtPeso.setText(String.valueOf(envio.getPeso()) + " kg");
        txtValor.setText("$" + String.valueOf(envio.getValor()));
        chkSeguro.setSelected(envio.getSeguro());

        if (envio.getListaOficinasTexto() != null) {
            rutaOficinas.setAll(envio.getListaOficinasTexto());
        }

        // Inspección polimórfica en la capa de presentación para conmutar paneles
        if (envio instanceof SobreModel) {
            paneSobre.setVisible(true);
            paneCaja.setVisible(false);

            txtTamano.setText(((SobreModel) envio).getTamano());
        } else if (envio instanceof CajaModel) {
            paneSobre.setVisible(false);
            paneCaja.setVisible(true);

            CajaModel caja = (CajaModel) envio;
            txtAlto.setText(String.valueOf(caja.getAlto()) + " cm");
            txtAncho.setText(String.valueOf(caja.getAncho()) + " cm");
            txtLargo.setText(String.valueOf(caja.getLargo()) + " cm");
        }
    }

    public void setNavegador(NavegadorVistas navegador) {
        this.navegador = navegador;
    }

    @FXML
    private void handleVolver(ActionEvent event) {
        if (navegador != null) {
            navegador.cambiarAPantalla("Paquetes/ListadoPaquetes/ListadoPaquetesView.fxml", "Lista de Paquetes");
        }
    }
}