module com.ucuenca.proyecto_courier {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;

    opens com.ucuenca.proyecto_courier.CapaPresentacion to javafx.fxml;
    exports com.ucuenca.proyecto_courier.CapaPresentacion;

    opens com.ucuenca.proyecto_courier.CapaDominio;
    exports com.ucuenca.proyecto_courier.CapaDominio;
    exports com.ucuenca.proyecto_courier.CapaDA;
}