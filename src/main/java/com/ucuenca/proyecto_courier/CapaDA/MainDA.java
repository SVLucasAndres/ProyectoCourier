package com.ucuenca.proyecto_courier.CapaDA;

import com.ucuenca.proyecto_courier.CapaDominio.Cliente;
import com.ucuenca.proyecto_courier.CapaDominio.Oficina;

import java.util.List;
import java.util.Optional;

public class MainDA {

    public static void main(String[] args) {
        System.out.println("=== Iniciando Pruebas de la Capa de Acceso a Datos (DA) ===");

        // 1. Instanciar los DAOs con rutas de prueba
        ClienteXmlDAO clienteDaoXml = new ClienteXmlDAO("test_clientes.xml");
        OficinaBinDAO oficinaDaoBin = new OficinaBinDAO("test_oficinas.bin");

        // 2. Probar Entidad Cliente (XML)
        System.out.println("\n--- Probando ClienteXmlDAO ---");
        
        // NOTA: Como solo tengo el UML, asumo el uso de un constructor vacío y setters, 
        // o un constructor con parámetros. Ajusta esto según cómo programaste la clase Cliente.
        Cliente cliente1 = new Cliente();
        // cliente1.setIdCliente("C001");
        // cliente1.setNombre("Carlos Andrade");
        // cliente1.setDireccion("Av. Loja, Cuenca");
        // cliente1.setTelefono("0999999999");
        // cliente1.setActive(true);
        
        // Guardar el cliente
        System.out.println("Guardando cliente en XML...");
        // clienteDaoXml.guardar(cliente1);

        // Recuperar y buscar
        System.out.println("Buscando clientes por nombre...");
        // List<Cliente> clientesEncontrados = clienteDaoXml.buscarPorNombre("Carlos Andrade");
        // System.out.println("Clientes encontrados: " + clientesEncontrados.size());

        // 3. Probar Entidad Oficina (BIN)
        System.out.println("\n--- Probando OficinaBinDAO ---");
        
        Oficina oficina1 = new Oficina();
        // oficina1.setIdOficina("OFI-01");
        // oficina1.setNombre("Sucursal Centro");
        // oficina1.setDireccion("Centro Histórico");
        // oficina1.setTelefono("072888888");
        // oficina1.setActive(true);

        // Guardar la oficina
        System.out.println("Guardando oficina en Binario...");
        // oficinaDaoBin.guardar(oficina1);

        // Recuperar todas
        System.out.println("Recuperando todas las oficinas...");
        // List<Oficina> todasLasOficinas = oficinaDaoBin.obtenerTodos();
        // System.out.println("Total de oficinas en archivo binario: " + todasLasOficinas.size());

        // Buscar oficina específica usando Optional (como define tu UML)
        System.out.println("Buscando oficina específica...");
        // Optional<Oficina> oficinaBuscada = oficinaDaoBin.buscarPorNombre("Sucursal Centro");
        // oficinaBuscada.ifPresent(o -> System.out.println("Oficina encontrada exitosamente."));

        System.out.println("\n=== Pruebas Finalizadas ===");
    }
}