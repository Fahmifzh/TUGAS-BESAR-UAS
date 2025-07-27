package com.sidesalite1.app.controller;

import com.sidesalite1.app.MainApp;
import com.sidesalite1.app.dao.JenisSuratDao;
import com.sidesalite1.app.dao.PermohonanDao;
import com.sidesalite1.app.dao.SuratDao;
import com.sidesalite1.app.model.JenisSurat;
import com.sidesalite1.app.model.Permohonan;
import com.sidesalite1.app.model.Surat;
import com.sidesalite1.app.util.AlertUtil;
import com.sidesalite1.app.util.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

public class StatusPermohonanPendudukController {

    @FXML private TableView<Permohonan> permohonanTable;
    @FXML private TableColumn<Permohonan, Integer> idColumn;
    @FXML private TableColumn<Permohonan, Integer> idJenisSuratColumn; // Akan menampilkan ID jenis surat
    @FXML private TableColumn<Permohonan, String> namaJenisSuratColumn; // Nama jenis surat
    @FXML private TableColumn<Permohonan, String> keteranganColumn;
    @FXML private TableColumn<Permohonan, LocalDate> tanggalPermohonanColumn;
    @FXML private TableColumn<Permohonan, String> statusColumn;

    @FXML private Label idPermohonanDetailLabel;
    @FXML private Label jenisSuratDetailLabel;
    @FXML private TextArea keteranganDetailTextArea;
    @FXML private Label tanggalPermohonanDetailLabel;
    @FXML private Label statusDetailLabel;
    
    @FXML private Button lihatSuratButton; // Tombol untuk melihat surat yang diterbitkan

    @FXML private TextArea kontenSuratTextArea; // Untuk menampilkan konten surat yang diterbitkan
    @FXML private TitledPane suratTitledPane; // Pane yang akan disembunyikan/ditampilkan

    private final PermohonanDao permohonanDao = new PermohonanDao();
    private final JenisSuratDao jenisSuratDao = new JenisSuratDao();
    private final SuratDao suratDao = new SuratDao();

    @FXML
    public void initialize() {
        // Inisialisasi kolom tabel
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idJenisSuratColumn.setCellValueFactory(new PropertyValueFactory<>("idJenisSurat")); // Tampilkan ID jenis surat

        // Kolom untuk nama jenis surat (perlu custom CellValueFactory)
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
            }.textProperty();
        });

        keteranganColumn.setCellValueFactory(new PropertyValueFactory<>("keterangan"));
        tanggalPermohonanColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalPermohonan"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Listener untuk memilih item di tabel
        permohonanTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showPermohonanDetails(newValue));

        // Sembunyikan pane surat secara default
        suratTitledPane.setVisible(false);
        suratTitledPane.setManaged(false); // Penting untuk layout agar tidak memakan ruang

        loadPermohonanData();
    }

    private void loadPermohonanData() {
        String nikPengguna = UserSession.getInstance().getLoggedInUserNik();
        if (nikPengguna == null) {
            AlertUtil.showError("NIK pengguna tidak ditemukan dalam sesi. Harap login ulang.");
            return;
        }
        ObservableList<Permohonan> data = FXCollections.observableArrayList(permohonanDao.getPermohonanByNik(nikPengguna));
        permohonanTable.setItems(data);
    }

    private void showPermohonanDetails(Permohonan permohonan) {
        if (permohonan != null) {
            idPermohonanDetailLabel.setText(String.valueOf(permohonan.getId()));
            
            JenisSurat jenis = jenisSuratDao.getById(permohonan.getIdJenisSurat());
            jenisSuratDetailLabel.setText(jenis != null ? jenis.getNama() : "Tidak Dikenal");

            keteranganDetailTextArea.setText(permohonan.getKeterangan());
            tanggalPermohonanDetailLabel.setText(permohonan.getTanggalPermohonan() != null ? permohonan.getTanggalPermohonan().toString() : "-");
            statusDetailLabel.setText(permohonan.getStatus());

            // Atur visibilitas tombol "Lihat Surat" dan panel surat
            boolean isApproved = "Disetujui".equals(permohonan.getStatus());
            Surat existingSurat = suratDao.getSuratByPermohonanId(permohonan.getId());
            
            if (isApproved && existingSurat != null) {
                // Jika sudah disetujui dan surat ada, aktifkan tombol dan panel
                lihatSuratButton.setDisable(false);
                suratTitledPane.setVisible(true);
                suratTitledPane.setManaged(true);
                kontenSuratTextArea.setText(existingSurat.getKontenSurat());
            } else {
                // Sembunyikan tombol dan panel jika tidak disetujui atau surat belum ada
                lihatSuratButton.setDisable(true);
                suratTitledPane.setVisible(false);
                suratTitledPane.setManaged(false);
                kontenSuratTextArea.clear();
            }

        } else {
            clearFields();
            suratTitledPane.setVisible(false);
            suratTitledPane.setManaged(false);
            lihatSuratButton.setDisable(true);
        }
    }

    @FXML
    private void handleLihatSurat() {
        Permohonan selectedPermohonan = permohonanTable.getSelectionModel().getSelectedItem();
        if (selectedPermohonan != null && "Disetujui".equals(selectedPermohonan.getStatus())) {
            Surat surat = suratDao.getSuratByPermohonanId(selectedPermohonan.getId());
            if (surat != null) {
                // Konten surat sudah ditampilkan otomatis di showPermohonanDetails
                // Anda bisa menambahkan dialog pop-up terpisah di sini jika diinginkan
                AlertUtil.showInfo("Detail Surat Diterbitkan", "Konten Surat:\n\n" + surat.getKontenSurat());
            } else {
                AlertUtil.showWarning("Surat belum diterbitkan untuk permohonan ini.");
            }
        } else {
            AlertUtil.showWarning("Pilih permohonan yang berstatus 'Disetujui' untuk melihat surat.");
        }
    }

    private void clearFields() {
        idPermohonanDetailLabel.setText("-");
        jenisSuratDetailLabel.setText("-");
        keteranganDetailTextArea.clear();
        tanggalPermohonanDetailLabel.setText("-");
        statusDetailLabel.setText("-");
        kontenSuratTextArea.clear();
        lihatSuratButton.setDisable(true);
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