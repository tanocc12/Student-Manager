package DAO;

import Models.Enrollment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DAL.DBContext;

public class EnrollmentDAO extends DBContext {

    public EnrollmentDAO() {
        super();
    }

    public List<Enrollment> getAllEnrollments() {

        List<Enrollment> list = new ArrayList<>();

        String sql
                = "SELECT "
                + "e.Id, "
                + "e.StudentId, "
                + "s.StudentCode, "
                + "u.FullName AS StudentName, "
                + "e.ClassId, "
                + "c.ClassCode, "
                + "c.ClassName, "
                + "co.Id AS CourseId, "
                + "co.CourseCode, "
                + "co.CourseName, "
                + "m.Id AS MajorId, "
                + "m.MajorCode, "
                + "m.MajorName, "
                + "e.EnrollmentDate, "
                + "e.Status "
                + "FROM Enrollments e "
                + "JOIN Students s ON e.StudentId = s.Id "
                + "JOIN Users u ON s.UserId = u.Id "
                + "JOIN Classes c ON e.ClassId = c.Id "
                + "JOIN Courses co ON c.CourseId = co.Id "
                + "JOIN Majors m ON c.MajorId = m.Id "
                + "ORDER BY e.Id DESC";

        try (
                PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapEnrollment(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Enrollment getEnrollmentById(int id) {

        String sql
                = "SELECT "
                + "e.Id, "
                + "e.StudentId, "
                + "s.StudentCode, "
                + "u.FullName AS StudentName, "
                + "e.ClassId, "
                + "c.ClassCode, "
                + "c.ClassName, "
                + "co.Id AS CourseId, "
                + "co.CourseCode, "
                + "co.CourseName, "
                + "m.Id AS MajorId, "
                + "m.MajorCode, "
                + "m.MajorName, "
                + "e.EnrollmentDate, "
                + "e.Status "
                + "FROM Enrollments e "
                + "JOIN Students s ON e.StudentId = s.Id "
                + "JOIN Users u ON s.UserId = u.Id "
                + "JOIN Classes c ON e.ClassId = c.Id "
                + "JOIN Courses co ON c.CourseId = co.Id "
                + "JOIN Majors m ON c.MajorId = m.Id "
                + "WHERE e.Id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapEnrollment(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Enrollment> searchEnrollments(String keyword) {

        List<Enrollment> list = new ArrayList<>();

        String sql
                = "SELECT "
                + "e.Id, "
                + "e.StudentId, "
                + "s.StudentCode, "
                + "u.FullName AS StudentName, "
                + "e.ClassId, "
                + "c.ClassCode, "
                + "c.ClassName, "
                + "co.Id AS CourseId, "
                + "co.CourseCode, "
                + "co.CourseName, "
                + "m.Id AS MajorId, "
                + "m.MajorCode, "
                + "m.MajorName, "
                + "e.EnrollmentDate, "
                + "e.Status "
                + "FROM Enrollments e "
                + "JOIN Students s ON e.StudentId = s.Id "
                + "JOIN Users u ON s.UserId = u.Id "
                + "JOIN Classes c ON e.ClassId = c.Id "
                + "JOIN Courses co ON c.CourseId = co.Id "
                + "JOIN Majors m ON c.MajorId = m.Id "
                + "WHERE s.StudentCode LIKE ? "
                + "OR u.FullName LIKE ? "
                + "OR c.ClassCode LIKE ? "
                + "OR c.ClassName LIKE ? "
                + "OR co.CourseCode LIKE ? "
                + "OR co.CourseName LIKE ? "
                + "ORDER BY e.Id DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            String value = "%" + keyword.trim() + "%";

            ps.setString(1, value);
            ps.setString(2, value);
            ps.setString(3, value);
            ps.setString(4, value);
            ps.setString(5, value);
            ps.setString(6, value);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(mapEnrollment(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean insertEnrollment(Enrollment enrollment) {

        String sql
                = "INSERT INTO Enrollments "
                + "(StudentId, ClassId, EnrollmentDate, Status) "
                + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, enrollment.getStudentId());
            ps.setInt(2, enrollment.getClassId());
            ps.setDate(3, enrollment.getEnrollmentDate());
            ps.setString(4, enrollment.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateEnrollment(Enrollment enrollment) {

        String sql
                = "UPDATE Enrollments "
                + "SET StudentId = ?, "
                + "ClassId = ?, "
                + "EnrollmentDate = ?, "
                + "Status = ? "
                + "WHERE Id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, enrollment.getStudentId());
            ps.setInt(2, enrollment.getClassId());
            ps.setDate(3, enrollment.getEnrollmentDate());
            ps.setString(4, enrollment.getStatus());
            ps.setInt(5, enrollment.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteEnrollment(int id) {

        String sql = "DELETE FROM Enrollments WHERE Id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean checkEnrollmentId(int id) {

        String sql = "SELECT 1 FROM Enrollments WHERE Id = ?";

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

    public boolean checkDuplicateEnrollment(
            int studentId,
            int classId) {

        String sql
                = "SELECT 1 "
                + "FROM Enrollments "
                + "WHERE StudentId = ? "
                + "AND ClassId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, classId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean checkDuplicateEnrollmentExceptId(
            int studentId,
            int classId,
            int enrollmentId) {

        String sql
                = "SELECT 1 "
                + "FROM Enrollments "
                + "WHERE StudentId = ? "
                + "AND ClassId = ? "
                + "AND Id <> ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, classId);
            ps.setInt(3, enrollmentId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private Enrollment mapEnrollment(ResultSet rs)
            throws SQLException {

        Enrollment enrollment = new Enrollment();

        enrollment.setId(rs.getInt("Id"));

        enrollment.setStudentId(
                rs.getInt("StudentId")
        );

        enrollment.setStudentCode(
                rs.getString("StudentCode")
        );

        enrollment.setStudentName(
                rs.getString("StudentName")
        );

        enrollment.setClassId(
                rs.getInt("ClassId")
        );

        enrollment.setClassCode(
                rs.getString("ClassCode")
        );

        enrollment.setClassName(
                rs.getString("ClassName")
        );

        enrollment.setCourseId(
                rs.getInt("CourseId")
        );

        enrollment.setCourseCode(
                rs.getString("CourseCode")
        );

        enrollment.setCourseName(
                rs.getString("CourseName")
        );

        enrollment.setMajorId(
                rs.getInt("MajorId")
        );

        enrollment.setMajorCode(
                rs.getString("MajorCode")
        );

        enrollment.setMajorName(
                rs.getString("MajorName")
        );

        enrollment.setEnrollmentDate(
                rs.getDate("EnrollmentDate")
        );

        enrollment.setStatus(
                rs.getString("Status")
        );

        return enrollment;
    }
}
