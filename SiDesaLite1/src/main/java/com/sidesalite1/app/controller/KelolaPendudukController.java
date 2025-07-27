package com.sidesalite1.app.controller;

import com.sidesalite1.app.MainApp;
import com.sidesalite1.app.dao.PendudukDao;
import com.sidesalite1.app.dao.UserDao;
import com.sidesalite1.app.model.Penduduk;
import com.sidesalite1.app.model.User;
import com.sidesalite1.app.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.Optional;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.application.Platform;


public class KelolaPendudukController {

    @FXML private TableView<Penduduk> pendudukTable;
    @FXML private TableColumn<Penduduk, String> nikColumn;
    @FXML private TableColumn<Penduduk, String> namaColumn;
    @FXML private TableColumn<Penduduk, String> tempatLahirColumn;
    @FXML private TableColumn<Penduduk, LocalDate> tanggalLahirColumn;
    @FXML private TableColumn<Penduduk, String> jenisKelaminColumn;
    @FXML private TableColumn<Penduduk, String> alamatColumn;
    @FXML private TableColumn<Penduduk, String> teleponColumn;
    @FXML private TableColumn<Penduduk, String> emailColumn;
    @FXML private TableColumn<Penduduk, Integer> userIdColumn; // Untuk menampilkan User ID

    @FXML private TextField nikField;
    @FXML private TextField namaField;
    @FXML private TextField tempatLahirField;
    @FXML private DatePicker tanggalLahirPicker;
    @FXML private ComboBox<String> jenisKelaminComboBox;
    @FXML private TextArea alamatField;
    @FXML private TextField teleponField;
    @FXML private TextField emailField;
    
    // Tombol untuk pembuatan akun
    @FXML private Button buatAkunButton;
    @FXML private Button hapusAkunButton;

    private final PendudukDao pendudukDao = new PendudukDao();
    private final UserDao userDao = new UserDao();

    @FXML
    public void initialize() {
        // Inisialisasi ComboBox Jenis Kelamin
        jenisKelaminComboBox.setItems(FXCollections.observableArrayList("Laki-laki", "Perempuan"));

        // Inisialisasi kolom tabel
        nikColumn.setCellValueFactory(new PropertyValueFactory<>("nik"));
        namaColumn.setCellValueFactory(new PropertyValueFactory<>("nama"));
        tempatLahirColumn.setCellValueFactory(new PropertyValueFactory<>("tempatLahir"));
        tanggalLahirColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalLahir"));
        jenisKelaminColumn.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
        alamatColumn.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        teleponColumn.setCellValueFactory(new PropertyValueFactory<>("telepon"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        
        // Listener untuk memilih item di tabel
        pendudukTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showPendudukDetails(newValue));

        loadPendudukData();
    }

    private void loadPendudukData() {
        ObservableList<Penduduk> data = FXCollections.observableArrayList(pendudukDao.getAllPenduduk());
        pendudukTable.setItems(data);
    }

    private void showPendudukDetails(Penduduk penduduk) {
        if (penduduk != null) {
            nikField.setText(penduduk.getNik());
            namaField.setText(penduduk.getNama());
            tempatLahirField.setText(penduduk.getTempatLahir());
            tanggalLahirPicker.setValue(penduduk.getTanggalLahir());
            jenisKelaminComboBox.setValue(penduduk.getJenisKelamin());
            alamatField.setText(penduduk.getAlamat());
            teleponField.setText(penduduk.getTelepon());
            emailField.setText(penduduk.getEmail());
            
            // Atur status tombol buat/hapus akun
            boolean hasAccount = (penduduk.getUserId() != null && penduduk.getUserId() != 0);
            buatAkunButton.setDisable(hasAccount); // Jika sudah ada akun, disable "Buat Akun"
            hapusAkunButton.setDisable(!hasAccount); // Jika belum ada akun, disable "Hapus Akun"
            nikField.setEditable(false); // NIK tidak bisa diedit setelah dipilih
        } else {
            clearFields();
            nikField.setEditable(true); // NIK bisa diedit jika tidak ada yang dipilih (untuk tambah baru)
            buatAkunButton.setDisable(true); // Disable jika tidak ada penduduk terpilih
            hapusAkunButton.setDisable(true);
        }
    }

    @FXML
    private void handleTambah() {
        if (validateInput()) {
            Penduduk newPenduduk = new Penduduk();
            newPenduduk.setNik(nikField.getText());
            newPenduduk.setNama(namaField.getText());
            newPenduduk.setTempatLahir(tempatLahirField.getText());
            newPenduduk.setTanggalLahir(tanggalLahirPicker.getValue());
            newPenduduk.setJenisKelamin(jenisKelaminComboBox.getValue());
            newPenduduk.setAlamat(alamatField.getText());
            newPenduduk.setTelepon(teleponField.getText());
            newPenduduk.setEmail(emailField.getText());
            // userId akan null secara default karena belum ada akun
            
            if (pendudukDao.isNikExists(newPenduduk.getNik())) {
                AlertUtil.showError("NIK ini sudah terdaftar. Gunakan NIK lain atau edit data yang sudah ada.");
                return;
            }

            if (pendudukDao.insert(newPenduduk)) {
                AlertUtil.showInfo("Data penduduk berhasil ditambahkan.");
                loadPendudukData();
                clearFields();
            } else {
                AlertUtil.showError("Gagal menambahkan data penduduk.");
            }
        }
    }

    @FXML
    private void handleUpdate() {
        Penduduk selectedPenduduk = pendudukTable.getSelectionModel().getSelectedItem();
        if (selectedPenduduk != null) {
            if (validateInput()) {
                // NIK tidak diubah karena dijadikan primary key, yang diupdate hanya detailnya
                selectedPenduduk.setNama(namaField.getText());
                selectedPenduduk.setTempatLahir(tempatLahirField.getText());
                selectedPenduduk.setTanggalLahir(tanggalLahirPicker.getValue());
                selectedPenduduk.setJenisKelamin(jenisKelaminComboBox.getValue());
                selectedPenduduk.setAlamat(alamatField.getText());
                selectedPenduduk.setTelepon(teleponField.getText());
                selectedPenduduk.setEmail(emailField.getText());

                if (pendudukDao.update(selectedPenduduk)) {
                    AlertUtil.showInfo("Data penduduk berhasil diperbarui.");
                    loadPendudukData();
                    clearFields();
                } else {
                    AlertUtil.showError("Gagal memperbarui data penduduk.");
                }
            }
        } else {
            AlertUtil.showWarning("Pilih data penduduk yang ingin diperbarui.");
        }
    }

    @FXML
    private void handleHapus() {
        Penduduk selectedPenduduk = pendudukTable.getSelectionModel().getSelectedItem();
        if (selectedPenduduk != null) {
            Optional<ButtonType> result = AlertUtil.showConfirmation(
                "Konfirmasi Hapus",
                "Anda yakin ingin menghapus data penduduk dengan NIK: " + selectedPenduduk.getNik() + "?\n" +
                "Ini juga akan menghapus akun login terkait (jika ada)!"

            );

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Hapus akun user terlebih dahulu jika ada
                if (selectedPenduduk.getUserId() != null && selectedPenduduk.getUserId() != 0) {
                    userDao.delete(selectedPenduduk.getUserId()); // Hapus akun User
                }

                if (pendudukDao.delete(selectedPenduduk.getNik())) {
                    AlertUtil.showInfo("Data penduduk berhasil dihapus.");
                    loadPendudukData();
                    clearFields();
                } else {
                    AlertUtil.showError("Gagal menghapus data penduduk.");
                }
            }
        } else {
            AlertUtil.showWarning("Pilih data penduduk yang ingin dihapus.");
        }
    }
    
    @FXML
    private void handleBuatAkun() {
        Penduduk selectedPenduduk = pendudukTable.getSelectionModel().getSelectedItem();
        if (selectedPenduduk == null) {
            AlertUtil.showWarning("Pilih data penduduk terlebih dahulu untuk membuat akun.");
            return;
        }
        if (selectedPenduduk.getUserId() != null && selectedPenduduk.getUserId() != 0) {
            AlertUtil.showWarning("Penduduk ini sudah memiliki akun.");
            return;
        }

        // Tampilkan dialog input untuk username dan password
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Buat Akun Login untuk " + selectedPenduduk.getNama());
        dialog.setHeaderText("Masukkan Username dan Password untuk akun penduduk.");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Buat Akun", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10)); // Anda perlu import javafx.geometry.Insets

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        PasswordField confirmPassword = new PasswordField();
        confirmPassword.setPromptText("Konfirmasi Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);
        grid.add(new Label("Konfirmasi Password:"), 0, 2);
        grid.add(confirmPassword, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus()); // Anda perlu import javafx.application.Platform

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                if (username.getText().isEmpty() || password.getText().isEmpty() || confirmPassword.getText().isEmpty()) {
                    AlertUtil.showError("Username dan password tidak boleh kosong!");
                    return null;
                }
                if (!password.getText().equals(confirmPassword.getText())) {
                    AlertUtil.showError("Password dan konfirmasi password tidak cocok!");
                    return null;
                }
                // Validasi username unik
                if (userDao.login(username.getText(), "dummy") != null) { // Cek apakah username sudah ada
                     AlertUtil.showError("Username ini sudah digunakan. Harap pilih username lain.");
                     return null;
                }
                return new User(0, username.getText(), password.getText(), "penduduk");
            }
            return null;
        });

        Optional<User> result = dialog.showAndWait();
        
        result.ifPresent(user -> {
            if (userDao.insert(user)) {
                // Setelah user berhasil dibuat, tautkan user_id ke data penduduk
                if (userDao.linkUserToPenduduk(selectedPenduduk.getNik(), user.getId())) {
                    AlertUtil.showInfo("Akun login berhasil dibuat dan ditautkan untuk " + selectedPenduduk.getNama() + ".");
                    loadPendudukData(); // Muat ulang data untuk update userId di tabel
                    showPendudukDetails(selectedPenduduk); // Update tampilan detail
                } else {
                    AlertUtil.showError("Gagal menautkan akun ke data penduduk. Akun dibuat tapi tidak terhubung.");
                    // Opsional: Hapus user yang baru dibuat jika gagal menautkan
                    userDao.delete(user.getId()); 
                }
            } else {
                AlertUtil.showError("Gagal membuat akun login.");
            }
        });
    }

    @FXML
    private void handleHapusAkun() {
        Penduduk selectedPenduduk = pendudukTable.getSelectionModel().getSelectedItem();
        if (selectedPenduduk == null || selectedPenduduk.getUserId() == null || selectedPenduduk.getUserId() == 0) {
            AlertUtil.showWarning("Pilih penduduk yang memiliki akun untuk dihapus.");
            return;
        }

        Optional<ButtonType> result = AlertUtil.showConfirmation(
            "Konfirmasi Hapus Akun",
            "Anda yakin ingin menghapus akun login untuk " + selectedPenduduk.getNama() + " (NIK: " + selectedPenduduk.getNik() + ")?"
       
        );

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (userDao.delete(selectedPenduduk.getUserId())) {
                // Setelah akun User dihapus, lepaskan tautan di Penduduk
                if (userDao.unlinkUserFromPenduduk(selectedPenduduk.getNik())) {
                    AlertUtil.showInfo("Akun login berhasil dihapus dan tautan dilepaskan.");
                    loadPendudukData();
                    showPendudukDetails(selectedPenduduk); // Update tampilan detail
                } else {
                    AlertUtil.showError("Gagal melepaskan tautan akun dari data penduduk.");
                }
            } else {
                AlertUtil.showError("Gagal menghapus akun login.");
            }
        }
    }


    @FXML
    private void handleClearFields() {
        clearFields();
        pendudukTable.getSelectionModel().clearSelection(); // Batalkan pilihan di tabel
    }

    private void clearFields() {
        nikField.clear();
        namaField.clear();
        tempatLahirField.clear();
        tanggalLahirPicker.setValue(null);
        jenisKelaminComboBox.getSelectionModel().clearSelection();
        alamatField.clear();
        teleponField.clear();
        emailField.clear();
        nikField.setEditable(true); // Izinkan NIK diedit lagi untuk entri baru
        buatAkunButton.setDisable(true); // Reset disable
        hapusAkunButton.setDisable(true); // Reset disable
    }

    private boolean validateInput() {
        String errorMessage = "";

        if (nikField.getText() == null || nikField.getText().isEmpty()) {
            errorMessage += "NIK tidak boleh kosong!\n";
        }
        if (namaField.getText() == null || namaField.getText().isEmpty()) {
            errorMessage += "Nama tidak boleh kosong!\n";
        }
        if (tempatLahirField.getText() == null || tempatLahirField.getText().isEmpty()) {
            errorMessage += "Tempat lahir tidak boleh kosong!\n";
        }
        if (tanggalLahirPicker.getValue() == null) {
            errorMessage += "Tanggal lahir tidak boleh kosong!\n";
        }
        if (jenisKelaminComboBox.getValue() == null || jenisKelaminComboBox.getValue().isEmpty()) {
            errorMessage += "Jenis kelamin tidak boleh kosong!\n";
        }
        if (alamatField.getText() == null || alamatField.getText().isEmpty()) {
            errorMessage += "Alamat tidak boleh kosong!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            AlertUtil.showError(errorMessage);
            return false;
        }
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