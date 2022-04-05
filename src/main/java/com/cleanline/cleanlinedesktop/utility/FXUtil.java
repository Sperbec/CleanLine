package com.cleanline.cleanlinedesktop.utility;

import javafx.scene.control.Alert;

public class FXUtil {
    public static void showInformationAlert(String message) {
        Alert inf = new Alert(Alert.AlertType.INFORMATION);

        inf.setHeaderText(null);
        inf.setTitle("Información");
        inf.setContentText(message);
        inf.showAndWait();
    }
}
