module com.isep.the7wondersarchitect {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.isep.controllers to javafx.fxml;
    exports com.isep.controllers;
    exports com.isep;
    opens com.isep to javafx.fxml;
    exports com.isep.the7WondersArchitect;
    opens com.isep.the7WondersArchitect to javafx.fxml;
}