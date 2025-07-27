package com.sidesalite1.app.util;

import com.sidesalite1.app.model.User;

public class UserSession {

    private static UserSession instance;

    private User loggedInUser;
    private String loggedInUserNik; // NIK penduduk yang login (null untuk admin/petugas)

    private UserSession(User user) {
        this.loggedInUser = user;
    }

    /**
     * Menginisialisasi sesi pengguna baru.
     * @param user Objek User yang baru saja login.
     * @return Instance UserSession.
     */
    public static UserSession getInstance(User user) {
        if (instance == null) {
            instance = new UserSession(user);
        } else {
            // Jika ada sesi aktif, perbarui pengguna yang login
            instance.loggedInUser = user;
            instance.loggedInUserNik = null; // Reset NIK jika user berubah
        }
        return instance;
    }

    /**
     * Mendapatkan instance sesi pengguna yang sedang aktif.
     * @return Instance UserSession, atau null jika belum ada yang login.
     */
    public static UserSession getInstance() {
        return instance;
    }

    /**
     * Membersihkan sesi (logout).
     */
    public static void cleanSession() {
        instance = null; // Menghapus instance sesi
    }

    // --- Getters untuk informasi pengguna yang login ---

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public int getUserId() {
        return loggedInUser != null ? loggedInUser.getId() : 0;
    }

    public String getUsername() {
        return loggedInUser != null ? loggedInUser.getUsername() : null;
    }

    public String getRole() {
        return loggedInUser != null ? loggedInUser.getRole() : null;
    }

    // --- Getter/Setter untuk NIK penduduk yang login ---

    public String getLoggedInUserNik() {
        return loggedInUserNik;
    }

    public void setLoggedInUserNik(String loggedInUserNik) {
        this.loggedInUserNik = loggedInUserNik;
    }

    /**
     * Memeriksa apakah pengguna saat ini adalah penduduk.
     * @return true jika role adalah 'penduduk', false selain itu.
     */
    public boolean isPenduduk() {
        return loggedInUser != null && "penduduk".equalsIgnoreCase(loggedInUser.getRole());
    }

    /**
     * Memeriksa apakah pengguna saat ini adalah admin.
     * @return true jika role adalah 'admin', false selain itu.
     */
    public boolean isAdmin() {
        return loggedInUser != null && "admin".equalsIgnoreCase(loggedInUser.getRole());
    }

    /**
     * Memeriksa apakah pengguna saat ini adalah petugas.
     * @return true jika role adalah 'petugas', false selain itu.
     */
    public boolean isPetugas() {
        return loggedInUser != null && "petugas".equalsIgnoreCase(loggedInUser.getRole());
    }
}