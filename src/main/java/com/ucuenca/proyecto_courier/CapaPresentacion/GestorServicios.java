package com.ucuenca.proyecto_courier.CapaPresentacion;

import com.ucuenca.proyecto_courier.CapaDominio.*;
import com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl.*;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.*;
import com.ucuenca.proyecto_courier.CapaDA.*;
//Esta clase se encargará de la relación de Dependencias
public class GestorServicios {
    private static ClienteService servicioCliente;
    private static EnvioService servicioEnvio;
    private static OficinaService servicioOficina;
    private static PaqueteService servicioPaquete;
    private static ConfiguracionService servicioConfiguracion;
    private static GestorServicios instancia;

    private GestorServicios() {}

    public static GestorServicios getInstance() {
        if (instancia == null) {
            instancia = new GestorServicios();
        }
        return instancia;
    }
    public static void inicializarComponentes(String persistenciaSeleccionada) {
        DAO<Cliente> clienteDAO;
        DAO<Envio> envioDAO;
        DAO<Configuracion> configuracionDAO;
        DAO<Oficina> oficinaDAO;
        DAO<Paquete> paqueteDAO;
        // Instanciamos los DAOs
        if(persistenciaSeleccionada.equalsIgnoreCase("XML")){
            clienteDAO = new ClienteXmlDAO("data/clientes.xml");
            envioDAO = new EnvioXmlDAO("data/envios.xml");
            configuracionDAO = new ConfiguracionXmlDAO("data/configuraciones.xml");
            oficinaDAO = new OficinaXmlDAO("data/oficinas.xml");
            paqueteDAO = new PaqueteXmlDAO("data/paquetes.xml");
        }else{
            clienteDAO = new ClienteBinDAO("data/clientes.bin");
            envioDAO = new EnvioBinDAO("data/envios.bin");
            configuracionDAO = new ConfiguracionBinDAO("data/configuraciones.bin");
            oficinaDAO = new OficinaBinDAO("data/oficinas.bin");
            paqueteDAO = new PaqueteBinDAO("data/paquetes.bin");
        }
        // Inyectores de DAOs
        servicioCliente = new ClienteServiceImpl(clienteDAO);
        servicioEnvio = new EnvioServiceImpl(envioDAO,clienteDAO,configuracionDAO);
        servicioConfiguracion = new ConfiguracionServiceImpl(configuracionDAO);
        servicioOficina = new OficinaServiceImpl(oficinaDAO);
        servicioPaquete = new PaqueteServiceImpl(paqueteDAO,oficinaDAO);
    }

    public ClienteService obtenerServicioCliente(){
        return servicioCliente;
    }

    public EnvioService obtenerServicioEnvio(){
        return servicioEnvio;
    }

    public OficinaService obtenerServicioOficina(){
        return servicioOficina;
    }

    public PaqueteService obtenerServicioPaquete(){
        return servicioPaquete;
    }

    public ConfiguracionService obtenerServicioConfiguracion(){
        return servicioConfiguracion;
    }

}
