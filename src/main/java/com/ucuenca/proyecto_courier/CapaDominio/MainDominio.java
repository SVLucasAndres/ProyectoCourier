package com.ucuenca.proyecto_courier.CapaDominio;

import com.ucuenca.proyecto_courier.CapaDominio.Enums.MetodoPago;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.Tamano;
import com.ucuenca.proyecto_courier.CapaDominio.Enums.TipoServicio;

import java.util.ArrayList;
import java.util.List;

import com.ucuenca.proyecto_courier.CapaDominio.ServiceImpl.ClienteServiceImpl;
import com.ucuenca.proyecto_courier.CapaDominio.interfaces.ClienteService;
import com.ucuenca.proyecto_courier.CapaDA.xml.ClienteXmlDAO;
import com.ucuenca.proyecto_courier.CapaDominio.DTO.ClienteDTO;

public class MainDominio {
    public static void main(String[] args) {
        System.out.println("--- INICIANDO PRUEBA DEL DOMINIO ---");

        // 1. Crear Rangos (Tarifas)
        List<Rango> tarifas = new ArrayList<>();
        tarifas.add(new Rango("Liviano", 0.0, 5.0, 2.50)); // $2.50 x kg
        tarifas.add(new Rango("Pesado", 5.01, 20.0, 4.00)); // $4.00 x kg

        // 2. Crear Clientes
        Cliente remitente = new Cliente("CLI-001", "Juan Perez", "Av. Americas", "0999999999", new ArrayList<>(), true);
        Cliente destinatario = new Cliente("CLI-002", "Maria Lopez", "Calle Larga", "0988888888", new ArrayList<>(), true);
        System.out.println("Clientes creados: " + remitente.getNombre() + " y " + destinatario.getNombre());

        // 3. Crear Paquetes
        RutaSeguimiento rutaCaja = new RutaSeguimiento(new ArrayList<>());
        // Caja de 3kg, valor 100, con seguro del 5%
        Caja caja = new Caja("PKG-001", 3.0, 100.0, true, 5.0, rutaCaja, 10, 10, 10);
        
        RutaSeguimiento rutaSobre = new RutaSeguimiento(new ArrayList<>());
        // Sobre de 0.5kg, valor 10, sin seguro
        Sobre sobre = new Sobre("PKG-002", 0.5, 10.0, false, 0.0, rutaSobre, Tamano.PEQUENO);

        List<Paquete> listaPaquetes = new ArrayList<>();
        listaPaquetes.add(caja);
        listaPaquetes.add(sobre);

        // 4. Calcular Costos Individuales
        System.out.println("\n--- COSTOS INDIVIDUALES ---");
        System.out.println("Caja Base (3kg * $2.50): $" + caja.calcularCostoBase(tarifas));
        System.out.println("Caja Seguro (5% de 100): $" + caja.calcularCostoSeguro());
        System.out.println("Sobre Base (0.5kg * $2.50): $" + sobre.calcularCostoBase(tarifas));
        System.out.println("Sobre Seguro (Sin seguro): $" + sobre.calcularCostoSeguro());

        // 5. Crear Envio y Calcular Costo Total
        Envio envio = new Envio("ENV-001", remitente, destinatario, listaPaquetes, TipoServicio.NORMAL, MetodoPago.EFECTIVO);
        
        System.out.println("\n--- COSTO TOTAL DEL ENVÍO ---");
        double costoTotal = envio.calcularCostoTotal(tarifas);
        System.out.println("Costo Total (Base + Seguro de todos los paquetes): $" + costoTotal);

        // 6. Probar RutaSeguimiento y PuntosIntermedios
        Oficina oficinaCuenca = new Oficina("OFI-CUE", "Oficina Cuenca", "Av. Espana", "072222222", true);
        PuntoIntermedio punto = new PuntoIntermedio("Oficina Cuenca", java.time.LocalDateTime.now(), null, oficinaCuenca);
        
        rutaCaja.agregarPaso(punto);
        System.out.println("\n--- RUTA DE SEGUIMIENTO ---");
        System.out.println("Pasos registrados en la caja: " + rutaCaja.getPuntosIntermedios().size());
        System.out.println("Última ubicación: " + rutaCaja.getPuntosIntermedios().get(0).getNombreOficina());
        
        // 7. Prueba de Servicios y DAOs
        System.out.println("\n--- PRUEBA DE SERVICIOS Y DAOS (Camino A) ---");
        // Inyectando el DAO genérico XML al Servicio
        ClienteService clienteService = new ClienteServiceImpl(new ClienteXmlDAO());
        
        ClienteDTO nuevoDTO = new ClienteDTO("CLI-003", "Lucas", "Su casa", "123456789", true);
        clienteService.crearCliente(nuevoDTO);
        System.out.println("Servicio ejecutó crearCliente sin errores usando ClienteXmlDAO.");
        
        // Probando la búsqueda por nombre (retornará null porque los DAOs aún están vacíos)
        ClienteDTO buscado = clienteService.buscarClientePorNombre("Lucas");
        System.out.println("Búsqueda por nombre ejecutada (DAO aún no lee archivos): " + buscado);

        System.out.println("\n--- PRUEBA FINALIZADA CON ÉXITO ---");
    }
}
