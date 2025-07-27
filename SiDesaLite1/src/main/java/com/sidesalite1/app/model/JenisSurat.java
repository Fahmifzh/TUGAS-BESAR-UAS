package com.sidesalite1.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class JenisSurat {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nama = new SimpleStringProperty();

    public JenisSurat() {
    }

    public JenisSurat(int id, String nama) {
        setId(id);
        setNama(nama);
    }

    // Getters
    public int getId() {
        return id.get();
    }

    public String getNama() {
        return nama.get();
    }

    // Setters
    public void setId(int id) {
        this.id.set(id);
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }

    // Property methods for JavaFX TableView
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty namaProperty() {
        return nama;
    }

    // Override toString for ComboBox
    @Override
    public String toString() {
        return getNama();
    }
}