package com.sidesalite1.app.dao;

import com.sidesalite1.app.model.JenisSurat;
import com.sidesalite1.app.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JenisSuratDao {

    /**
     * Mengambil semua jenis surat dari database.
     * @return List objek JenisSurat.
     */
    public List<JenisSurat> getAll() {
        List<JenisSurat> list = new ArrayList<>();
        String sql = "SELECT id, nama FROM JenisSurat ORDER BY nama ASC";
        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new JenisSurat(rs.getInt("id"), rs.getString("nama")));
            }

        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua jenis surat: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Mengambil jenis surat berdasarkan ID.
     * @param id ID jenis surat.
     * @return Objek JenisSurat jika ditemukan, null jika tidak.
     */
    public JenisSurat getById(int id) {
        String sql = "SELECT id, nama FROM JenisSurat WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new JenisSurat(rs.getInt("id"), rs.getString("nama"));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil jenis surat by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mengambil jenis surat berdasarkan nama.
     * @param nama Nama jenis surat.
     * @return Objek JenisSurat jika ditemukan, null jika tidak.
     */
    public JenisSurat getByNama(String nama) {
        String sql = "SELECT id, nama FROM JenisSurat WHERE nama = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nama);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new JenisSurat(rs.getInt("id"), rs.getString("nama"));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil jenis surat by Nama: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}