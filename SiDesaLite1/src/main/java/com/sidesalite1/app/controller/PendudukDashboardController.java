package com.sidesalite1.app.controller;

import com.sidesalite1.app.MainApp;
import com.sidesalite1.app.util.AlertUtil;
import com.sidesalite1.app.util.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PendudukDashboardController {

    @FXML
    private void handleAjukanSurat(ActionEvent event) {
        try {
            MainApp.setRoot("/view/PermohonanSuratView.fxml");
        } catch (Exception e) {
            AlertUtil.showError("Gagal membuka form permohonan.");
        }
    }

    @FXML
    private void handleLihatStatus(ActionEvent event) {
        try {
            // Mengarahkan ke tampilan status permohonan penduduk
            MainApp.setRoot("/view/StatusPermohonanPendudukView.fxml");
        } catch (Exception e) {
            AlertUtil.showError("Gagal membuka status permohonan.");
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