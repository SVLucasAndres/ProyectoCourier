package com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.AgregacionClientes;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Excepciones.ValidacionException;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ClienteService;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ClienteMapper;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ClienteModel;
import com.ucuenca.proyecto_courier.CapaPresentacion.NavegadorVistas;
import com.ucuenca.proyecto_courier.GestorServicios;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class AgregacionClientesController {

    @FXML private TextField txtCedula;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    @FXML private PasswordField txtContrasena;

    @FXML private Button btnCancelar;
    @FXML private Button btnContinuar;

    private final ClienteModel clienteModel = new ClienteModel();
    private final StringProperty contrasena = new SimpleStringProperty("");

    private boolean esModificacion = false;

    private NavegadorVistas navegador;

    public void initialize() {
        // Vinculamos los TextFields al modelo de la clase en tiempo real
        txtCedula.textProperty().bindBidirectional(clienteModel.idClienteProperty());
        txtNombre.textProperty().bindBidirectional(clienteModel.nombreProperty());
        txtDireccion.textProperty().bindBidirectional(clienteModel.direccionProperty());
        txtTelefono.textProperty().bindBidirectional(clienteModel.telefonoProperty());
        txtContrasena.textProperty().bindBidirectional(contrasena);
    }

    @FXML
    private void handleCancelar(ActionEvent event) {

        // 1. Limpiamos los datos del modelo para que no queden guardados en memoria
        clienteModel.setIdCliente("");
        clienteModel.setNombre("");
        clienteModel.setDireccion("");
        clienteModel.setTelefono("");

        if (navegador != null) {
            navegador.cambiarAPantalla("Clientes/ListaClientes/ListaClientesView.fxml", "Listado de clientes");
        }
    }

    @FXML
    private void handleContinuar(ActionEvent event) {
        try {
            //Mapeamos modelo a DTO
            ClienteDTO clienteDTO = ClienteMapper.modeloToDto(clienteModel);

            //Recuperamos el servicio de clientes
            ClienteService servicioCliente = GestorServicios.getInstance().obtenerServicioCliente();

            String mensaje = validarCampos(clienteDTO,contrasena.get());

            if(mensaje.equalsIgnoreCase("Correct")){
                //Le avisamos al cliente si se creó
                servicioCliente.crearCliente(clienteDTO,contrasena.get());
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Cliente registrado correctamente.");
            }else{
                mostrarAlerta(Alert.AlertType.ERROR, "Error de Validación", mensaje);
            }
            //Volvemos a la lista de clientes
            if (navegador != null) {
                navegador.cambiarAPantalla("Clientes/ListaClientes/ListaClientesView.fxml", "Listado de clientes");
            }

        }catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error del Sistema", "No se pudo guardar el cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(javafx.scene.control.Alert.AlertType tipo, String titulo, String mensaje) {
        javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void setNavegador(NavegadorVistas navegador) {
        this.navegador = navegador;
    }

    private String validarCampos(ClienteDTO cliente, String contrasena){
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            return ("El nombre del cliente no puede estar vacío.");
        }else if(cliente.getTelefono() == null || cliente.getTelefono().trim().isEmpty()){
            return ("El campo de teléfono no puede estar vacio");
        }else if(cliente.getDireccion() == null || cliente.getDireccion().trim().isEmpty()){
            return ("El campo de dirección no puede estar vacio");
        }else if(cliente.getIdCliente()==null || cliente.getIdCliente().trim().isEmpty()){
            return ("El campo de cédula no puede estar vacio");
        }else if(cliente.getTelefono().length() < 8){
            return "La contraseña debe tener al menos 8 caracteres";
        }else{
            return "Correct";
        }
    }
}