module com.ucuenca.proyecto_courier {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;

    opens com.ucuenca.proyecto_courier.CapaPresentacion to javafx.fxml;
    opens com.ucuenca.proyecto_courier.CapaPresentacion.Clientes to javafx.fxml;
    exports com.ucuenca.proyecto_courier.CapaPresentacion;
    exports com.ucuenca.proyecto_courier.CapaPresentacion.Clientes;
}