module com.example.demo17 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.demo17 to javafx.fxml;
    exports com.example.demo17;
}