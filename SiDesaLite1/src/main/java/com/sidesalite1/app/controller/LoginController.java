package com.sidesalite1.app.controller;

import com.sidesalite1.app.MainApp;
import com.sidesalite1.app.dao.PendudukDao;
import com.sidesalite1.app.dao.UserDao;
import com.sidesalite1.app.model.Penduduk;
import com.sidesalite1.app.model.User;
import com.sidesalite1.app.util.AlertUtil;
import com.sidesalite1.app.util.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final UserDao userDao = new UserDao();
    private final PendudukDao pendudukDao = new PendudukDao(); // Digunakan untuk mendapatkan NIK penduduk

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            AlertUtil.showWarning("Username dan Password harus diisi!");
            return;
        }

        User user = userDao.login(username, password);
        if (user != null) {
            // Inisialisasi UserSession
            UserSession.getInstance(user);

            try {
                if (UserSession.getInstance().isPenduduk()) {
                    // Jika user adalah penduduk, cari NIK-nya
                    Penduduk p = pendudukDao.getPendudukByUserId(user.getId());
                    if (p != null) {
                        UserSession.getInstance().setLoggedInUserNik(p.getNik());
                        MainApp.setRoot("/view/PendudukDashboardView.fxml");
                    } else {
                        // Ini skenario jarang, tapi mungkin terjadi jika user_id ada tapi data penduduk tidak ada
                        AlertUtil.showError("Data penduduk tidak ditemukan untuk akun ini. Harap hubungi petugas.");
                        UserSession.cleanSession(); // Hapus sesi jika data tidak konsisten
                    }
                } else if (UserSession.getInstance().isAdmin() || UserSession.getInstance().isPetugas()) {
                    // Admin/Petugas tidak perlu NIK, jadi set null
                    UserSession.getInstance().setLoggedInUserNik(null);
                    MainApp.setRoot("/view/PetugasDashboardView.fxml");
                } else {
                    AlertUtil.showError("Role pengguna tidak dikenali.");
                    UserSession.cleanSession();
                }
            } catch (Exception e) {
                AlertUtil.showError("Gagal membuka dashboard: " + e.getMessage());
                e.printStackTrace();
                UserSession.cleanSession();
            }
        } else {
            AlertUtil.showError("Login gagal. Cek username atau password!");
        }
    }
}