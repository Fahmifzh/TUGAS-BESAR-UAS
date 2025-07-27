package com.sidesalite1.app.dao;

import com.sidesalite1.app.model.User;
import com.sidesalite1.app.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    /**
     * Memverifikasi kredensial pengguna untuk login.
     * @param username Username pengguna.
     * @param password Password pengguna.
     * @return Objek User jika kredensial valid, null jika tidak.
     */
    public User login(String username, String password) {
        String sql = "SELECT id, username, password, role FROM Users WHERE username = ? AND password = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password); // Dalam aplikasi nyata, gunakan hashing untuk password
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat login: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Menyimpan pengguna baru ke database.
     * @param user Objek User yang akan disimpan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean insert(User user) {
        String sql = "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1)); // Set ID yang dibuat otomatis
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error saat insert user: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Mengambil semua pengguna dari database.
     * @return List objek User.
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, password, role FROM Users";
        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua user: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Mengambil pengguna berdasarkan ID.
     * @param id ID pengguna.
     * @return Objek User jika ditemukan, null jika tidak.
     */
    public User getUserById(int id) {
        String sql = "SELECT id, username, password, role FROM Users WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil user by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Memperbarui data pengguna di database.
     * @param user Objek User dengan data terbaru.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean update(User user) {
        String sql = "UPDATE Users SET username = ?, password = ?, role = ? WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setInt(4, user.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error saat update user: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Menghapus pengguna dari database.
     * @param id ID pengguna yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM Users WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error saat delete user: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Mendapatkan ID user berdasarkan NIK Penduduk.
     * Berguna untuk mengecek apakah NIK sudah punya akun.
     * @param nik NIK Penduduk
     * @return User ID jika ditemukan, null jika tidak.
     */
    public Integer getUserIdByNikPenduduk(String nik) {
        String sql = "SELECT user_id FROM Penduduk WHERE nik = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nik);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Periksa apakah user_id adalah NULL di database
                int userId = rs.getInt("user_id");
                return rs.wasNull() ? null : userId;
            }
        } catch (SQLException e) {
            System.err.println("Error saat get user ID by NIK: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Menghubungkan user_id ke Penduduk berdasarkan NIK.
     * @param nik NIK Penduduk yang akan dihubungkan.
     * @param userId ID User yang akan dihubungkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean linkUserToPenduduk(String nik, int userId) {
        String sql = "UPDATE Penduduk SET user_id = ? WHERE nik = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, nik);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error saat link user to penduduk: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Melepaskan tautan user_id dari Penduduk berdasarkan NIK.
     * @param nik NIK Penduduk yang akan dilepaskan tautannya.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean unlinkUserFromPenduduk(String nik) {
        String sql = "UPDATE Penduduk SET user_id = NULL WHERE nik = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nik);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error saat unlink user from penduduk: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}