package com.sidesalite1.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.sidesalite1.app.util.UserSession; // Import UserSession
import com.sidesalite1.app.util.AlertUtil; // Import AlertUtil

import java.io.IOException;

public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        // Set properti stage awal
        primaryStage.setTitle("SiDesaLite - Sistem Informasi Desa");
        // Memuat tampilan login sebagai tampilan awal
        setRoot("/view/LoginView.fxml");
        primaryStage.show();
    }

    /**
     * Mengatur root scene ke tampilan FXML yang ditentukan.
     * Ini digunakan untuk navigasi antar halaman.
     * @param fxmlPath Jalur ke file FXML (misal: "/view/LoginView.fxml")
     */
    public static void setRoot(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Tambahkan stylesheet CSS global
            scene.getStylesheets().add(MainApp.class.getResource("/css/styles.css").toExternalForm());

            // Set scene baru
            primaryStage.setScene(scene);

            // Menyesuaikan ukuran stage saat perubahan ukuran scene
            // Ini akan membuat elemen FXML responsif terhadap perubahan ukuran window.
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen(); // Pusatkan jendela
        } catch (IOException e) {
            AlertUtil.showError("Gagal memuat tampilan: " + fxmlPath + "\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}