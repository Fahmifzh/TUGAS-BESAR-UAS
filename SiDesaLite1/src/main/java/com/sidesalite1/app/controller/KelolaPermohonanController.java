package com.sidesalite1.app.controller;

import com.sidesalite1.app.MainApp;
import com.sidesalite1.app.dao.JenisSuratDao;
import com.sidesalite1.app.dao.PendudukDao;
import com.sidesalite1.app.dao.PermohonanDao;
import com.sidesalite1.app.dao.SuratDao;
import com.sidesalite1.app.model.JenisSurat;
import com.sidesalite1.app.model.Penduduk;
import com.sidesalite1.app.model.Permohonan;
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

public class KelolaPermohonanController {

    @FXML private TableView<Permohonan> permohonanTable;
    @FXML private TableColumn<Permohonan, Integer> idColumn;
    @FXML private TableColumn<Permohonan, String> nikColumn;
    @FXML private TableColumn<Permohonan, Integer> idJenisSuratColumn; // Tampilkan ID
    @FXML private TableColumn<Permohonan, String> namaJenisSuratColumn; // Akan ditampilkan dari join
    @FXML private TableColumn<Permohonan, String> keteranganColumn;
    @FXML private TableColumn<Permohonan, LocalDate> tanggalPermohonanColumn;
    @FXML private TableColumn<Permohonan, String> statusColumn;

    @FXML private Label nikLabel;
    @FXML private Label namaPemohonLabel;
    @FXML private Label jenisSuratLabel;
    @FXML private TextArea keteranganTextArea;
    @FXML private DatePicker tanggalPermohonanPicker;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private Button prosesButton; // Tombol untuk memproses jadi surat

    private final PermohonanDao permohonanDao = new PermohonanDao();
    private final PendudukDao pendudukDao = new PendudukDao(); // Untuk mendapatkan nama pemohon
    private final JenisSuratDao jenisSuratDao = new JenisSuratDao(); // Untuk mendapatkan nama jenis surat
    private final SuratDao suratDao = new SuratDao(); // Untuk membuat surat baru

    @FXML
    public void initialize() {
        // Inisialisasi ComboBox Status
        statusComboBox.setItems(FXCollections.observableArrayList("Menunggu", "Diproses", "Disetujui", "Ditolak"));

        // Inisialisasi kolom tabel
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nikColumn.setCellValueFactory(new PropertyValueFactory<>("nik"));
        idJenisSuratColumn.setCellValueFactory(new PropertyValueFactory<>("idJenisSurat")); // ID Jenis Surat

        // Kolom khusus untuk Nama Jenis Surat (membutuhkan custom CellValueFactory atau mapping manual)
        namaJenisSuratColumn.setCellValueFactory(cellData -> {
            int idJenis = cellData.getValue().getIdJenisSurat();
            JenisSurat js = jenisSuratDao.getById(idJenis);
            return new TableCell<Permohonan, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || js == null) {
                        setText(null);
                    } else {
                        setText(js.getNama());
                    }
                }
            }.textProperty(); // Menggunakan textProperty dari TableCell
        });
        
        keteranganColumn.setCellValueFactory(new PropertyValueFactory<>("keterangan"));
        tanggalPermohonanColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalPermohonan"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Listener untuk memilih item di tabel
        permohonanTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showPermohonanDetails(newValue));
        
        loadPermohonanData();
    }

    private void loadPermohonanData() {
        ObservableList<Permohonan> data = FXCollections.observableArrayList(permohonanDao.getAllPermohonan());
        permohonanTable.setItems(data);
    }

    private void showPermohonanDetails(Permohonan permohonan) {
        if (permohonan != null) {
            nikLabel.setText(permohonan.getNik());
            
            // Dapatkan nama pemohon dari NIK
            Penduduk pemohon = pendudukDao.getPendudukByNik(permohonan.getNik());
            namaPemohonLabel.setText(pemohon != null ? pemohon.getNama() : "Tidak Ditemukan");

            // Dapatkan nama jenis surat dari ID
            JenisSurat jenis = jenisSuratDao.getById(permohonan.getIdJenisSurat());
            jenisSuratLabel.setText(jenis != null ? jenis.getNama() : "Tidak Dikenal");

            keteranganTextArea.setText(permohonan.getKeterangan());
            tanggalPermohonanPicker.setValue(permohonan.getTanggalPermohonan());
            statusComboBox.setValue(permohonan.getStatus());

            // Aktifkan/nonaktifkan tombol Proses berdasarkan status
            prosesButton.setDisable(!("Disetujui".equals(permohonan.getStatus()) && suratDao.getSuratByPermohonanId(permohonan.getId()) == null));

        } else {
            clearFields();
            prosesButton.setDisable(true);
        }
    }

    @FXML
    private void handleUpdateStatus() {
        Permohonan selectedPermohonan = permohonanTable.getSelectionModel().getSelectedItem();
        if (selectedPermohonan != null) {
            String newStatus = statusComboBox.getValue();
            if (newStatus == null || newStatus.isEmpty()) {
                AlertUtil.showWarning("Pilih status baru.");
                return;
            }

            if (permohonanDao.updateStatus(selectedPermohonan.getId(), newStatus)) {
                AlertUtil.showInfo("Status permohonan berhasil diperbarui menjadi: " + newStatus);
                loadPermohonanData(); // Muat ulang data
                showPermohonanDetails(selectedPermohonan); // Perbarui tampilan detail
            } else {
                AlertUtil.showError("Gagal memperbarui status permohonan.");
            }
        } else {
            AlertUtil.showWarning("Pilih permohonan yang ingin diperbarui statusnya.");
        }
    }
    
    @FXML
    private void handleProsesSurat() {
        Permohonan selectedPermohonan = permohonanTable.getSelectionModel().getSelectedItem();
        if (selectedPermohonan == null) {
            AlertUtil.showWarning("Pilih permohonan yang ingin diproses.");
            return;
        }

        if (!"Disetujui".equals(selectedPermohonan.getStatus())) {
            AlertUtil.showWarning("Hanya permohonan dengan status 'Disetujui' yang bisa diproses menjadi surat.");
            return;
        }
        
        if (suratDao.getSuratByPermohonanId(selectedPermohonan.getId()) != null) {
            AlertUtil.showWarning("Surat untuk permohonan ini sudah diterbitkan.");
            return;
        }

        Optional<ButtonType> result = AlertUtil.showConfirmation(
            "Konfirmasi Proses Surat",
            "Anda yakin ingin memproses permohonan ini menjadi surat?"
  );

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Dapatkan jenis surat
            JenisSurat jenis = jenisSuratDao.getById(selectedPermohonan.getIdJenisSurat());
            String jenisNama = (jenis != null) ? jenis.getNama() : "Tidak Dikenal";
            
            // Anda bisa membuat template konten surat di sini atau di database
            String kontenSurat = "Dengan ini menyatakan bahwa " + namaPemohonLabel.getText() +
                                 " dengan NIK " + nikLabel.getText() +
                                 " telah mengajukan " + jenisNama +
                                 " dengan keterangan: " + keteranganTextArea.getText() +
                                 ". Surat ini diterbitkan pada tanggal " + LocalDate.now() + ".";

            Surat newSurat = new Surat(
                0, // ID akan di-generate otomatis
                selectedPermohonan.getId(),
                selectedPermohonan.getNik(),
                jenisNama,
                LocalDate.now(), // Tanggal terbit adalah hari ini
                kontenSurat
            );

            if (suratDao.insert(newSurat)) {
                AlertUtil.showInfo("Surat berhasil diterbitkan!");
                // Opsional: Perbarui status permohonan menjadi "Selesai" atau "Diterbitkan" jika ada status tersebut
                // permohonanDao.updateStatus(selectedPermohonan.getId(), "Diterbitkan"); 
                loadPermohonanData();
                showPermohonanDetails(selectedPermohonan); // Refresh details and button state
            } else {
                AlertUtil.showError("Gagal menerbitkan surat.");
            }
        }
    }

    @FXML
    private void handleClearFields() {
        clearFields();
        permohonanTable.getSelectionModel().clearSelection();
    }

    private void clearFields() {
        nikLabel.setText("-");
        namaPemohonLabel.setText("-");
        jenisSuratLabel.setText("-");
        keteranganTextArea.clear();
        tanggalPermohonanPicker.setValue(null);
        statusComboBox.getSelectionModel().clearSelection();
        prosesButton.setDisable(true);
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