package com.ucuenca.proyecto_courier.CapaPresentacion.Inicio;

import com.ucuenca.proyecto_courier.CapaDominio.DTO.ConfiguracionDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.EnvioDTO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.OficinaDTO;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.EstadoEnvio;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ClienteService;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ConfiguracionService;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.EnvioService;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.OficinaService;
import com.ucuenca.proyecto_courier.GestorServicios;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class InicioController {

    @FXML private Label lblEnviosActivos;
    @FXML private Label lblClientesTotales;
    @FXML private Label lblOficinasActivas;

    @FXML
    public void initialize() {
        cargarDatosReales();
    }

    private void cargarDatosReales() {
        try {
            ClienteService clienteService = GestorServicios.getInstance().obtenerServicioCliente();
            EnvioService envioService = GestorServicios.getInstance().obtenerServicioEnvio();
            OficinaService oficinaService = GestorServicios.getInstance().obtenerServicioOficina();
            ConfiguracionService configuracionService = GestorServicios.getInstance().obtenerServicioConfiguracion();

            ConfiguracionDTO config = configuracionService.obtenerConfiguracion();

            int totalClientes = clienteService.mostrarListaClientes().size();
            
            List<EnvioDTO> envios = envioService.mostrarListaEnvios(config);
            long enviosActivos = envios.stream().filter(e -> e.getEstadoEnvio() == EstadoEnvio.ACTIVO).count();
            
            List<OficinaDTO> oficinas = oficinaService.mostrarListaOficinas();
            long oficinasActivas = oficinas.stream().filter(OficinaDTO::isActive).count();

            lblClientesTotales.setText(String.valueOf(totalClientes));
            lblEnviosActivos.setText(String.valueOf(enviosActivos));
            lblOficinasActivas.setText(String.valueOf(oficinasActivas));

        } catch (Exception e) {
            System.err.println("Error al cargar los datos del panel de control: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
