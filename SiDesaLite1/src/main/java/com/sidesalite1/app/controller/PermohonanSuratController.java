package com.sidesalite1.app.controller;

import com.sidesalite1.app.MainApp;
import com.sidesalite1.app.dao.JenisSuratDao;
import com.sidesalite1.app.dao.PendudukDao; // Perlu untuk mendapatkan NIK penduduk yang login
import com.sidesalite1.app.dao.PermohonanDao;
import com.sidesalite1.app.model.JenisSurat;
import com.sidesalite1.app.model.Penduduk;
import com.sidesalite1.app.model.Permohonan;
import com.sidesalite1.app.util.AlertUtil;
import com.sidesalite1.app.util.UserSession;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.util.List;

public class PermohonanSuratController {

    @FXML private Label nikPemohonLabel; // Label untuk menampilkan NIK pemohon
    @FXML private Label namaPemohonLabel; // Label untuk menampilkan nama pemohon
    @FXML private ComboBox<JenisSurat> jenisSuratComboBox;
    @FXML private TextArea keteranganTextArea;
    @FXML private DatePicker tanggalPermohonanPicker;

    private final PermohonanDao permohonanDao = new PermohonanDao();
    private final JenisSuratDao jenisSuratDao = new JenisSuratDao();
    private final PendudukDao pendudukDao = new PendudukDao(); // Instansiasi PendudukDao

    private String loggedInNik; // Untuk menyimpan NIK dari sesi

    @FXML
    public void initialize() {
        // Ambil NIK penduduk yang sedang login
        loggedInNik = UserSession.getInstance().getLoggedInUserNik();
        if (loggedInNik == null) {
            AlertUtil.showError("NIK pengguna tidak ditemukan. Harap login kembali.");
            // Mungkin arahkan kembali ke login jika NIK tidak ada
            MainApp.setRoot("/view/LoginView.fxml");
            return;
        }

        // Tampilkan NIK dan Nama Pemohon
        Penduduk pemohon = pendudukDao.getPendudukByNik(loggedInNik);
        if (pemohon != null) {
            nikPemohonLabel.setText(loggedInNik);
            namaPemohonLabel.setText(pemohon.getNama());
        } else {
            nikPemohonLabel.setText("N/A");
            namaPemohonLabel.setText("Data Pemohon Tidak Ditemukan");
            AlertUtil.showWarning("Data penduduk Anda tidak ditemukan. Harap hubungi petugas.");
        }

        // Isi ComboBox jenis surat
        List<JenisSurat> jenisSuratList = jenisSuratDao.getAll();
        jenisSuratComboBox.setItems(FXCollections.observableArrayList(jenisSuratList));

        // Set tanggal permohonan ke tanggal hari ini secara default
        tanggalPermohonanPicker.setValue(LocalDate.now());
        // Nonaktifkan DatePicker agar tidak bisa diubah oleh user (permohonan selalu hari ini)
        tanggalPermohonanPicker.setDisable(true);
    }

    @FXML
    private void handleAjukanPermohonan() {
        if (loggedInNik == null) {
            AlertUtil.showError("Sesi pengguna tidak valid. Harap login kembali.");
            return;
        }

        JenisSurat selectedJenisSurat = jenisSuratComboBox.getValue();
        String keterangan = keteranganTextArea.getText().trim();
        LocalDate tanggalPermohonan = tanggalPermohonanPicker.getValue();

        // Validasi input
        if (selectedJenisSurat == null) {
            AlertUtil.showWarning("Pilih jenis surat.");
            return;
        }
        if (keterangan.isEmpty()) {
            AlertUtil.showWarning("Keterangan permohonan tidak boleh kosong.");
            return;
        }
        if (tanggalPermohonan == null) {
            AlertUtil.showWarning("Tanggal permohonan tidak boleh kosong.");
            return;
        }

        // Buat objek Permohonan baru
        Permohonan newPermohonan = new Permohonan(
            0, // ID akan di-generate otomatis
            loggedInNik,
            selectedJenisSurat.getId(),
            keterangan,
            tanggalPermohonan,
            "Menunggu" // Status awal selalu "Menunggu"
        );

        // Simpan ke database
        if (permohonanDao.insert(newPermohonan)) {
            AlertUtil.showInfo("Permohonan surat berhasil diajukan! Status: Menunggu.");
            clearFields();
        } else {
            AlertUtil.showError("Gagal mengajukan permohonan surat.");
        }
    }

    @FXML
    private void handleClearFields() {
        clearFields();
    }

    private void clearFields() {
        jenisSuratComboBox.getSelectionModel().clearSelection();
        keteranganTextArea.clear();
        tanggalPermohonanPicker.setValue(LocalDate.now()); // Reset ke tanggal hari ini
    }

    @FXML
    private void handleKembali() {
        try {
            MainApp.setRoot("/view/PendudukDashboardView.fxml");
        } catch (Exception e) {
            AlertUtil.showError("Gagal kembali ke dashboard penduduk.");
        }
    }
}