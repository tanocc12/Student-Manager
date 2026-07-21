package DAO;

import DAL.DBContext;
import Models.Student;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends DBContext {

    /*
     * SQL dùng chung để lấy đầy đủ thông tin Student
     * Students -> Users -> Classes -> Majors -> Courses
     */
    private static final String SELECT_STUDENT
            = "SELECT "
            + "s.Id, "
            + "s.StudentCode, "
            + "s.UserId, "
            + "s.ClassId, "
            + "s.MajorId, "
            + "s.Address, "
            + "s.Status, "
            + "u.Username, "
            + "u.FullName, "
            + "u.Email, "
            + "u.Gender, "
            + "u.Dob, "
            + "u.Phone, "
            + "c.ClassCode, "
            + "c.ClassName, "
            + "m.MajorCode, "
            + "m.MajorName, "
            + "co.Id AS CourseId, "
            + "co.CourseCode, "
            + "co.CourseName "
            + "FROM Students s "
            + "INNER JOIN Users u ON s.UserId = u.Id "
            + "INNER JOIN Classes c ON s.ClassId = c.Id "
            + "INNER JOIN Majors m ON s.MajorId = m.Id "
            + "INNER JOIN Courses co ON c.CourseId = co.Id ";

    /*
     * Chuyển một dòng ResultSet thành Student object
     */
    private Student mapStudent(ResultSet rs) throws SQLException {
        Student student = new Student();

        // Students
        student.setId(rs.getInt("Id"));
        student.setStudentCode(rs.getString("StudentCode"));
        student.setUserId(rs.getInt("UserId"));
        student.setClassId(rs.getInt("ClassId"));
        student.setMajorId(rs.getInt("MajorId"));
        student.setAddress(rs.getString("Address"));
        student.setStatus(rs.getString("Status"));

        // Users
        student.setUsername(rs.getString("Username"));
        student.setFullName(rs.getString("FullName"));
        student.setEmail(rs.getString("Email"));
        student.setGender(rs.getString("Gender"));
        student.setDob(rs.getDate("Dob"));
        student.setPhone(rs.getString("Phone"));

        // Classes
        student.setClassCode(rs.getString("ClassCode"));
        student.setClassName(rs.getString("ClassName"));

        // Majors
        student.setMajorCode(rs.getString("MajorCode"));
        student.setMajorName(rs.getString("MajorName"));

        // Courses
        student.setCourseId(rs.getInt("CourseId"));
        student.setCourseCode(rs.getString("CourseCode"));
        student.setCourseName(rs.getString("CourseName"));

        return student;
    }

    /*
     * Lấy toàn bộ danh sách sinh viên
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        String sql = SELECT_STUDENT
                + "ORDER BY s.Id DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                students.add(mapStudent(rs));
            }

        } catch (SQLException e) {
            System.out.println("getAllStudents error: " + e.getMessage());
        }

        return students;
    }

    /*
     * Tìm kiếm sinh viên theo:
     * - StudentCode
     * - FullName
     * - Email
     * - Username
     * - ClassCode
     */
    public List<Student> searchStudents(String keyword) {
        List<Student> students = new ArrayList<>();

        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllStudents();
        }

        String sql = SELECT_STUDENT
                + "WHERE s.StudentCode LIKE ? "
                + "OR u.FullName LIKE ? "
                + "OR u.Email LIKE ? "
                + "OR u.Username LIKE ? "
                + "OR c.ClassCode LIKE ? "
                + "ORDER BY s.Id DESC";

        String searchValue = "%" + keyword.trim() + "%";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, searchValue);
            ps.setString(2, searchValue);
            ps.setString(3, searchValue);
            ps.setString(4, searchValue);
            ps.setString(5, searchValue);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    students.add(mapStudent(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("searchStudents error: " + e.getMessage());
        }

        return students;
    }

    /*
     * Lấy một Student theo Student.Id
     */
    public Student getStudentById(int id) {
        String sql = SELECT_STUDENT
                + "WHERE s.Id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapStudent(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("getStudentById error: " + e.getMessage());
        }

        return null;
    }

    /*
     * Lấy Student theo User.Id
     * Dùng khi Student đăng nhập và muốn xem thông tin của mình.
     */
    public Student getStudentByUserId(int userId) {
        String sql = SELECT_STUDENT
                + "WHERE s.UserId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapStudent(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("getStudentByUserId error: " + e.getMessage());
        }

        return null;
    }

    /*
     * Thêm sinh viên:
     *
     * Bước 1: INSERT Users
     * Bước 2: Lấy UserId vừa tạo
     * Bước 3: INSERT Students
     *
     * Dùng transaction để tránh Users được thêm nhưng Students bị lỗi.
     */
    public boolean insertStudent(Student student, String password) {
        String insertUserSql
                = "INSERT INTO Users "
                + "(Username, Email, Password, FullName, Gender, Dob, Phone, Role) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, 'Student')";

        String insertStudentSql
                = "INSERT INTO Students "
                + "(StudentCode, UserId, ClassId, MajorId, Address, Status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        if (student == null || password == null || password.trim().isEmpty()) {
            return false;
        }

        try {
            connection.setAutoCommit(false);

            int newUserId;

            // Thêm tài khoản User
            try (PreparedStatement psUser
                    = connection.prepareStatement(
                            insertUserSql,
                            Statement.RETURN_GENERATED_KEYS)) {

                psUser.setString(1, student.getUsername());
                psUser.setString(2, student.getEmail());
                psUser.setString(3, password);
                psUser.setString(4, student.getFullName());

                setNullableString(psUser, 5, student.getGender());
                setNullableDate(psUser, 6, student.getDob());
                setNullableString(psUser, 7, student.getPhone());

                int userRows = psUser.executeUpdate();

                if (userRows == 0) {
                    connection.rollback();
                    return false;
                }

                try (ResultSet generatedKeys = psUser.getGeneratedKeys()) {
                    if (!generatedKeys.next()) {
                        connection.rollback();
                        return false;
                    }

                    newUserId = generatedKeys.getInt(1);
                }
            }

            // Thêm thông tin Student
            try (PreparedStatement psStudent
                    = connection.prepareStatement(insertStudentSql)) {

                psStudent.setString(1, student.getStudentCode());
                psStudent.setInt(2, newUserId);
                psStudent.setInt(3, student.getClassId());
                psStudent.setInt(4, student.getMajorId());
                setNullableString(psStudent, 5, student.getAddress());
                psStudent.setString(6, student.getStatus());

                int studentRows = psStudent.executeUpdate();

                if (studentRows == 0) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            student.setUserId(newUserId);

            return true;

        } catch (SQLException e) {
            rollbackQuietly();
            System.out.println("insertStudent error: " + e.getMessage());
            return false;

        } finally {
            restoreAutoCommit();
        }
    }

    /*
     * Cập nhật Student:
     *
     * - Update bảng Users
     * - Update bảng Students
     *
     * Không cập nhật password tại đây.
     */
    public boolean updateStudent(Student student) {
        String updateUserSql
                = "UPDATE Users SET "
                + "Username = ?, "
                + "Email = ?, "
                + "FullName = ?, "
                + "Gender = ?, "
                + "Dob = ?, "
                + "Phone = ? "
                + "WHERE Id = ?";

        String updateStudentSql
                = "UPDATE Students SET "
                + "StudentCode = ?, "
                + "ClassId = ?, "
                + "MajorId = ?, "
                + "Address = ?, "
                + "Status = ? "
                + "WHERE Id = ?";

        if (student == null) {
            return false;
        }

        try {
            connection.setAutoCommit(false);

            // Update Users
            try (PreparedStatement psUser
                    = connection.prepareStatement(updateUserSql)) {

                psUser.setString(1, student.getUsername());
                psUser.setString(2, student.getEmail());
                psUser.setString(3, student.getFullName());

                setNullableString(psUser, 4, student.getGender());
                setNullableDate(psUser, 5, student.getDob());
                setNullableString(psUser, 6, student.getPhone());

                psUser.setInt(7, student.getUserId());

                int userRows = psUser.executeUpdate();

                if (userRows == 0) {
                    connection.rollback();
                    return false;
                }
            }

            // Update Students
            try (PreparedStatement psStudent
                    = connection.prepareStatement(updateStudentSql)) {

                psStudent.setString(1, student.getStudentCode());
                psStudent.setInt(2, student.getClassId());
                psStudent.setInt(3, student.getMajorId());
                setNullableString(psStudent, 4, student.getAddress());
                psStudent.setString(5, student.getStatus());
                psStudent.setInt(6, student.getId());

                int studentRows = psStudent.executeUpdate();

                if (studentRows == 0) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            rollbackQuietly();
            System.out.println("updateStudent error: " + e.getMessage());
            return false;

        } finally {
            restoreAutoCommit();
        }
    }

    /*
     * Đổi mật khẩu của Student.
     * Có thể sử dụng riêng khi cần.
     */
    public boolean updateStudentPassword(int userId, String newPassword) {
        String sql = "UPDATE Users SET Password = ? WHERE Id = ?";

        if (newPassword == null || newPassword.trim().isEmpty()) {
            return false;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("updateStudentPassword error: " + e.getMessage());
            return false;
        }
    }

    /*
     * Xóa hoàn toàn Student.
     *
     * Thứ tự:
     * 1. Grades
     * 2. StudentCourses
     * 3. Students
     * 4. Users
     */
    public boolean deleteStudent(int studentId) {
        String getUserIdSql
                = "SELECT UserId FROM Students WHERE Id = ?";

        String deleteGradesSql
                = "DELETE FROM Grades "
                + "WHERE StudentCourseId IN "
                + "(SELECT Id FROM StudentCourses WHERE StudentId = ?)";

        String deleteStudentCoursesSql
                = "DELETE FROM StudentCourses WHERE StudentId = ?";

        String deleteStudentSql
                = "DELETE FROM Students WHERE Id = ?";

        String deleteUserSql
                = "DELETE FROM Users WHERE Id = ?";

        try {
            connection.setAutoCommit(false);

            int userId;

            // Lấy UserId trước khi xóa Student
            try (PreparedStatement ps
                    = connection.prepareStatement(getUserIdSql)) {

                ps.setInt(1, studentId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        connection.rollback();
                        return false;
                    }

                    userId = rs.getInt("UserId");
                }
            }

            // Xóa Grades
            try (PreparedStatement ps
                    = connection.prepareStatement(deleteGradesSql)) {

                ps.setInt(1, studentId);
                ps.executeUpdate();
            }

            // Xóa StudentCourses
            try (PreparedStatement ps
                    = connection.prepareStatement(deleteStudentCoursesSql)) {

                ps.setInt(1, studentId);
                ps.executeUpdate();
            }

            // Xóa Students
            try (PreparedStatement ps
                    = connection.prepareStatement(deleteStudentSql)) {

                ps.setInt(1, studentId);

                if (ps.executeUpdate() == 0) {
                    connection.rollback();
                    return false;
                }
            }

            // Xóa tài khoản Users
            try (PreparedStatement ps
                    = connection.prepareStatement(deleteUserSql)) {

                ps.setInt(1, userId);

                if (ps.executeUpdate() == 0) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            rollbackQuietly();
            System.out.println("deleteStudent error: " + e.getMessage());
            return false;

        } finally {
            restoreAutoCommit();
        }
    }

    /*
     * Kiểm tra StudentCode đã tồn tại chưa.
     */
    public boolean checkStudentCode(String studentCode) {
        String sql
                = "SELECT 1 FROM Students WHERE StudentCode = ?";

        return exists(sql, studentCode);
    }

    /*
     * Kiểm tra StudentCode trùng, bỏ qua Student đang update.
     */
    public boolean checkStudentCodeExceptId(
            String studentCode, int studentId) {

        String sql
                = "SELECT 1 FROM Students "
                + "WHERE StudentCode = ? AND Id <> ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, studentCode);
            ps.setInt(2, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println(
                    "checkStudentCodeExceptId error: " + e.getMessage());
            return false;
        }
    }

    /*
     * Kiểm tra Username đã tồn tại.
     */
    public boolean checkUsername(String username) {
        String sql = "SELECT 1 FROM Users WHERE Username = ?";

        return exists(sql, username);
    }

    /*
     * Kiểm tra Username trùng, bỏ qua User đang update.
     */
    public boolean checkUsernameExceptUserId(
            String username, int userId) {

        String sql
                = "SELECT 1 FROM Users "
                + "WHERE Username = ? AND Id <> ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println(
                    "checkUsernameExceptUserId error: " + e.getMessage());
            return false;
        }
    }

    /*
     * Kiểm tra Email đã tồn tại.
     */
    public boolean checkEmail(String email) {
        String sql = "SELECT 1 FROM Users WHERE Email = ?";

        return exists(sql, email);
    }

    /*
     * Kiểm tra Email trùng, bỏ qua User đang update.
     */
    public boolean checkEmailExceptUserId(
            String email, int userId) {

        String sql
                = "SELECT 1 FROM Users "
                + "WHERE Email = ? AND Id <> ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println(
                    "checkEmailExceptUserId error: " + e.getMessage());
            return false;
        }
    }

    /*
     * Hàm kiểm tra một giá trị tồn tại trong database.
     */
    private boolean exists(String sql, String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, value.trim());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println("exists error: " + e.getMessage());
            return false;
        }
    }

    /*
     * Set String nullable.
     */
    private void setNullableString(
            PreparedStatement ps, int index, String value)
            throws SQLException {

        if (value == null || value.trim().isEmpty()) {
            ps.setNull(index, Types.NVARCHAR);
        } else {
            ps.setString(index, value.trim());
        }
    }

    /*
     * Set Date nullable.
     */
    private void setNullableDate(
            PreparedStatement ps, int index, Date value)
            throws SQLException {

        if (value == null) {
            ps.setNull(index, Types.DATE);
        } else {
            ps.setDate(index, value);
        }
    }

    /*
     * Rollback an toàn.
     */
    private void rollbackQuietly() {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Rollback error: " + e.getMessage());
        }
    }

    /*
     * Trả connection về autoCommit = true.
     */
    private void restoreAutoCommit() {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println(
                    "Restore autoCommit error: " + e.getMessage());
        }
    }
}