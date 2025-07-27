package com.sidesalite1.app.controller;

import com.sidesalite1.app.MainApp;
import com.sidesalite1.app.util.AlertUtil;
import com.sidesalite1.app.util.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PetugasDashboardController {

    @FXML
    private void handleKelolaPenduduk(ActionEvent event) {
        try {
            MainApp.setRoot("/view/KelolaPendudukView.fxml");
        } catch (Exception e) {
            AlertUtil.showError("Gagal membuka manajemen penduduk.");
        }
    }

    @FXML
    private void handleKelolaPermohonan(ActionEvent event) {
        try {
            MainApp.setRoot("/view/KelolaPermohonanView.fxml");
        } catch (Exception e) {
            AlertUtil.showError("Gagal membuka manajemen permohonan.");
        }
    }

    @FXML
    private void handleKelolaSurat(ActionEvent event) {
        try {
            MainApp.setRoot("/view/KelolaSuratView.fxml"); // Untuk mengelola surat yang sudah diterbitkan
        } catch (Exception e) {
            AlertUtil.showError("Gagal membuka manajemen surat.");
        }
    }

    @FXML
    private void handleLogout() {
        // Membersihkan sesi saat logout
        UserSession.cleanSession();
        try {
            MainApp.setRoot("/view/LoginView.fxml");
        } catch (Exception e) {
            AlertUtil.showError("Logout gagal.");
        }
    }
}