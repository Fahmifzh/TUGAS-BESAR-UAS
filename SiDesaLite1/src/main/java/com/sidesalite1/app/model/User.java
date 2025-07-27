package com.sidesalite1.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty role = new SimpleStringProperty(); // e.g., "admin", "petugas", "penduduk"

    public User() {
    }

    public User(int id, String username, String password, String role) {
        setId(id);
        setUsername(username);
        setPassword(password);
        setRole(role);
    }

    // Getter
    public int getId() {
        return id.get();
    }

    public String getUsername() {
        return username.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getRole() {
        return role.get();
    }

    // Setter
    public void setId(int id) {
        this.id.set(id);
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    // Property methods for JavaFX TableView
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty roleProperty() {
        return role;
    }
}