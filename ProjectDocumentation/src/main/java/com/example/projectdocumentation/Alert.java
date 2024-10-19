package com.example.projectdocumentation;

import javafx.scene.control.ButtonType;

public class Alert {
    public static void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean showConfirmation(String title, String message){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType okButton = new ButtonType("استمرار");
        ButtonType cancelButton = new ButtonType("إلغاء");

        alert.getButtonTypes().setAll(okButton, cancelButton);

        alert.showAndWait();

        return alert.getResult() == okButton;
    }
}
