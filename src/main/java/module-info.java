module com.example.billingsoftware {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;



    opens com.example.billingsoftware to javafx.fxml;
    exports com.example.billingsoftware;
    exports Model;
    exports util;
}