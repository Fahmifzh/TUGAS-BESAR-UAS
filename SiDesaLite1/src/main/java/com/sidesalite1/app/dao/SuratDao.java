package com.sidesalite1.app.dao;

import com.sidesalite1.app.model.Surat;
import com.sidesalite1.app.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SuratDao {

    /**
     * Menyimpan data surat yang sudah diterbitkan ke database.
     * @param s Objek Surat yang akan disimpan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean insert(Surat s) {
        String sql = "INSERT INTO Surat (permohonan_id, nik, jenis, tanggal_terbit, konten_surat) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, s.getPermohonanId());
            stmt.setString(2, s.getNik());
            stmt.setString(3, s.getJenis());
            stmt.setString(4, s.getTanggalTerbit().toString());
            stmt.setString(5, s.getKontenSurat());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        s.setId(generatedKeys.getInt(1)); // Set ID yang dibuat otomatis
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error saat insert surat: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Mengambil semua surat yang sudah diterbitkan dari database.
     * @return List objek Surat.
     */
    public List<Surat> getAllSurat() {
        List<Surat> list = new ArrayList<>();
        String sql = "SELECT id, permohonan_id, nik, jenis, tanggal_terbit, konten_surat FROM Surat ORDER BY tanggal_terbit DESC";
        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Surat(
                    rs.getInt("id"),
                    rs.getInt("permohonan_id"),
                    rs.getString("nik"),
                    rs.getString("jenis"),
                    LocalDate.parse(rs.getString("tanggal_terbit")),
                    rs.getString("konten_surat")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua surat: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Mengambil surat berdasarkan NIK.
     * @param nik NIK pemilik surat.
     * @return List objek Surat.
     */
    public List<Surat> getSuratByNik(String nik) {
        List<Surat> list = new ArrayList<>();
        String sql = "SELECT id, permohonan_id, nik, jenis, tanggal_terbit, konten_surat FROM Surat WHERE nik = ? ORDER BY tanggal_terbit DESC";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nik);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Surat(
                    rs.getInt("id"),
                    rs.getInt("permohonan_id"),
                    rs.getString("nik"),
                    rs.getString("jenis"),
                    LocalDate.parse(rs.getString("tanggal_terbit")),
                    rs.getString("konten_surat")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error saat mengambil surat by NIK: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Mengambil surat berdasarkan ID permohonan.
     * @param permohonanId ID permohonan terkait.
     * @return Objek Surat jika ditemukan, null jika tidak.
     */
    public Surat getSuratByPermohonanId(int permohonanId) {
        String sql = "SELECT id, permohonan_id, nik, jenis, tanggal_terbit, konten_surat FROM Surat WHERE permohonan_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, permohonanId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Surat(
                    rs.getInt("id"),
                    rs.getInt("permohonan_id"),
                    rs.getString("nik"),
                    rs.getString("jenis"),
                    LocalDate.parse(rs.getString("tanggal_terbit")),
                    rs.getString("konten_surat")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil surat by Permohonan ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Memperbarui konten surat yang sudah diterbitkan.
     * @param surat Objek Surat dengan data terbaru.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean update(Surat surat) {
        String sql = "UPDATE Surat SET nik = ?, jenis = ?, tanggal_terbit = ?, konten_surat = ? WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, surat.getNik());
            stmt.setString(2, surat.getJenis());
            stmt.setString(3, surat.getTanggalTerbit().toString());
            stmt.setString(4, surat.getKontenSurat());
            stmt.setInt(5, surat.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error saat update surat: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus surat yang sudah diterbitkan dari database.
     * @param id ID surat yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM Surat WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error saat delete surat: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}