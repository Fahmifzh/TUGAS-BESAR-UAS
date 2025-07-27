package com.sidesalite1.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;

public class Surat {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty permohonanId = new SimpleIntegerProperty(); // Foreign key to Permohonan
    private final StringProperty nik = new SimpleStringProperty(); // NIK of the recipient
    private final StringProperty jenis = new SimpleStringProperty(); // Name of the letter type
    private final ObjectProperty<LocalDate> tanggalTerbit = new SimpleObjectProperty<>();
    private final StringProperty kontenSurat = new SimpleStringProperty(); // Content of the letter

    public Surat() {
    }

    public Surat(int id, int permohonanId, String nik, String jenis, LocalDate tanggalTerbit, String kontenSurat) {
        setId(id);
        setPermohonanId(permohonanId);
        setNik(nik);
        setJenis(jenis);
        setTanggalTerbit(tanggalTerbit);
        setKontenSurat(kontenSurat);
    }

    // Getters
    public int getId() {
        return id.get();
    }

    public int getPermohonanId() {
        return permohonanId.get();
    }

    public String getNik() {
        return nik.get();
    }

    public String getJenis() {
        return jenis.get();
    }

    public LocalDate getTanggalTerbit() {
        return tanggalTerbit.get();
    }

    public String getKontenSurat() {
        return kontenSurat.get();
    }

    // Setters
    public void setId(int id) {
        this.id.set(id);
    }

    public void setPermohonanId(int permohonanId) {
        this.permohonanId.set(permohonanId);
    }

    public void setNik(String nik) {
        this.nik.set(nik);
    }

    public void setJenis(String jenis) {
        this.jenis.set(jenis);
    }

    public void setTanggalTerbit(LocalDate tanggalTerbit) {
        this.tanggalTerbit.set(tanggalTerbit);
    }

    public void setKontenSurat(String kontenSurat) {
        this.kontenSurat.set(kontenSurat);
    }

    // Property methods for JavaFX TableView
    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty permohonanIdProperty() {
        return permohonanId;
    }

    public StringProperty nikProperty() {
        return nik;
    }

    public StringProperty jenisProperty() {
        return jenis;
    }

    public ObjectProperty<LocalDate> tanggalTerbitProperty() {
        return tanggalTerbit;
    }

    public StringProperty kontenSuratProperty() {
        return kontenSurat;
    }
}