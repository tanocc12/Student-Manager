package DAO;

import DAL.DBContext;
import Models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class UserDAO extends DBContext {

    // Đăng ký tài khoản
    public boolean register(User user) {

        String sql = """
            INSERT INTO Users
            (Username, Email, Password, FullName, Gender, Dob, Phone, Role)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            // Username
            ps.setString(1, user.getUsername());

            // Email
            ps.setString(2, user.getEmail());

            // Password
            ps.setString(3, user.getPassword());

            // FullName
            ps.setString(4, user.getFullName());

            // Gender
            if (user.getGender() == null || user.getGender().isEmpty()) {
                ps.setNull(5, Types.VARCHAR);
            } else {
                ps.setString(5, user.getGender());
            }

            // Date of Birth
            if (user.getDob() == null) {
                ps.setNull(6, Types.DATE);
            } else {
                ps.setDate(6, user.getDob());
            }

            // Phone
            if (user.getPhone() == null || user.getPhone().isEmpty()) {
                ps.setNull(7, Types.VARCHAR);
            } else {
                ps.setString(7, user.getPhone());
            }

            // Role
            ps.setString(8, user.getRole());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Kiểm tra Email đã tồn tại chưa
    public boolean checkEmail(String email) {

        String sql = "SELECT 1 FROM Users WHERE Email = ?";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Kiểm tra Username đã tồn tại chưa
    public boolean checkUsername(String username) {

        String sql = "SELECT 1 FROM Users WHERE Username = ?";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}