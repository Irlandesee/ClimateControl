module com.climatemonitoring.climatemonitoring {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens it.uninsubria.climatemonitoring to javafx.fxml;
    exports it.uninsubria.climatemonitoring;
    exports it.uninsubria.climatemonitoring.operatore;
    opens it.uninsubria.climatemonitoring.operatore to javafx.fxml;
    exports it.uninsubria.climatemonitoring.centroMonitoraggio;
    opens it.uninsubria.climatemonitoring.centroMonitoraggio to javafx.fxml;
}