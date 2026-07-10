package com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.GeneracionPaquetes;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.OficinaDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.PaqueteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.Tamano;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ConfiguracionService;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.OficinaService;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.PaqueteService;
import com.ucuenca.proyecto_courier.CapaPresentacion.ClienteActual;
import com.ucuenca.proyecto_courier.CapaPresentacion.NavegadorVistas;
import com.ucuenca.proyecto_courier.GestorServicios;
import com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.OficinaModel;
import com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.PaqueteMapper;
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

import java.util.UUID;

public class GeneracionPaquetesController {

    @FXML private TextField txtIdPaquete;
    @FXML private Label textoAlerta;

    @FXML private ToggleGroup grupoTipo;
    @FXML private RadioButton rbSobre;
    @FXML private RadioButton rbCaja;

    @FXML private TextField txtPeso;
    @FXML private TextField txtValor;
    @FXML private CheckBox chkSeguro;

    @FXML private VBox paneSobre;
    @FXML private HBox paneCaja;

    @FXML private ComboBox<String> cmbTamano;
    @FXML private TextField txtAlto;
    @FXML private TextField txtAncho;
    @FXML private TextField txtLargo;

    @FXML private ComboBox<OficinaModel> cmbOficinasDisponibles;

    @FXML private ListView<String> lstRutaOficinas;

    private final SobreModel sobreModel = new SobreModel();
    private final CajaModel cajaModel = new CajaModel();

    private final ObservableList<String> rutaOficinas = FXCollections.observableArrayList();

    private NavegadorVistas navegador;

    @FXML private Button btnGuardar;

    @FXML private VBox seccionOficinas;

    @FXML
    public void initialize() {
        String idGenerado = "PAQ-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        sobreModel.setIdPaquete(idGenerado);
        cajaModel.setIdPaquete(idGenerado);
        txtIdPaquete.setText(idGenerado);
        txtIdPaquete.setEditable(false);

        for (Tamano t : Tamano.values()) {
            cmbTamano.getItems().add(t.name());
        }
        cargarOficinasDisponibles();

        sobreModel.setListaOficinasTexto(rutaOficinas);
        cajaModel.setListaOficinasTexto(rutaOficinas);
        lstRutaOficinas.setItems(rutaOficinas);
        seccionOficinas.setVisible(ClienteActual.isIsAdmin());
        // Configurar cómo se muestra el objeto OficinaModel en el ComboBox de selección
        var cellFactoryCombo = new javafx.util.Callback<ListView<OficinaModel>, ListCell<OficinaModel>>() {
            @Override
            public ListCell<OficinaModel> call(ListView<OficinaModel> p) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(OficinaModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) setText(null);
                        else setText(item.getIdOficina() + " - " + item.getNombre());
                    }
                };
            }
        };
        cmbOficinasDisponibles.setCellFactory(cellFactoryCombo);
        cmbOficinasDisponibles.setButtonCell(cellFactoryCombo.call(null));

        grupoTipo.selectedToggleProperty().addListener((obs, viejo, nuevo) -> {
            conmutarEstructuraBindings(rbSobre.isSelected());
        });

        txtPeso.textProperty().addListener((o, v, n) -> verificarValidaciones());
        txtValor.textProperty().addListener((o, v, n) -> verificarValidaciones());
        cmbTamano.valueProperty().addListener((o, v, n) -> verificarValidaciones());
        txtAlto.textProperty().addListener((o, v, n) -> verificarValidaciones());
        txtAncho.textProperty().addListener((o, v, n) -> verificarValidaciones());
        txtLargo.textProperty().addListener((o, v, n) -> verificarValidaciones());

        rutaOficinas.addListener((javafx.collections.ListChangeListener<String>) c -> verificarValidaciones());

        rbSobre.setSelected(true);
        conmutarEstructuraBindings(true);
    }

    private void conmutarEstructuraBindings(boolean esSobre) {
        txtPeso.textProperty().unbindBidirectional(sobreModel.pesoProperty());
        txtPeso.textProperty().unbindBidirectional(cajaModel.pesoProperty());
        txtValor.textProperty().unbindBidirectional(sobreModel.valorProperty());
        txtValor.textProperty().unbindBidirectional(cajaModel.valorProperty());
        chkSeguro.selectedProperty().unbindBidirectional(sobreModel.seguroProperty());
        chkSeguro.selectedProperty().unbindBidirectional(cajaModel.seguroProperty());

        cmbTamano.valueProperty().unbindBidirectional(sobreModel.tamanoProperty());
        txtAlto.textProperty().unbindBidirectional(cajaModel.altoProperty());
        txtAncho.textProperty().unbindBidirectional(cajaModel.anchoProperty());
        txtLargo.textProperty().unbindBidirectional(cajaModel.largoProperty());

        var numberConverter = new javafx.util.converter.NumberStringConverter();

        if (esSobre) {
            paneSobre.setVisible(true);
            paneCaja.setVisible(false);

            cajaModel.setAlto(0.0);
            cajaModel.setAncho(0.0);
            cajaModel.setLargo(0.0);

            javafx.beans.binding.Bindings.bindBidirectional(txtPeso.textProperty(), sobreModel.pesoProperty(), numberConverter);
            javafx.beans.binding.Bindings.bindBidirectional(txtValor.textProperty(), sobreModel.valorProperty(), numberConverter);
            chkSeguro.selectedProperty().bindBidirectional(sobreModel.seguroProperty());
            cmbTamano.valueProperty().bindBidirectional(sobreModel.tamanoProperty());
        } else {
            paneSobre.setVisible(false);
            paneCaja.setVisible(true);

            sobreModel.setTamano(null);

            javafx.beans.binding.Bindings.bindBidirectional(txtPeso.textProperty(), cajaModel.pesoProperty(), numberConverter);
            javafx.beans.binding.Bindings.bindBidirectional(txtValor.textProperty(), cajaModel.valorProperty(), numberConverter);
            chkSeguro.selectedProperty().bindBidirectional(cajaModel.seguroProperty());

            javafx.beans.binding.Bindings.bindBidirectional(txtAlto.textProperty(), cajaModel.altoProperty(), numberConverter);
            javafx.beans.binding.Bindings.bindBidirectional(txtAncho.textProperty(), cajaModel.anchoProperty(), numberConverter);
            javafx.beans.binding.Bindings.bindBidirectional(txtLargo.textProperty(), cajaModel.largoProperty(), numberConverter);
        }
        verificarValidaciones();
    }

    private void cargarOficinasDisponibles() {
        try {
            OficinaService oficinaService = GestorServicios.getInstance().obtenerServicioOficina();
            for (OficinaDTO dto : oficinaService.mostrarListaOficinas()) {
                OficinaModel mod = new OficinaModel();
                mod.setIdOficina(dto.getIdOficina());
                mod.setNombre(dto.getNombre());
                mod.setDireccion(dto.getDireccion());
                mod.setTelefono(dto.getTelefono());
                cmbOficinasDisponibles.getItems().add(mod);
            }
        } catch (Exception e) {
            System.err.println("Error al poblar catálogo de oficinas.");
        }
    }

    @FXML
    private void handleAgregarOficinaARuta(ActionEvent event) {
        OficinaModel seleccionada = cmbOficinasDisponibles.getValue();
        if (seleccionada != null && rutaOficinas.stream().noneMatch(id -> id.equals(seleccionada.getIdOficina()))) {
            rutaOficinas.add(seleccionada.getIdOficina() + " - " + seleccionada.getNombre());
            cmbOficinasDisponibles.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void handleRemoverOficinaDeRuta(ActionEvent event) {
        String seleccionada = lstRutaOficinas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            rutaOficinas.remove(seleccionada);
        }
    }

    private void verificarValidaciones() {
        if (txtPeso.getText().trim().isEmpty() || txtValor.getText().trim().isEmpty()) {
            textoAlerta.setText("Complete el peso y valor del contenido.");
            btnGuardar.setDisable(true);
            return;
        }

        try {
            Double.parseDouble(txtPeso.getText().trim());
            Double.parseDouble(txtValor.getText().trim());
        } catch (NumberFormatException e) {
            textoAlerta.setText("El peso y el valor deben ser numéricos.");
            btnGuardar.setDisable(true);
            return;
        }

        if (rbSobre.isSelected()) {
            if (cmbTamano.getValue() == null) {
                textoAlerta.setText("Debe seleccionar el tamaño del sobre.");
                btnGuardar.setDisable(true);
                return;
            }
        } else {
            if (txtAlto.getText().trim().isEmpty() || txtAncho.getText().trim().isEmpty() || txtLargo.getText().trim().isEmpty()) {
                textoAlerta.setText("Especifique las dimensiones de la caja.");
                btnGuardar.setDisable(true);
                return;
            }
            try {
                Double.parseDouble(txtAlto.getText().trim());
                Double.parseDouble(txtAncho.getText().trim());
                Double.parseDouble(txtLargo.getText().trim());
            } catch (NumberFormatException e) {
                textoAlerta.setText("Las dimensiones deben ser numéricas.");
                btnGuardar.setDisable(true);
                return;
            }
        }

        if (rutaOficinas.isEmpty() && ClienteActual.isIsAdmin()) {
            textoAlerta.setText("Debe añadir al menos una oficina a la ruta.");
            btnGuardar.setDisable(true);
            return;
        }

        textoAlerta.setText("");
        btnGuardar.setDisable(false);
    }

    @FXML
    private void handleGuardar(ActionEvent event) {
        try {
            if (rbSobre.isSelected()) {
                String tamanoUI = cmbTamano.getValue();
                if (tamanoUI != null) {
                    sobreModel.setTamano(tamanoUI);
                }
            }

            PaqueteModel modeloAEnviar = rbSobre.isSelected() ? sobreModel : cajaModel;

            modeloAEnviar.setListaOficinasTexto(rutaOficinas);

            // Mapeamos
            PaqueteDTO paqueteDto = PaqueteMapper.modeloToDto(modeloAEnviar);

            PaqueteService servicioPaquete = GestorServicios.getInstance().obtenerServicioPaquete();
            ConfiguracionService servicioConfiguracion = GestorServicios.getInstance().obtenerServicioConfiguracion();

            servicioPaquete.crearPaquete(paqueteDto,servicioConfiguracion.obtenerConfiguracion());

            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "¡Paquete registrado correctamente!");

            alerta.setHeaderText(null);
            alerta.showAndWait();

            if(!ClienteActual.isIsAdmin()){
                alerta = new Alert(Alert.AlertType.INFORMATION, "LA RUTA QUE SEGUIRA EL PAQUETE LA CONFIGURA EL ADMINISTRADOR");
                alerta.setHeaderText(null);
                alerta.showAndWait();
            }



            regresarAListado();

        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR, "Error al guardar: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        regresarAListado();
    }

    public void setNavegador(NavegadorVistas navegador) {
        this.navegador = navegador;
    }

    private void regresarAListado() {
        if (navegador != null) {
            if(ClienteActual.isIsAdmin())
                navegador.cambiarAPantalla("Paquetes/ListadoPaquetes/ListadoPaquetesAdminView.fxml", "Lista de Paquetes");
            else
                navegador.cambiarAPantalla("Paquetes/ListadoPaquetes/ListadoPaquetesUserView.fxml", "Mis Paquetes");
        }
    }
}