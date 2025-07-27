package com.sidesalite1.app.dao;

import com.sidesalite1.app.model.Permohonan;
import com.sidesalite1.app.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PermohonanDao {

    /**
     * Menyimpan permohonan baru ke database.
     * @param p Objek Permohonan yang akan disimpan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean insert(Permohonan p) {
        String sql = "INSERT INTO Permohonan (nik, id_jenis_surat, keterangan, tanggal_permohonan, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, p.getNik());
            stmt.setInt(2, p.getIdJenisSurat());
            stmt.setString(3, p.getKeterangan());
            stmt.setString(4, p.getTanggalPermohonan().toString());
            stmt.setString(5, p.getStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        p.setId(generatedKeys.getInt(1)); // Set ID yang dibuat otomatis
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error saat insert permohonan: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Mengambil semua permohonan dari database.
     * @return List objek Permohonan.
     */
    public List<Permohonan> getAllPermohonan() {
        List<Permohonan> list = new ArrayList<>();
        String sql = "SELECT id, nik, id_jenis_surat, keterangan, tanggal_permohonan, status FROM Permohonan ORDER BY tanggal_permohonan DESC";
        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Permohonan(
                    rs.getInt("id"),
                    rs.getString("nik"),
                    rs.getInt("id_jenis_surat"),
                    rs.getString("keterangan"),
                    LocalDate.parse(rs.getString("tanggal_permohonan")),
                    rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua permohonan: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Mengambil permohonan berdasarkan NIK.
     * Digunakan oleh penduduk untuk melihat permohonan mereka sendiri.
     * @param nik NIK penduduk.
     * @return List objek Permohonan.
     */
    public List<Permohonan> getPermohonanByNik(String nik) {
        List<Permohonan> list = new ArrayList<>();
        String sql = "SELECT id, nik, id_jenis_surat, keterangan, tanggal_permohonan, status FROM Permohonan WHERE nik = ? ORDER BY tanggal_permohonan DESC";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nik);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Permohonan(
                    rs.getInt("id"),
                    rs.getString("nik"),
                    rs.getInt("id_jenis_surat"),
                    rs.getString("keterangan"),
                    LocalDate.parse(rs.getString("tanggal_permohonan")),
                    rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error saat mengambil permohonan by NIK: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Mengambil permohonan berdasarkan ID.
     * @param id ID permohonan.
     * @return Objek Permohonan jika ditemukan, null jika tidak.
     */
    public Permohonan getPermohonanById(int id) {
        String sql = "SELECT id, nik, id_jenis_surat, keterangan, tanggal_permohonan, status FROM Permohonan WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Permohonan(
                    rs.getInt("id"),
                    rs.getString("nik"),
                    rs.getInt("id_jenis_surat"),
                    rs.getString("keterangan"),
                    LocalDate.parse(rs.getString("tanggal_permohonan")),
                    rs.getString("status")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil permohonan by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Memperbarui status permohonan.
     * @param id ID permohonan yang akan diperbarui.
     * @param newStatus Status baru permohonan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateStatus(int id, String newStatus) {
        String sql = "UPDATE Permohonan SET status = ? WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error saat update status permohonan: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}