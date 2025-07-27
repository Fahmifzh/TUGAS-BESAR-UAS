package com.sidesalite1.app.controller;

import com.sidesalite1.app.MainApp;
import com.sidesalite1.app.dao.PendudukDao;
import com.sidesalite1.app.dao.SuratDao;
import com.sidesalite1.app.model.Penduduk;
import com.sidesalite1.app.model.Surat;
import com.sidesalite1.app.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.Optional;

public class KelolaSuratController {

    @FXML private TableView<Surat> suratTable;
    @FXML private TableColumn<Surat, Integer> idColumn;
    @FXML private TableColumn<Surat, Integer> permohonanIdColumn;
    @FXML private TableColumn<Surat, String> nikColumn;
    @FXML private TableColumn<Surat, String> namaPemilikColumn; // Nama pemilik surat (dari data penduduk)
    @FXML private TableColumn<Surat, String> jenisColumn;
    @FXML private TableColumn<Surat, LocalDate> tanggalTerbitColumn;

    @FXML private Label idSuratLabel;
    @FXML private Label permohonanIdLabel;
    @FXML private Label nikPemilikLabel;
    @FXML private Label namaPemilikSuratLabel;
    @FXML private Label jenisSuratLabel;
    @FXML private DatePicker tanggalTerbitPicker;
    @FXML private TextArea kontenSuratTextArea; // Untuk menampilkan atau mengedit konten surat

    private final SuratDao suratDao = new SuratDao();
    private final PendudukDao pendudukDao = new PendudukDao();

    @FXML
    public void initialize() {
        // Inisialisasi kolom tabel
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        permohonanIdColumn.setCellValueFactory(new PropertyValueFactory<>("permohonanId"));
        nikColumn.setCellValueFactory(new PropertyValueFactory<>("nik"));
        jenisColumn.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        tanggalTerbitColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalTerbit"));

        // Kolom khusus untuk Nama Pemilik Surat (membutuhkan custom CellValueFactory)
        namaPemilikColumn.setCellValueFactory(cellData -> {
            String nik = cellData.getValue().getNik();
            Penduduk p = pendudukDao.getPendudukByNik(nik);
            return new TableCell<Surat, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || p == null) {
                        setText(null);
                    } else {
                        setText(p.getNama());
                    }
                }
            }.textProperty(); // Menggunakan textProperty dari TableCell
        });

        // Listener untuk memilih item di tabel
        suratTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showSuratDetails(newValue));

        loadSuratData();
    }

    private void loadSuratData() {
        ObservableList<Surat> data = FXCollections.observableArrayList(suratDao.getAllSurat());
        suratTable.setItems(data);
    }

    private void showSuratDetails(Surat surat) {
        if (surat != null) {
            idSuratLabel.setText(String.valueOf(surat.getId()));
            permohonanIdLabel.setText(String.valueOf(surat.getPermohonanId()));
            nikPemilikLabel.setText(surat.getNik());

            // Dapatkan nama pemilik surat dari NIK
            Penduduk pemilik = pendudukDao.getPendudukByNik(surat.getNik());
            namaPemilikSuratLabel.setText(pemilik != null ? pemilik.getNama() : "Tidak Ditemukan");

            jenisSuratLabel.setText(surat.getJenis());
            tanggalTerbitPicker.setValue(surat.getTanggalTerbit());
            kontenSuratTextArea.setText(surat.getKontenSurat());
            kontenSuratTextArea.setEditable(true); // Bolehkan edit konten surat
        } else {
            clearFields();
            kontenSuratTextArea.setEditable(false);
        }
    }
    
    @FXML
    private void handleUpdateSurat() {
        Surat selectedSurat = suratTable.getSelectionModel().getSelectedItem();
        if (selectedSurat != null) {
            // Kita hanya akan mengizinkan pengeditan konten surat
            selectedSurat.setKontenSurat(kontenSuratTextArea.getText());
            selectedSurat.setTanggalTerbit(tanggalTerbitPicker.getValue()); // Juga izinkan update tanggal terbit
            
            if (suratDao.update(selectedSurat)) {
                AlertUtil.showInfo("Detail surat berhasil diperbarui.");
                loadSuratData(); // Muat ulang data untuk refleksi perubahan
                showSuratDetails(selectedSurat); // Refresh detail
            } else {
                AlertUtil.showError("Gagal memperbarui detail surat.");
            }
        } else {
            AlertUtil.showWarning("Pilih surat yang ingin diperbarui.");
        }
    }

    @FXML
    private void handleHapusSurat() {
        Surat selectedSurat = suratTable.getSelectionModel().getSelectedItem();
        if (selectedSurat != null) {
            Optional<ButtonType> result = AlertUtil.showConfirmation(
                "Konfirmasi Hapus Surat",
                "Anda yakin ingin menghapus surat ini (ID: " + selectedSurat.getId() + ")?"
               
            );

            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (suratDao.delete(selectedSurat.getId())) {
                    AlertUtil.showInfo("Surat berhasil dihapus.");
                    loadSuratData();
                    clearFields();
                } else {
                    AlertUtil.showError("Gagal menghapus surat.");
                }
            }
        } else {
            AlertUtil.showWarning("Pilih surat yang ingin dihapus.");
        }
    }

    @FXML
    private void handleClearFields() {
        clearFields();
        suratTable.getSelectionModel().clearSelection();
    }

    private void clearFields() {
        idSuratLabel.setText("-");
        permohonanIdLabel.setText("-");
        nikPemilikLabel.setText("-");
        namaPemilikSuratLabel.setText("-");
        jenisSuratLabel.setText("-");
        tanggalTerbitPicker.setValue(null);
        kontenSuratTextArea.clear();
        kontenSuratTextArea.setEditable(false);
    }

    @FXML
    private void handleKembali() {
        try {
            MainApp.setRoot("/view/PetugasDashboardView.fxml");
        } catch (Exception e) {
            AlertUtil.showError("Gagal kembali ke dashboard petugas.");
        }
    }
}