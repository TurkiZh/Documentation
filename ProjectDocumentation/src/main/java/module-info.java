module com.example.projectdocumentation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.projectdocumentation to javafx.fxml;
    exports com.example.projectdocumentation;
}