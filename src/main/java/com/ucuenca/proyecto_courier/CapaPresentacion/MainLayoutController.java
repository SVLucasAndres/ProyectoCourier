package com.ucuenca.proyecto_courier.CapaPresentacion;

import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.AgregacionClientes.AgregacionClientesController;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ListaClientes.ListaClientesController;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ModificarCliente.ModificacionClientesController;
import com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.PropiedadesCliente.PropiedadesClienteController;
import com.ucuenca.proyecto_courier.CapaPresentacion.Configuracion.ConfiguracionController;
import com.ucuenca.proyecto_courier.CapaPresentacion.Envios.DetalleEnvios.DetalleEnviosController;
import com.ucuenca.proyecto_courier.CapaPresentacion.Envios.GeneracionEnvios.GeneracionEnviosController;
import com.ucuenca.proyecto_courier.CapaPresentacion.Envios.ListaEnvios.ListaEnviosController;
import com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.AgregacionOficinas.AgregacionOficinasController;
import com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.DetalleOficinas.DetallesOficinasController;
import com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.ListaOficinas.ListaOficinasController;
import com.ucuenca.proyecto_courier.CapaPresentacion.Oficinas.ModificacionOficinas.ModificacionOficinasController;
import com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.DetallePaquetes.DetallePaquetesController;
import com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.GeneracionPaquetes.GeneracionPaquetesController;
import com.ucuenca.proyecto_courier.CapaPresentacion.Paquetes.ListadoPaquetes.ListadoPaquetesController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class MainLayoutController {

    // Enlazamos el VBox del centro usando el mismo fx:id
    @FXML private VBox panelCentral;
    @FXML private Button btnInicio;
    @FXML private Button btnClientes;
    @FXML private Button btnEnvios;
    @FXML private Button btnPaquetes;
    @FXML private Button btnOficinas;
    @FXML private Button btnConfig;


    @FXML
    public void initialize() {
        if(ClienteActual.isIsAdmin()){
            btnEnvios.setText("Envios");
            btnPaquetes.setText("Paquetes");
            cargarVista("Inicio/InicioView.fxml","Inicio");
            actualizarBotonActivo(btnInicio);
        }else{
            btnEnvios.setText("Mís Envios");
            btnPaquetes.setText("Mís Paquetes");
            cargarVista("Envios/ListaEnvios/ListaEnviosUserView.fxml","Inicio");
            actualizarBotonActivo(btnEnvios);
        }
        // Al arrancar, cargamos la lista automáticamente en ese espacio

    }
    private void cargarVista(String rutaVista, @NotNull @NonNls String nombreVista){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaVista));
            Parent vistaClientes = loader.load();

            Object controlador = loader.getController();

            //CLIENTES
            if (controlador instanceof ListaClientesController) {
                ((ListaClientesController) controlador).setNavegador(this::cargarVista);
            }else if (controlador instanceof AgregacionClientesController) {
                ((AgregacionClientesController) controlador).setNavegador(this::cargarVista);
            }else if (controlador instanceof PropiedadesClienteController) {
                ((PropiedadesClienteController) controlador).setNavegador(this::cargarVista);
            }else if (controlador instanceof ModificacionClientesController) {
                ((ModificacionClientesController) controlador).setNavegador(this::cargarVista);
            }

            //ENVIOS
            if (controlador instanceof ListaEnviosController) {
                ((ListaEnviosController) controlador).setNavegador(this::cargarVista);
            }else if (controlador instanceof GeneracionEnviosController) {
                ((GeneracionEnviosController) controlador).setNavegador(this::cargarVista);
            }else if (controlador instanceof DetalleEnviosController) {
                ((DetalleEnviosController) controlador).setNavegador(this::cargarVista);
            }

            //Paquetes
            if (controlador instanceof ListadoPaquetesController) {
                ((ListadoPaquetesController) controlador).setNavegador(this::cargarVista);
            }else if (controlador instanceof GeneracionPaquetesController) {
                ((GeneracionPaquetesController) controlador).setNavegador(this::cargarVista);
            }else if (controlador instanceof DetallePaquetesController) {
                ((DetallePaquetesController) controlador).setNavegador(this::cargarVista);
            }

            //Oficina
            if (controlador instanceof ListaOficinasController) {
                ((ListaOficinasController) controlador).setNavegador(this::cargarVista);
            }else if (controlador instanceof AgregacionOficinasController) {
                ((AgregacionOficinasController) controlador).setNavegador(this::cargarVista);
            }else if (controlador instanceof DetallesOficinasController) {
                ((DetallesOficinasController) controlador).setNavegador(this::cargarVista);
            }else if (controlador instanceof ModificacionOficinasController) {
                ((ModificacionOficinasController) controlador).setNavegador(this::cargarVista);
            }

            //Configuracion
            if (controlador instanceof ConfiguracionController) {
                ((ConfiguracionController) controlador).setNavegador(this::cargarVista);
            }

            panelCentral.getChildren().clear();
            panelCentral.getChildren().add(vistaClientes);
            VBox.setVgrow(vistaClientes, javafx.scene.layout.Priority.ALWAYS);

        } catch(IOException e){
            System.out.println("ERROR DE CARGA: No se pudo incrustar la vista de " + nombreVista + ".");
            e.printStackTrace();
        }
    }
    @FXML
    private void cambiarVista(ActionEvent evento){
        Object botonPresionado = evento.getSource();
        if (botonPresionado instanceof Button) {
            actualizarBotonActivo((Button) botonPresionado);
        }
        if(botonPresionado == btnClientes){
            cargarVista("Clientes/ListaClientes/ListaClientesView.fxml","Clientes");
        }else if(botonPresionado == btnConfig){
            cargarVista("Configuracion/ConfiguracionView.fxml","Configuracion");
        }else if(botonPresionado == btnEnvios){
            if (ClienteActual.isIsAdmin()) {
                cargarVista("Envios/ListaEnvios/ListaEnviosAdminView.fxml","Envios");
            } else {
                cargarVista("Envios/ListaEnvios/ListaEnviosUserView.fxml","Mis Envios");
            }

        }else if(botonPresionado == btnInicio){
            cargarVista("Inicio/InicioView.fxml","Inicio");
        }else if(botonPresionado == btnOficinas){
            cargarVista("Oficinas/ListaOficinas/ListaOficinasView.fxml","Oficinas");
        }else if(botonPresionado == btnPaquetes){
            if (ClienteActual.isIsAdmin()) {
                cargarVista("Paquetes/ListadoPaquetes/ListadoPaquetesAdminView.fxml", "Paquetes - Administración");
            } else {
                cargarVista("Paquetes/ListadoPaquetes/ListadoPaquetesUserView.fxml", "Mis Paquetes");
            }
        }
    }

    private void actualizarBotonActivo(Button botonSeleccionado) {
        // Creamos un arreglo con todos tus botones del menú
        Button[] todosLosBotones = {btnInicio, btnClientes, btnEnvios, btnPaquetes, btnOficinas, btnConfig};

        String claseActiva = "button-menu-active";

        for (Button btn : todosLosBotones) {
            if (btn != null) {
                // Removemos el estilo activo si lo tiene
                btn.getStyleClass().remove(claseActiva);
            }
        }

        botonSeleccionado.getStyleClass().add(claseActiva);
    }

    public void configurarMenuSegunRol(@NotNull String rol) {
        if (rol.equalsIgnoreCase("Cliente")) {
            // El cliente solo puede ver la pantalla de Inicio, Envíos y sus Paquetes
            ocultarComponente(btnClientes);
            ocultarComponente(btnOficinas);
            ocultarComponente(btnConfig);
            ocultarComponente(btnInicio);

        } else if (rol.equalsIgnoreCase("Administrador")) {
            // El administrador tiene acceso total a todos los botones del menú
            mostrarComponente(btnInicio);
            mostrarComponente(btnClientes);
            mostrarComponente(btnEnvios);
            mostrarComponente(btnPaquetes);
            mostrarComponente(btnOficinas);
            mostrarComponente(btnConfig);
        }
    }

    private void ocultarComponente(Button boton) {
        if (boton != null) {
            boton.setVisible(false);
            boton.setManaged(false); // Quita el espacio físico reservado en el contenedor layout
        }
    }

    private void mostrarComponente(Button boton) {
        if (boton != null) {
            boton.setVisible(true);
            boton.setManaged(true);  // Devuelve el espacio físico en el contenedor layout
        }
    }
}

