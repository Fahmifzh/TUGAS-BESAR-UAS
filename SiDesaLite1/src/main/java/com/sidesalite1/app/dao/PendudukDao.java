package com.sidesalite1.app.dao;

import com.sidesalite1.app.model.Penduduk;
import com.sidesalite1.app.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PendudukDao {

    /**
     * Menyimpan data penduduk baru ke database.
     * @param p Objek Penduduk yang akan disimpan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean insert(Penduduk p) {
        String sql = "INSERT INTO Penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, alamat, telepon, email, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNik());
            stmt.setString(2, p.getNama());
            stmt.setString(3, p.getTempatLahir());
            stmt.setString(4, p.getTanggalLahir() != null ? p.getTanggalLahir().toString() : null); // Convert LocalDate to String
            stmt.setString(5, p.getJenisKelamin());
            stmt.setString(6, p.getAlamat());
            stmt.setString(7, p.getTelepon());
            stmt.setString(8, p.getEmail());
            // Set user_id. Jika getUserId() mengembalikan null, set ke SQL NULL
            if (p.getUserId() != null) {
                stmt.setInt(9, p.getUserId());
            } else {
                stmt.setNull(9, java.sql.Types.INTEGER);
            }

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error saat insert penduduk: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua data penduduk dari database.
     * @return List objek Penduduk.
     */
    public List<Penduduk> getAllPenduduk() {
        List<Penduduk> pendudukList = new ArrayList<>();
        String sql = "SELECT nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, alamat, telepon, email, user_id FROM Penduduk ORDER BY nama ASC";
        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Penduduk p = new Penduduk();
                p.setNik(rs.getString("nik"));
                p.setNama(rs.getString("nama"));
                p.setTempatLahir(rs.getString("tempat_lahir"));
                String tanggalLahirStr = rs.getString("tanggal_lahir");
                p.setTanggalLahir(tanggalLahirStr != null ? LocalDate.parse(tanggalLahirStr) : null);
                p.setJenisKelamin(rs.getString("jenis_kelamin"));
                p.setAlamat(rs.getString("alamat"));
                p.setTelepon(rs.getString("telepon"));
                p.setEmail(rs.getString("email"));
                
                // Mendapatkan user_id dan menanganinya jika NULL
                int userId = rs.getInt("user_id");
                if (rs.wasNull()) {
                    p.setUserId(null); // Set ke null jika nilai dari database adalah NULL
                } else {
                    p.setUserId(userId);
                }
                pendudukList.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua penduduk: " + e.getMessage());
            e.printStackTrace();
        }
        return pendudukList;
    }

    /**
     * Mengambil data penduduk berdasarkan NIK.
     * @param nik NIK penduduk yang akan diambil.
     * @return Objek Penduduk jika ditemukan, null jika tidak.
     */
    public Penduduk getPendudukByNik(String nik) {
        String sql = "SELECT nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, alamat, telepon, email, user_id FROM Penduduk WHERE nik = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nik);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Penduduk p = new Penduduk();
                p.setNik(rs.getString("nik"));
                p.setNama(rs.getString("nama"));
                p.setTempatLahir(rs.getString("tempat_lahir"));
                String tanggalLahirStr = rs.getString("tanggal_lahir");
                p.setTanggalLahir(tanggalLahirStr != null ? LocalDate.parse(tanggalLahirStr) : null);
                p.setJenisKelamin(rs.getString("jenis_kelamin"));
                p.setAlamat(rs.getString("alamat"));
                p.setTelepon(rs.getString("telepon"));
                p.setEmail(rs.getString("email"));
                
                int userId = rs.getInt("user_id");
                if (rs.wasNull()) {
                    p.setUserId(null);
                } else {
                    p.setUserId(userId);
                }
                return p;
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil penduduk by NIK: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Mengambil data penduduk berdasarkan user_id.
     * Digunakan setelah login penduduk untuk mendapatkan data demografi mereka.
     * @param userId ID user.
     * @return Objek Penduduk jika ditemukan, null jika tidak.
     */
    public Penduduk getPendudukByUserId(int userId) {
        String sql = "SELECT nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, alamat, telepon, email, user_id FROM Penduduk WHERE user_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Penduduk p = new Penduduk();
                p.setNik(rs.getString("nik"));
                p.setNama(rs.getString("nama"));
                p.setTempatLahir(rs.getString("tempat_lahir"));
                String tanggalLahirStr = rs.getString("tanggal_lahir");
                p.setTanggalLahir(tanggalLahirStr != null ? LocalDate.parse(tanggalLahirStr) : null);
                p.setJenisKelamin(rs.getString("jenis_kelamin"));
                p.setAlamat(rs.getString("alamat"));
                p.setTelepon(rs.getString("telepon"));
                p.setEmail(rs.getString("email"));
                
                int retrievedUserId = rs.getInt("user_id");
                if (rs.wasNull()) { // should not be null if queried by userId
                    p.setUserId(null); 
                } else {
                    p.setUserId(retrievedUserId);
                }
                return p;
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil penduduk by user ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Memperbarui data penduduk di database.
     * @param p Objek Penduduk dengan data terbaru (NIK sebagai kunci).
     * @return true jika berhasil, false jika gagal.
     */
    public boolean update(Penduduk p) {
        String sql = "UPDATE Penduduk SET nama = ?, tempat_lahir = ?, tanggal_lahir = ?, jenis_kelamin = ?, alamat = ?, telepon = ?, email = ?, user_id = ? WHERE nik = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNama());
            stmt.setString(2, p.getTempatLahir());
            stmt.setString(3, p.getTanggalLahir() != null ? p.getTanggalLahir().toString() : null);
            stmt.setString(4, p.getJenisKelamin());
            stmt.setString(5, p.getAlamat());
            stmt.setString(6, p.getTelepon());
            stmt.setString(7, p.getEmail());
            if (p.getUserId() != null) {
                stmt.setInt(8, p.getUserId());
            } else {
                stmt.setNull(8, java.sql.Types.INTEGER);
            }
            stmt.setString(9, p.getNik());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error saat update penduduk: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus data penduduk dari database.
     * @param nik NIK penduduk yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean delete(String nik) {
        String sql = "DELETE FROM Penduduk WHERE nik = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nik);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error saat delete penduduk: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memeriksa apakah NIK sudah ada di database.
     * @param nik NIK yang akan diperiksa.
     * @return true jika NIK sudah ada, false jika belum.
     */
    public boolean isNikExists(String nik) {
        String sql = "SELECT COUNT(*) FROM Penduduk WHERE nik = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nik);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error saat cek NIK: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}