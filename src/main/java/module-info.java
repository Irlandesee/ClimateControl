module it.uninsubria.climatemonitoring {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens it.uninsubria.climatemonitoring to javafx.fxml;
    exports it.uninsubria.climatemonitoring;
}