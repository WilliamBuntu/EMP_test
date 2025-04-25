module com.example.amalitechemployeemanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires annotations;
    requires java.logging;

    opens com.example.amalitechemployeemanagementsystem to javafx.fxml;
    exports com.example.amalitechemployeemanagementsystem;
    exports com.example.amalitechemployeemanagementsystem.controllers;
    opens com.example.amalitechemployeemanagementsystem.controllers to javafx.fxml;
}