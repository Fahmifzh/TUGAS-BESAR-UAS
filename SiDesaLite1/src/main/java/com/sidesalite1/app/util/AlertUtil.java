package com.sidesalite1.app.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertUtil {

    /**
     * Menampilkan dialog alert biasa tanpa return.
     */
    public static void showAlert(String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // --- Alert dengan return (bisa digunakan untuk konfirmasi)
    public static Optional<ButtonType> showAlertWithResult(String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert.showAndWait(); // return Optional<ButtonType>
    }

    // --- INFO
    public static void showInfo(String content) {
        showAlert("Informasi", content, AlertType.INFORMATION);
    }

    public static void showInfo(String title, String content) {
        showAlert(title, content, AlertType.INFORMATION);
    }

    // --- ERROR
    public static void showError(String content) {
        showAlert("Error", content, AlertType.ERROR);
    }

    public static void showError(String title, String content) {
        showAlert(title, content, AlertType.ERROR);
    }

    // --- WARNING
    public static void showWarning(String content) {
        showAlert("Peringatan", content, AlertType.WARNING);
    }

    public static void showWarning(String title, String content) {
        showAlert(title, content, AlertType.WARNING);
    }

    // --- CONFIRMATION
    public static Optional<ButtonType> showConfirmation(String title, String content) {
        return showAlertWithResult(title, content, AlertType.CONFIRMATION);
    }
}
