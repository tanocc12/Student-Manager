package DAO;

import DAL.DBContext;
import Models.Major;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MajorDAO extends DBContext {

    public MajorDAO() {
        super();
    }

    public List<Major> getAllMajors() {

        List<Major> majors = new ArrayList<>();

        String sql = """
                     SELECT Id, MajorCode, MajorName
                     FROM Majors
                     ORDER BY MajorName
                     """;

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Major major = new Major();

                major.setId(rs.getInt("Id"));
                major.setMajorCode(rs.getString("MajorCode"));
                major.setMajorName(rs.getString("MajorName"));

                majors.add(major);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return majors;
    }

    public Major getMajorById(int id) {

        String sql = """
                     SELECT Id, MajorCode, MajorName
                     FROM Majors
                     WHERE Id = ?
                     """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Major major = new Major();

                    major.setId(rs.getInt("Id"));
                    major.setMajorCode(rs.getString("MajorCode"));
                    major.setMajorName(rs.getString("MajorName"));

                    return major;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean checkMajorId(int id) {

        String sql = """
                     SELECT Id
                     FROM Majors
                     WHERE Id = ?
                     """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}