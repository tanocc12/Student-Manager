package DAO;

import DAL.DBContext;
import Models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class UserDAO extends DBContext {

    public boolean register(User user) {

        String sql = """
                INSERT INTO Users
                (Email, Password, FullName, Gender, Dob, Phone, Role)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());

            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.DATE);
            ps.setNull(6, Types.VARCHAR);

            ps.setString(7, user.getRole());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

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

}