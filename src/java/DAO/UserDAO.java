package DAO;

import DAL.DBContext;
import Models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class UserDAO extends DBContext {

    // ========================= REGISTER =========================
    public boolean register(User user) {

        String sql = """
                INSERT INTO Users
                (Username, Email, Password, FullName, Gender, Dob, Phone, Role)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try {

            if (connection == null) {
                System.out.println("Connection is NULL!");
                return false;
            }

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getFullName());

            if (user.getGender() == null || user.getGender().isEmpty()) {
                ps.setNull(5, Types.VARCHAR);
            } else {
                ps.setString(5, user.getGender());
            }

            if (user.getDob() == null) {
                ps.setNull(6, Types.DATE);
            } else {
                ps.setDate(6, user.getDob());
            }

            if (user.getPhone() == null || user.getPhone().isEmpty()) {
                ps.setNull(7, Types.VARCHAR);
            } else {
                ps.setString(7, user.getPhone());
            }

            ps.setString(8, user.getRole());

            System.out.println("========== REGISTER ==========");
            System.out.println("Username : " + user.getUsername());
            System.out.println("Email    : " + user.getEmail());
            System.out.println("Role     : " + user.getRole());

            int row = ps.executeUpdate();

            System.out.println("Rows Inserted = " + row);

            return row > 0;

        } catch (Exception e) {

            System.out.println("========== REGISTER ERROR ==========");
            e.printStackTrace();
            return false;
        }
    }

    // ========================= CHECK EMAIL =========================
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

    // ========================= CHECK USERNAME =========================
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

    // ========================= LOGIN =========================
    public User login(String username, String password) {

        String sql = """
                SELECT *
                FROM Users
                WHERE Username = ?
                AND Password = ?
                """;

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                User user = new User();

                user.setId(rs.getInt("Id"));
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setFullName(rs.getString("FullName"));
                user.setGender(rs.getString("Gender"));
                user.setDob(rs.getDate("Dob"));
                user.setPhone(rs.getString("Phone"));
                user.setRole(rs.getString("Role"));

                return user;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

}