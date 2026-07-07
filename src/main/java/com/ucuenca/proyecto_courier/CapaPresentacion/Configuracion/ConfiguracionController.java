package com.ucuenca.proyecto_courier.CapaPresentacion.Configuracion;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.ConfiguracionDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.RangoDTO;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ConfiguracionService;
import com.ucuenca.proyecto_courier.CapaPresentacion.GestorServicios;
import com.ucuenca.proyecto_courier.CapaPresentacion.NavegadorVistas;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;

public class ConfiguracionController {

    @FXML private TextField txtIva;
    @FXML private Label lblErrorIva;

    @FXML private TableView<RangoModel> tablaRangos;
    @FXML private TableColumn<RangoModel, String> colIdRango;
    @FXML private TableColumn<RangoModel, Number> colDesde;
    @FXML private TableColumn<RangoModel, Number> colHasta;
    @FXML private TableColumn<RangoModel, Number> colPrecio;
    @FXML private TableColumn<RangoModel, Void> colAcciones;

    @FXML private Button btnGuardar;

    private final ConfiguracionModel configuracionModel = new ConfiguracionModel();
    private NavegadorVistas navegador;

    private double ivaOriginal = 15.0;
    private int cantidadRangosOriginal = 0;
    private boolean cargandoDatos = false;

    @FXML
    public void initialize() {
        colIdRango.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colDesde.setCellValueFactory(cellData -> cellData.getValue().pesoMinimoProperty());
        colHasta.setCellValueFactory(cellData -> cellData.getValue().pesoMaximoProperty());
        colPrecio.setCellValueFactory(cellData -> cellData.getValue().costoPorKilogramoProperty());

        configurarColumnaEliminar();

        txtIva.textProperty().bindBidirectional(configuracionModel.impuestoIVAProperty(), new NumberStringConverter());

        tablaRangos.setItems(configuracionModel.getRangos());

        cargarConfiguracionGlobal();

        txtIva.textProperty().addListener((observable, oldValue, newValue) -> {
            if (cargandoDatos) return;
            validarYVerificarCambios();
        });

        configuracionModel.getRangos().addListener((ListChangeListener<RangoModel>) change -> {
            if (cargandoDatos) return;
            validarYVerificarCambios();
        });
    }

    private void configurarColumnaEliminar() {
        Callback<TableColumn<RangoModel, Void>, TableCell<RangoModel, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<RangoModel, Void> call(final TableColumn<RangoModel, Void> param) {
                return new TableCell<>() {
                    private final Button btnEliminar = new Button("Eliminar");

                    {
                        btnEliminar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 3; -fx-cursor: hand;");
                        btnEliminar.setOnAction(event -> {
                            RangoModel rango = getTableView().getItems().get(getIndex());
                            configuracionModel.getRangos().remove(rango);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnEliminar);
                        }
                    }
                };
            }
        };
        colAcciones.setCellFactory(cellFactory);
    }

    private void validarYVerificarCambios() {
        String textoIva = txtIva.getText();

        if (textoIva == null || textoIva.trim().isEmpty()) {
            lblErrorIva.setText("El campo IVA es obligatorio.");
            lblErrorIva.setStyle("-fx-text-fill: #e74c3c;");
            btnGuardar.setDisable(true);
            return;
        }

        try {
            double valorIva = Double.parseDouble(textoIva.trim());
            if (valorIva < 0 || valorIva > 100) {
                lblErrorIva.setText("Debe estar entre 0% y 100%.");
                lblErrorIva.setStyle("-fx-text-fill: #e74c3c;");
                btnGuardar.setDisable(true);
                return;
            }
        } catch (NumberFormatException e) {
            lblErrorIva.setText("Ingrese un número decimal válido.");
            lblErrorIva.setStyle("-fx-text-fill: #e74c3c;");
            btnGuardar.setDisable(true);
            return;
        }

        double valorIvaActual = configuracionModel.getImpuestoIVA();
        boolean haCambiadoIva = Math.abs(valorIvaActual - ivaOriginal) > 0.001;
        boolean hanCambiadoRangos = configuracionModel.getRangos().size() != cantidadRangosOriginal;

        if (haCambiadoIva || hanCambiadoRangos) {
            lblErrorIva.setText("Hay cambios sin guardar.");
            lblErrorIva.setStyle("-fx-text-fill: #f39c12;");
            btnGuardar.setDisable(false);
        } else {
            lblErrorIva.setText("");
            btnGuardar.setDisable(false);
        }
    }

    private void cargarConfiguracionGlobal() {
        try {
            cargandoDatos = true;
            ConfiguracionService servicio = GestorServicios.getInstance().obtenerServicioConfiguracion();
            ConfiguracionDTO dto = servicio.obtenerConfiguracion();

            if (dto != null) {
                ConfiguracionModel cargado = ConfiguracionMapper.dtoToModelo(dto);
                configuracionModel.setIdConfiguracion(cargado.getIdConfiguracion());
                configuracionModel.setImpuestoIVA(cargado.getImpuestoIVA());
                configuracionModel.getRangos().setAll(cargado.getRangos());

                ivaOriginal = cargado.getImpuestoIVA();
                cantidadRangosOriginal = cargado.getRangos().size();
            }
        } catch (Exception e) {
            configuracionModel.setIdConfiguracion("GLOBAL");
            configuracionModel.setImpuestoIVA(15.0);
            ivaOriginal = 15.0;
            cantidadRangosOriginal = 0;
        } finally {
            cargandoDatos = false;
        }
    }

    @FXML
    private void handleGuardar(ActionEvent event) {
        try {
            ConfiguracionService servicio = GestorServicios.getInstance().obtenerServicioConfiguracion();

            ConfiguracionDTO dtoAEnviar = ConfiguracionMapper.modeloToDto(configuracionModel);
            servicio.guardarConfiguracion(dtoAEnviar);

            ivaOriginal = configuracionModel.getImpuestoIVA();
            cantidadRangosOriginal = configuracionModel.getRangos().size();
            lblErrorIva.setText("");

            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Configuración global guardada correctamente.", ButtonType.OK);
            alerta.setTitle("Éxito");
            alerta.setHeaderText(null);
            alerta.showAndWait();

        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR, "No se pudo guardar la configuración: " + e.getMessage(), ButtonType.OK);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.showAndWait();
        }
    }

    public void setNavegador(NavegadorVistas navegador) {
        this.navegador = navegador;
    }

    @FXML
    private void handleAgregarRango(ActionEvent event) {
        Dialog<RangoModel> dialog = new Dialog<>();
        dialog.setTitle("Agregar Nuevo Rango");
        dialog.setHeaderText("Ingrese los límites y el costo base del rango:");

        ButtonType buttonTypeOk = new ButtonType("Agregar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        TextField txtId = new TextField();
        txtId.setPromptText("Ej: RANGO_04");
        TextField txtMin = new TextField();
        txtMin.setPromptText("Ej: 10.5");
        TextField txtMax = new TextField();
        txtMax.setPromptText("Ej: 20.0");
        TextField txtVal = new TextField();
        txtVal.setPromptText("Ej: 5.75");

        grid.add(new Label("ID Rango:"), 0, 0);
        grid.add(txtId, 1, 0);
        grid.add(new Label("Límite Inferior (Min):"), 0, 1);
        grid.add(txtMin, 1, 1);
        grid.add(new Label("Límite Superior (Max):"), 0, 2);
        grid.add(txtMax, 1, 2);
        grid.add(new Label("Costo Base:"), 0, 3);
        grid.add(txtVal, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeOk) {
                try {
                    RangoModel nuevoRango = new RangoModel();
                    nuevoRango.setNombre(txtId.getText().trim());
                    nuevoRango.setPesoMinimo(Double.parseDouble(txtMin.getText().trim()));
                    nuevoRango.setPesoMaximo(Double.parseDouble(txtMax.getText().trim()));
                    nuevoRango.setCostoPorKilogramo(Double.parseDouble(txtVal.getText().trim()));

                    for(RangoModel e : configuracionModel.getRangos()){
                        if(e.getNombre().equalsIgnoreCase(nuevoRango.getNombre())){
                            mostrarAlerta(Alert.AlertType.ERROR, "Error de Nombre", "Este rango ya existe. Eliga otro nombre");
                            return null;
                        }
                        if(nuevoRango.getPesoMinimo() <= e.getPesoMaximo() && nuevoRango.getPesoMinimo() >= e.getPesoMinimo()){
                            mostrarAlerta(Alert.AlertType.ERROR, "Error de Peso minimo", "El límite inferior está sobre el rango " + e.getNombre());
                            return null;
                        }
                        if(nuevoRango.getPesoMaximo() <= e.getPesoMaximo() && nuevoRango.getPesoMaximo() >= e.getPesoMinimo()){
                            mostrarAlerta(Alert.AlertType.ERROR, "Error de Peso máximo", "El límite superior está sobre el rango " + e.getNombre());
                            return null;
                        }
                        if(nuevoRango.getPesoMinimo() >= nuevoRango.getPesoMaximo()){
                            mostrarAlerta(Alert.AlertType.ERROR, "Error de Pesos", "El peso minimo no puede ser mayor o igual al máximo");
                            return null;
                        }
                    }
                    return nuevoRango;
                } catch (NumberFormatException e) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error de Formato", "Los límites y el costo deben ser números válidos.");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(nuevoRango -> {
            if (!nuevoRango.getNombre().isEmpty()) {
                configuracionModel.getRangos().add(nuevoRango);
            }
        });
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}