//package DAO;
//
//import Models.Enrollment;
//import DAL.DBContext;
//
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class EnrollmentDAO extends DBContext {
//
//    public List<Enrollment> getAllEnrollments() {
//
//        List<Enrollment> list = new ArrayList<>();
//
//        String sql
//                = "SELECT "
//                + "e.Id, "
//                + "e.StudentId, "
//                + "s.StudentCode, "
//                + "u.FullName AS StudentName, "
//                + "e.ClassId, "
//                + "cl.ClassCode, "
//                + "cl.ClassName, "
//                + "co.Id AS CourseId, "
//                + "co.CourseCode, "
//                + "co.CourseName, "
//                + "m.Id AS MajorId, "
//                + "m.MajorCode, "
//                + "m.MajorName, "
//                + "e.EnrollmentDate, "
//                + "e.Status "
//                + "FROM Enrollments e "
//                + "JOIN Students s "
//                + "ON e.StudentId = s.Id "
//                + "JOIN Users u "
//                + "ON s.UserId = u.Id "
//                + "JOIN Classes cl "
//                + "ON e.ClassId = cl.Id "
//                + "JOIN Courses co "
//                + "ON cl.CourseId = co.Id "
//                + "JOIN Majors m "
//                + "ON cl.MajorId = m.Id "
//                + "ORDER BY e.Id DESC";
//
//        try (
//                Connection connection = connection();
//                PreparedStatement ps = connection.prepareStatement(sql);
//                ResultSet rs = ps.executeQuery()) {
//
//            while (rs.next()) {
//                list.add(mapEnrollment(rs));
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "getAllEnrollments error: " + e.getMessage()
//            );
//        }
//
//        return list;
//    }
//
//    /*
//     * Tìm kiếm ghi danh theo:
//     * - mã sinh viên
//     * - tên sinh viên
//     * - mã lớp
//     * - tên lớp
//     * - mã môn
//     * - tên môn
//     */
//    public List<Enrollment> searchEnrollments(String keyword) {
//
//        List<Enrollment> list = new ArrayList<>();
//
//        String sql
//                = "SELECT "
//                + "e.Id, "
//                + "e.StudentId, "
//                + "s.StudentCode, "
//                + "u.FullName AS StudentName, "
//                + "e.ClassId, "
//                + "cl.ClassCode, "
//                + "cl.ClassName, "
//                + "co.Id AS CourseId, "
//                + "co.CourseCode, "
//                + "co.CourseName, "
//                + "m.Id AS MajorId, "
//                + "m.MajorCode, "
//                + "m.MajorName, "
//                + "e.EnrollmentDate, "
//                + "e.Status "
//                + "FROM Enrollments e "
//                + "JOIN Students s "
//                + "ON e.StudentId = s.Id "
//                + "JOIN Users u "
//                + "ON s.UserId = u.Id "
//                + "JOIN Classes cl "
//                + "ON e.ClassId = cl.Id "
//                + "JOIN Courses co "
//                + "ON cl.CourseId = co.Id "
//                + "JOIN Majors m "
//                + "ON cl.MajorId = m.Id "
//                + "WHERE s.StudentCode LIKE ? "
//                + "OR u.FullName LIKE ? "
//                + "OR cl.ClassCode LIKE ? "
//                + "OR cl.ClassName LIKE ? "
//                + "OR co.CourseCode LIKE ? "
//                + "OR co.CourseName LIKE ? "
//                + "ORDER BY e.Id DESC";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            String searchValue = "%" + keyword.trim() + "%";
//
//            ps.setString(1, searchValue);
//            ps.setString(2, searchValue);
//            ps.setString(3, searchValue);
//            ps.setString(4, searchValue);
//            ps.setString(5, searchValue);
//            ps.setString(6, searchValue);
//
//            try (ResultSet rs = ps.executeQuery()) {
//
//                while (rs.next()) {
//                    list.add(mapEnrollment(rs));
//                }
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "searchEnrollments error: " + e.getMessage()
//            );
//        }
//
//        return list;
//    }
//
//    /*
//     * Lấy Enrollment theo ID.
//     */
//    public Enrollment getEnrollmentById(int id) {
//
//        String sql
//                = "SELECT "
//                + "e.Id, "
//                + "e.StudentId, "
//                + "s.StudentCode, "
//                + "u.FullName AS StudentName, "
//                + "e.ClassId, "
//                + "cl.ClassCode, "
//                + "cl.ClassName, "
//                + "co.Id AS CourseId, "
//                + "co.CourseCode, "
//                + "co.CourseName, "
//                + "m.Id AS MajorId, "
//                + "m.MajorCode, "
//                + "m.MajorName, "
//                + "e.EnrollmentDate, "
//                + "e.Status "
//                + "FROM Enrollments e "
//                + "JOIN Students s "
//                + "ON e.StudentId = s.Id "
//                + "JOIN Users u "
//                + "ON s.UserId = u.Id "
//                + "JOIN Classes cl "
//                + "ON e.ClassId = cl.Id "
//                + "JOIN Courses co "
//                + "ON cl.CourseId = co.Id "
//                + "JOIN Majors m "
//                + "ON cl.MajorId = m.Id "
//                + "WHERE e.Id = ?";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setInt(1, id);
//
//            try (ResultSet rs = ps.executeQuery()) {
//
//                if (rs.next()) {
//                    return mapEnrollment(rs);
//                }
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "getEnrollmentById error: " + e.getMessage()
//            );
//        }
//
//        return null;
//    }
//
//    /*
//     * Thêm một Enrollment mới.
//     */
//    public boolean insertEnrollment(Enrollment enrollment) {
//
//        String sql
//                = "INSERT INTO Enrollments "
//                + "(StudentId, ClassId, EnrollmentDate, Status) "
//                + "VALUES (?, ?, ?, ?)";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setInt(1, enrollment.getStudentId());
//            ps.setInt(2, enrollment.getClassId());
//            ps.setDate(3, enrollment.getEnrollmentDate());
//            ps.setString(4, enrollment.getStatus());
//
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "insertEnrollment error: " + e.getMessage()
//            );
//        }
//
//        return false;
//    }
//
//    /*
//     * Cập nhật Enrollment.
//     */
//    public boolean updateEnrollment(Enrollment enrollment) {
//
//        String sql
//                = "UPDATE Enrollments "
//                + "SET StudentId = ?, "
//                + "ClassId = ?, "
//                + "EnrollmentDate = ?, "
//                + "Status = ? "
//                + "WHERE Id = ?";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setInt(1, enrollment.getStudentId());
//            ps.setInt(2, enrollment.getClassId());
//            ps.setDate(3, enrollment.getEnrollmentDate());
//            ps.setString(4, enrollment.getStatus());
//            ps.setInt(5, enrollment.getId());
//
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "updateEnrollment error: " + e.getMessage()
//            );
//        }
//
//        return false;
//    }
//
//    /*
//     * Xóa Enrollment theo ID.
//     */
//    public boolean deleteEnrollment(int id) {
//
//        String sql
//                = "DELETE FROM Enrollments "
//                + "WHERE Id = ?";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setInt(1, id);
//
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "deleteEnrollment error: " + e.getMessage()
//            );
//        }
//
//        return false;
//    }
//
//    /*
//     * Kiểm tra Enrollment ID có tồn tại hay không.
//     */
//    public boolean checkEnrollmentId(int id) {
//
//        String sql
//                = "SELECT 1 "
//                + "FROM Enrollments "
//                + "WHERE Id = ?";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setInt(1, id);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                return rs.next();
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "checkEnrollmentId error: " + e.getMessage()
//            );
//        }
//
//        return false;
//    }
//
//    /*
//     * Kiểm tra sinh viên đã được ghi danh vào lớp chưa.
//     * Dùng khi Create.
//     */
//    public boolean checkDuplicateEnrollment(
//            int studentId,
//            int classId) {
//
//        String sql
//                = "SELECT 1 "
//                + "FROM Enrollments "
//                + "WHERE StudentId = ? "
//                + "AND ClassId = ?";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setInt(1, studentId);
//            ps.setInt(2, classId);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                return rs.next();
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "checkDuplicateEnrollment error: "
//                    + e.getMessage()
//            );
//        }
//
//        return false;
//    }
//
//    /*
//     * Kiểm tra trùng Student + Class nhưng bỏ qua ID hiện tại.
//     * Dùng khi Update.
//     */
//    public boolean checkDuplicateEnrollmentExceptId(
//            int studentId,
//            int classId,
//            int enrollmentId) {
//
//        String sql
//                = "SELECT 1 "
//                + "FROM Enrollments "
//                + "WHERE StudentId = ? "
//                + "AND ClassId = ? "
//                + "AND Id <> ?";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setInt(1, studentId);
//            ps.setInt(2, classId);
//            ps.setInt(3, enrollmentId);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                return rs.next();
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "checkDuplicateEnrollmentExceptId error: "
//                    + e.getMessage()
//            );
//        }
//
//        return false;
//    }
//
//    /*
//     * Kiểm tra Student có tồn tại.
//     */
//    public boolean checkStudentId(int studentId) {
//
//        String sql
//                = "SELECT 1 "
//                + "FROM Students "
//                + "WHERE Id = ?";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setInt(1, studentId);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                return rs.next();
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "checkStudentId error: " + e.getMessage()
//            );
//        }
//
//        return false;
//    }
//
//    /*
//     * Kiểm tra Class có tồn tại.
//     */
//    public boolean checkClassId(int classId) {
//
//        String sql
//                = "SELECT 1 "
//                + "FROM Classes "
//                + "WHERE Id = ?";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setInt(1, classId);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                return rs.next();
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "checkClassId error: " + e.getMessage()
//            );
//        }
//
//        return false;
//    }
//
//    /*
//     * Mapper: chuyển một dòng ResultSet thành Enrollment object.
//     */
//    private Enrollment mapEnrollment(ResultSet rs)
//            throws SQLException {
//
//        Enrollment enrollment = new Enrollment();
//
//        enrollment.setId(rs.getInt("Id"));
//
//        enrollment.setStudentId(
//                rs.getInt("StudentId")
//        );
//
//        enrollment.setStudentCode(
//                rs.getString("StudentCode")
//        );
//
//        enrollment.setStudentName(
//                rs.getString("StudentName")
//        );
//
//        enrollment.setClassId(
//                rs.getInt("ClassId")
//        );
//
//        enrollment.setClassCode(
//                rs.getString("ClassCode")
//        );
//
//        enrollment.setClassName(
//                rs.getString("ClassName")
//        );
//
//        enrollment.setCourseId(
//                rs.getInt("CourseId")
//        );
//
//        enrollment.setCourseCode(
//                rs.getString("CourseCode")
//        );
//
//        enrollment.setCourseName(
//                rs.getString("CourseName")
//        );
//
//        enrollment.setMajorId(
//                rs.getInt("MajorId")
//        );
//
//        enrollment.setMajorCode(
//                rs.getString("MajorCode")
//        );
//
//        enrollment.setMajorName(
//                rs.getString("MajorName")
//        );
//
//        enrollment.setEnrollmentDate(
//                rs.getDate("EnrollmentDate")
//        );
//
//        enrollment.setStatus(
//                rs.getString("Status")
//        );
//
//        return enrollment;package DAO;
//
//import Models.Enrollment;
//import DAL.DBContext;
//
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class EnrollmentDAO extends DBContext {
//
//    public List<Enrollment> getAllEnrollments() {
//
//        List<Enrollment> list = new ArrayList<>();
//
//        String sql
//                = "SELECT "
//                + "e.Id, "
//                + "e.StudentId, "
//                + "s.StudentCode, "
//                + "u.FullName AS StudentName, "
//                + "e.ClassId, "
//                + "cl.ClassCode, "
//                + "cl.ClassName, "
//                + "co.Id AS CourseId, "
//                + "co.CourseCode, "
//                + "co.CourseName, "
//                + "m.Id AS MajorId, "
//                + "m.MajorCode, "
//                + "m.MajorName, "
//                + "e.EnrollmentDate, "
//                + "e.Status "
//                + "FROM Enrollments e "
//                + "JOIN Students s "
//                + "ON e.StudentId = s.Id "
//                + "JOIN Users u "
//                + "ON s.UserId = u.Id "
//                + "JOIN Classes cl "
//                + "ON e.ClassId = cl.Id "
//                + "JOIN Courses co "
//                + "ON cl.CourseId = co.Id "
//                + "JOIN Majors m "
//                + "ON cl.MajorId = m.Id "
//                + "ORDER BY e.Id DESC";
//
//        try (
//                Connection connection = connection();
//                PreparedStatement ps = connection.prepareStatement(sql);
//                ResultSet rs = ps.executeQuery()) {
//
//            while (rs.next()) {
//                list.add(mapEnrollment(rs));
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "getAllEnrollments error: " + e.getMessage()
//            );
//        }
//
//        return list;
//    }
//
//    /*
//     * Tìm kiếm ghi danh theo:
//     * - mã sinh viên
//     * - tên sinh viên
//     * - mã lớp
//     * - tên lớp
//     * - mã môn
//     * - tên môn
//     */
//    public List<Enrollment> searchEnrollments(String keyword) {
//
//        List<Enrollment> list = new ArrayList<>();
//
//        String sql
//                = "SELECT "
//                + "e.Id, "
//                + "e.StudentId, "
//                + "s.StudentCode, "
//                + "u.FullName AS StudentName, "
//                + "e.ClassId, "
//                + "cl.ClassCode, "
//                + "cl.ClassName, "
//                + "co.Id AS CourseId, "
//                + "co.CourseCode, "
//                + "co.CourseName, "
//                + "m.Id AS MajorId, "
//                + "m.MajorCode, "
//                + "m.MajorName, "
//                + "e.EnrollmentDate, "
//                + "e.Status "
//                + "FROM Enrollments e "
//                + "JOIN Students s "
//                + "ON e.StudentId = s.Id "
//                + "JOIN Users u "
//                + "ON s.UserId = u.Id "
//                + "JOIN Classes cl "
//                + "ON e.ClassId = cl.Id "
//                + "JOIN Courses co "
//                + "ON cl.CourseId = co.Id "
//                + "JOIN Majors m "
//                + "ON cl.MajorId = m.Id "
//                + "WHERE s.StudentCode LIKE ? "
//                + "OR u.FullName LIKE ? "
//                + "OR cl.ClassCode LIKE ? "
//                + "OR cl.ClassName LIKE ? "
//                + "OR co.CourseCode LIKE ? "
//                + "OR co.CourseName LIKE ? "
//                + "ORDER BY e.Id DESC";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            String searchValue = "%" + keyword.trim() + "%";
//
//            ps.setString(1, searchValue);
//            ps.setString(2, searchValue);
//            ps.setString(3, searchValue);
//            ps.setString(4, searchValue);
//            ps.setString(5, searchValue);
//            ps.setString(6, searchValue);
//
//            try (ResultSet rs = ps.executeQuery()) {
//
//                while (rs.next()) {
//                    list.add(mapEnrollment(rs));
//                }
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "searchEnrollments error: " + e.getMessage()
//            );
//        }
//
//        return list;
//    }
//
//    /*
//     * Lấy Enrollment theo ID.
//     */
//    public Enrollment getEnrollmentById(int id) {
//
//        String sql
//                = "SELECT "
//                + "e.Id, "
//                + "e.StudentId, "
//                + "s.StudentCode, "
//                + "u.FullName AS StudentName, "
//                + "e.ClassId, "
//                + "cl.ClassCode, "
//                + "cl.ClassName, "
//                + "co.Id AS CourseId, "
//                + "co.CourseCode, "
//                + "co.CourseName, "
//                + "m.Id AS MajorId, "
//                + "m.MajorCode, "
//                + "m.MajorName, "
//                + "e.EnrollmentDate, "
//                + "e.Status "
//                + "FROM Enrollments e "
//                + "JOIN Students s "
//                + "ON e.StudentId = s.Id "
//                + "JOIN Users u "
//                + "ON s.UserId = u.Id "
//                + "JOIN Classes cl "
//                + "ON e.ClassId = cl.Id "
//                + "JOIN Courses co "
//                + "ON cl.CourseId = co.Id "
//                + "JOIN Majors m "
//                + "ON cl.MajorId = m.Id "
//                + "WHERE e.Id = ?";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setInt(1, id);
//
//            try (ResultSet rs = ps.executeQuery()) {
//
//                if (rs.next()) {
//                    return mapEnrollment(rs);
//                }
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "getEnrollmentById error: " + e.getMessage()
//            );
//        }
//
//        return null;
//    }
//
//    /*
//     * Thêm một Enrollment mới.
//     */
//    public boolean insertEnrollment(Enrollment enrollment) {
//
//        String sql
//                = "INSERT INTO Enrollments "
//                + "(StudentId, ClassId, EnrollmentDate, Status) "
//                + "VALUES (?, ?, ?, ?)";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setInt(1, enrollment.getStudentId());
//            ps.setInt(2, enrollment.getClassId());
//            ps.setDate(3, enrollment.getEnrollmentDate());
//            ps.setString(4, enrollment.getStatus());
//
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "insertEnrollment error: " + e.getMessage()
//            );
//        }
//
//        return false;
//    }
//
//    /*
//     * Cập nhật Enrollment.
//     */
//    public boolean updateEnrollment(Enrollment enrollment) {
//
//        String sql
//                = "UPDATE Enrollments "
//                + "SET StudentId = ?, "
//                + "ClassId = ?, "
//                + "EnrollmentDate = ?, "
//                + "Status = ? "
//                + "WHERE Id = ?";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setInt(1, enrollment.getStudentId());
//            ps.setInt(2, enrollment.getClassId());
//            ps.setDate(3, enrollment.getEnrollmentDate());
//            ps.setString(4, enrollment.getStatus());
//            ps.setInt(5, enrollment.getId());
//
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "updateEnrollment error: " + e.getMessage()
//            );
//        }
//
//        return false;
//    }
//
//    /*
//     * Xóa Enrollment theo ID.
//     */
//    public boolean deleteEnrollment(int id) {
//
//        String sql
//                = "DELETE FROM Enrollments "
//                + "WHERE Id = ?";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setInt(1, id);
//
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "deleteEnrollment error: " + e.getMessage()
//            );
//        }
//
//        return false;
//    }
//
//    /*
//     * Kiểm tra Enrollment ID có tồn tại hay không.
//     */
//    public boolean checkEnrollmentId(int id) {
//
//        String sql
//                = "SELECT 1 "
//                + "FROM Enrollments "
//                + "WHERE Id = ?";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setInt(1, id);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                return rs.next();
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "checkEnrollmentId error: " + e.getMessage()
//            );
//        }
//
//        return false;
//    }
//
//    /*
//     * Kiểm tra sinh viên đã được ghi danh vào lớp chưa.
//     * Dùng khi Create.
//     */
//    public boolean checkDuplicateEnrollment(
//            int studentId,
//            int classId) {
//
//        String sql
//                = "SELECT 1 "
//                + "FROM Enrollments "
//                + "WHERE StudentId = ? "
//                + "AND ClassId = ?";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setInt(1, studentId);
//            ps.setInt(2, classId);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                return rs.next();
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "checkDuplicateEnrollment error: "
//                    + e.getMessage()
//            );
//        }
//
//        return false;
//    }
//
//    /*
//     * Kiểm tra trùng Student + Class nhưng bỏ qua ID hiện tại.
//     * Dùng khi Update.
//     */
//    public boolean checkDuplicateEnrollmentExceptId(
//            int studentId,
//            int classId,
//            int enrollmentId) {
//
//        String sql
//                = "SELECT 1 "
//                + "FROM Enrollments "
//                + "WHERE StudentId = ? "
//                + "AND ClassId = ? "
//                + "AND Id <> ?";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setInt(1, studentId);
//            ps.setInt(2, classId);
//            ps.setInt(3, enrollmentId);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                return rs.next();
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "checkDuplicateEnrollmentExceptId error: "
//                    + e.getMessage()
//            );
//        }
//
//        return false;
//    }
//
//    /*
//     * Kiểm tra Student có tồn tại.
//     */
//    public boolean checkStudentId(int studentId) {
//
//        String sql
//                = "SELECT 1 "
//                + "FROM Students "
//                + "WHERE Id = ?";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setInt(1, studentId);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                return rs.next();
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "checkStudentId error: " + e.getMessage()
//            );
//        }
//
//        return false;
//    }
//
//    /*
//     * Kiểm tra Class có tồn tại.
//     */
//    public boolean checkClassId(int classId) {
//
//        String sql
//                = "SELECT 1 "
//                + "FROM Classes "
//                + "WHERE Id = ?";
//
//        try (
//                Connection connection = getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setInt(1, classId);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                return rs.next();
//            }
//
//        } catch (SQLException e) {
//            System.out.println(
//                    "checkClassId error: " + e.getMessage()
//            );
//        }
//
//        return false;
//    }
//
//    /*
//     * Mapper: chuyển một dòng ResultSet thành Enrollment object.
//     */
//    private Enrollment mapEnrollment(ResultSet rs)
//            throws SQLException {
//
//        Enrollment enrollment = new Enrollment();
//
//        enrollment.setId(rs.getInt("Id"));
//
//        enrollment.setStudentId(
//                rs.getInt("StudentId")
//        );
//
//        enrollment.setStudentCode(
//                rs.getString("StudentCode")
//        );
//
//        enrollment.setStudentName(
//                rs.getString("StudentName")
//        );
//
//        enrollment.setClassId(
//                rs.getInt("ClassId")
//        );
//
//        enrollment.setClassCode(
//                rs.getString("ClassCode")
//        );
//
//        enrollment.setClassName(
//                rs.getString("ClassName")
//        );
//
//        enrollment.setCourseId(
//                rs.getInt("CourseId")
//        );
//
//        enrollment.setCourseCode(
//                rs.getString("CourseCode")
//        );
//
//        enrollment.setCourseName(
//                rs.getString("CourseName")
//        );
//
//        enrollment.setMajorId(
//                rs.getInt("MajorId")
//        );
//
//        enrollment.setMajorCode(
//                rs.getString("MajorCode")
//        );
//
//        enrollment.setMajorName(
//                rs.getString("MajorName")
//        );
//
//        enrollment.setEnrollmentDate(
//                rs.getDate("EnrollmentDate")
//        );
//
//        enrollment.setStatus(
//                rs.getString("Status")
//        );
//
//        return enrollment;
//    }
//}
//    }
//}