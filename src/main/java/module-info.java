module com.ucuenca.proyecto_courier {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;
    requires annotations;

    opens com.ucuenca.proyecto_courier.CapaPresentacion to javafx.fxml;
    opens com.ucuenca.proyecto_courier.CapaPresentacion.Clientes to javafx.fxml;
    exports com.ucuenca.proyecto_courier.CapaPresentacion;

    opens com.ucuenca.proyecto_courier.CapaDominio;
    exports com.ucuenca.proyecto_courier.CapaDominio;
    exports com.ucuenca.proyecto_courier.CapaDA;
    exports com.ucuenca.proyecto_courier.CapaPresentacion.Clientes;
    opens com.ucuenca.proyecto_courier.CapaPresentacion.Inicio to javafx.fxml;
    exports com.ucuenca.proyecto_courier.CapaPresentacion.Inicio;
    opens com.ucuenca.proyecto_courier.CapaPresentacion.Envios to javafx.fxml;
    exports com.ucuenca.proyecto_courier.CapaPresentacion.Envios;
    exports com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.AgregacionClientes;
    opens com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.AgregacionClientes to javafx.fxml;
    exports com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ListaClientes;
    opens com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.ListaClientes to javafx.fxml;
    exports com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.PropiedadesCliente;
    opens com.ucuenca.proyecto_courier.CapaPresentacion.Clientes.PropiedadesCliente to javafx.fxml;
    exports com.ucuenca.proyecto_courier.CapaPresentacion.Envios.ListaEnvios;
    opens com.ucuenca.proyecto_courier.CapaPresentacion.Envios.ListaEnvios to javafx.fxml;
    exports com.ucuenca.proyecto_courier.CapaPresentacion.Envios.GeneracionEnvios;
    opens com.ucuenca.proyecto_courier.CapaPresentacion.Envios.GeneracionEnvios to javafx.fxml;
}