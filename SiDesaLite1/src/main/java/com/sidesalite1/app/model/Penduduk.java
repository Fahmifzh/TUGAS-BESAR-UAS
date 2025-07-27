package com.sidesalite1.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;

public class Penduduk {
    private final StringProperty nik = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty tempatLahir = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> tanggalLahir = new SimpleObjectProperty<>();
    private final StringProperty jenisKelamin = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty telepon = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final IntegerProperty userId = new SimpleIntegerProperty(); // Foreign key to Users table

    public Penduduk() {
    
        userId.set(0);
    }

    public Penduduk(String nik, String nama, String tempatLahir, LocalDate tanggalLahir,
                    String jenisKelamin, String alamat, String telepon, String email, Integer userId) {
        setNik(nik);
        setNama(nama);
        setTempatLahir(tempatLahir);
        setTanggalLahir(tanggalLahir);
        setJenisKelamin(jenisKelamin);
        setAlamat(alamat);
        setTelepon(telepon);
        setEmail(email);
        // Handle null userId from database by setting it to 0 or a specific indicator
        if (userId != null) {
            setUserId(userId);
        } else {
            setUserId(0); // Indicate no user linked
        }
    }

    // Getters
    public String getNik() {
        return nik.get();
    }

    public String getNama() {
        return nama.get();
    }

    public String getTempatLahir() {
        return tempatLahir.get();
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir.get();
    }

    public String getJenisKelamin() {
        return jenisKelamin.get();
    }

    public String getAlamat() {
        return alamat.get();
    }

    public String getTelepon() {
        return telepon.get();
    }

    public String getEmail() {
        return email.get();
    }

    public Integer getUserId() {
        // Return null if userId is 0, to match database NULL concept for foreign key
        return userId.get() == 0 ? null : userId.get();
    }

    // Setters
    public void setNik(String nik) {
        this.nik.set(nik);
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir.set(tempatLahir);
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir.set(tanggalLahir);
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin.set(jenisKelamin);
    }

    public void setAlamat(String alamat) {
        this.alamat.set(alamat);
    }

    public void setTelepon(String telepon) {
        this.telepon.set(telepon);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setUserId(Integer userId) {
        // Handle null input by setting to 0
        this.userId.set(userId == null ? 0 : userId);
    }

    // Property methods for JavaFX TableView
    public StringProperty nikProperty() {
        return nik;
    }

    public StringProperty namaProperty() {
        return nama;
    }

    public StringProperty tempatLahirProperty() {
        return tempatLahir;
    }

    public ObjectProperty<LocalDate> tanggalLahirProperty() {
        return tanggalLahir;
    }

    public StringProperty jenisKelaminProperty() {
        return jenisKelamin;
    }

    public StringProperty alamatProperty() {
        return alamat;
    }

    public StringProperty teleponProperty() {
        return telepon;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    // Override toString for ComboBox or debugging
    @Override
    public String toString() {
        return getNama() + " (" + getNik() + ")";
    }
}