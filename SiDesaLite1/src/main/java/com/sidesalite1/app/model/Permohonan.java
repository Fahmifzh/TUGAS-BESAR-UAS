package com.sidesalite1.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;

public class Permohonan {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nik = new SimpleStringProperty(); // NIK of the applicant
    private final IntegerProperty idJenisSurat = new SimpleIntegerProperty(); // Foreign key to JenisSurat
    private final StringProperty keterangan = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> tanggalPermohonan = new SimpleObjectProperty<>();
    private final StringProperty status = new SimpleStringProperty(); // e.g., "Menunggu", "Disetujui", "Ditolak"

    public Permohonan() {
    }

    public Permohonan(int id, String nik, int idJenisSurat, String keterangan, LocalDate tanggalPermohonan, String status) {
        setId(id);
        setNik(nik);
        setIdJenisSurat(idJenisSurat);
        setKeterangan(keterangan);
        setTanggalPermohonan(tanggalPermohonan);
        setStatus(status);
    }

    // Getters
    public int getId() {
        return id.get();
    }

    public String getNik() {
        return nik.get();
    }

    public int getIdJenisSurat() {
        return idJenisSurat.get();
    }

    public String getKeterangan() {
        return keterangan.get();
    }

    public LocalDate getTanggalPermohonan() {
        return tanggalPermohonan.get();
    }

    public String getStatus() {
        return status.get();
    }

    // Setters
    public void setId(int id) {
        this.id.set(id);
    }

    public void setNik(String nik) {
        this.nik.set(nik);
    }

    public void setIdJenisSurat(int idJenisSurat) {
        this.idJenisSurat.set(idJenisSurat);
    }

    public void setKeterangan(String keterangan) {
        this.keterangan.set(keterangan);
    }

    public void setTanggalPermohonan(LocalDate tanggalPermohonan) {
        this.tanggalPermohonan.set(tanggalPermohonan);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    // Property methods for JavaFX TableView
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nikProperty() {
        return nik;
    }

    public IntegerProperty idJenisSuratProperty() {
        return idJenisSurat;
    }

    public StringProperty keteranganProperty() {
        return keterangan;
    }

    public ObjectProperty<LocalDate> tanggalPermohonanProperty() {
        return tanggalPermohonan;
    }

    public StringProperty statusProperty() {
        return status;
    }
}