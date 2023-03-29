module com.climatemonitoring.climatemonitoring {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.climatemonitoring.climatemonitoring to javafx.fxml;
    exports com.climatemonitoring.climatemonitoring;
}