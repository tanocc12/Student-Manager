package DAO;

import DAL.DBContext;
import Models.Student;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends DBContext {

    /*
     * SQL dùng chung để lấy thông tin Student.
     *
     * Students
     * -> Users
     * -> Classes
     * -> Majors
     *
     * Không JOIN Courses tại đây vì Student có thể học
     * nhiều môn thông qua bảng StudentCourses.
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
            + "m.MajorName "
            + "FROM Students s "
            + "INNER JOIN Users u "
            + "ON s.UserId = u.Id "
            + "INNER JOIN Classes c "
            + "ON s.ClassId = c.Id "
            + "INNER JOIN Majors m "
            + "ON s.MajorId = m.Id ";

    /*
     * Chuyển một dòng ResultSet thành Student object.
     */
    private Student mapStudent(ResultSet rs)
            throws SQLException {

        Student student = new Student();

        // Students
        student.setId(rs.getInt("Id"));
        student.setStudentCode(
                rs.getString("StudentCode")
        );
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
        student.setClassCode(
                rs.getString("ClassCode")
        );
        student.setClassName(
                rs.getString("ClassName")
        );

        // Majors
        student.setMajorCode(
                rs.getString("MajorCode")
        );
        student.setMajorName(
                rs.getString("MajorName")
        );

        return student;
    }

    /*
     * Lấy toàn bộ danh sách Student.
     */
    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        String sql = SELECT_STUDENT
                + "ORDER BY s.Id DESC";

        try (PreparedStatement ps
                = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                students.add(mapStudent(rs));
            }

        } catch (SQLException e) {
            System.out.println(
                    "getAllStudents error: "
                    + e.getMessage()
            );
            e.printStackTrace();
        }

        return students;
    }

    /*
     * Tìm kiếm theo:
     * - StudentCode
     * - FullName
     * - Email
     * - Username
     * - ClassCode
     * - MajorCode
     * - MajorName
     */
    public List<Student> searchStudents(String keyword) {

        List<Student> students = new ArrayList<>();

        if (keyword == null
                || keyword.trim().isEmpty()) {

            return getAllStudents();
        }

        String sql = SELECT_STUDENT
                + "WHERE s.StudentCode LIKE ? "
                + "OR u.FullName LIKE ? "
                + "OR u.Email LIKE ? "
                + "OR u.Username LIKE ? "
                + "OR c.ClassCode LIKE ? "
                + "OR m.MajorCode LIKE ? "
                + "OR m.MajorName LIKE ? "
                + "ORDER BY s.Id DESC";

        String searchValue
                = "%" + keyword.trim() + "%";

        try (PreparedStatement ps
                = connection.prepareStatement(sql)) {

            ps.setString(1, searchValue);
            ps.setString(2, searchValue);
            ps.setString(3, searchValue);
            ps.setString(4, searchValue);
            ps.setString(5, searchValue);
            ps.setString(6, searchValue);
            ps.setString(7, searchValue);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    students.add(mapStudent(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "searchStudents error: "
                    + e.getMessage()
            );
            e.printStackTrace();
        }

        return students;
    }

    /*
     * Lấy một Student theo Students.Id.
     */
    public Student getStudentById(int id) {

        String sql = SELECT_STUDENT
                + "WHERE s.Id = ?";

        try (PreparedStatement ps
                = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapStudent(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "getStudentById error: "
                    + e.getMessage()
            );
            e.printStackTrace();
        }

        return null;
    }

    /*
     * Lấy Student theo Users.Id.
     *
     * Dùng khi Student đăng nhập và muốn
     * xem hồ sơ của chính mình.
     */
    public Student getStudentByUserId(int userId) {

        String sql = SELECT_STUDENT
                + "WHERE s.UserId = ?";

        try (PreparedStatement ps
                = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapStudent(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "getStudentByUserId error: "
                    + e.getMessage()
            );
            e.printStackTrace();
        }

        return null;
    }

    /*
     * Thêm hồ sơ Student.
     *
     * Tài khoản Users đã được tạo trước
     * bằng chức năng Register.
     *
     * Method này chỉ INSERT vào Students.
     */
    public boolean insertStudent(Student student) {

        String sql
                = "INSERT INTO Students "
                + "(StudentCode, UserId, ClassId, "
                + "MajorId, Address, Status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        if (student == null) {
            return false;
        }

        try (PreparedStatement ps
                = connection.prepareStatement(sql)) {

            ps.setString(
                    1,
                    student.getStudentCode()
            );

            ps.setInt(
                    2,
                    student.getUserId()
            );

            ps.setInt(
                    3,
                    student.getClassId()
            );

            ps.setInt(
                    4,
                    student.getMajorId()
            );

            setNullableString(
                    ps,
                    5,
                    student.getAddress()
            );

            ps.setString(
                    6,
                    student.getStatus()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "insertStudent error: "
                    + e.getMessage()
            );
            e.printStackTrace();

            return false;
        }
    }

    /*
     * Cập nhật hồ sơ Student.
     *
     * Không cập nhật Users vì thông tin tài khoản
     * được quản lý bởi chức năng đăng ký/profile.
     */
    public boolean updateStudent(Student student) {

        String sql
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

        try (PreparedStatement ps
                = connection.prepareStatement(sql)) {

            ps.setString(
                    1,
                    student.getStudentCode()
            );

            ps.setInt(
                    2,
                    student.getClassId()
            );

            ps.setInt(
                    3,
                    student.getMajorId()
            );

            setNullableString(
                    ps,
                    4,
                    student.getAddress()
            );

            ps.setString(
                    5,
                    student.getStatus()
            );

            ps.setInt(
                    6,
                    student.getId()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "updateStudent error: "
                    + e.getMessage()
            );
            e.printStackTrace();

            return false;
        }
    }

    /*
     * Xóa hồ sơ Student.
     *
     * Không xóa Users vì tài khoản được tạo
     * từ Register và có thể vẫn cần giữ lại.
     *
     * Thứ tự:
     * 1. Grades
     * 2. StudentCourses
     * 3. Students
     */
    public boolean deleteStudent(int studentId) {

        String deleteGradesSql
                = "DELETE FROM Grades "
                + "WHERE StudentCourseId IN "
                + "(SELECT Id "
                + "FROM StudentCourses "
                + "WHERE StudentId = ?)";

        String deleteStudentCoursesSql
                = "DELETE FROM StudentCourses "
                + "WHERE StudentId = ?";

        String deleteStudentSql
                = "DELETE FROM Students "
                + "WHERE Id = ?";

        try {
            connection.setAutoCommit(false);

            /*
             * Xóa Grades liên quan đến
             * StudentCourses của Student.
             */
            try (PreparedStatement ps
                    = connection.prepareStatement(
                            deleteGradesSql)) {

                ps.setInt(1, studentId);
                ps.executeUpdate();
            }

            /*
             * Xóa các đăng ký môn học.
             */
            try (PreparedStatement ps
                    = connection.prepareStatement(
                            deleteStudentCoursesSql)) {

                ps.setInt(1, studentId);
                ps.executeUpdate();
            }

            /*
             * Xóa hồ sơ Student.
             */
            try (PreparedStatement ps
                    = connection.prepareStatement(
                            deleteStudentSql)) {

                ps.setInt(1, studentId);

                int deletedRows
                        = ps.executeUpdate();

                if (deletedRows == 0) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            rollbackQuietly();

            System.out.println(
                    "deleteStudent error: "
                    + e.getMessage()
            );
            e.printStackTrace();

            return false;

        } finally {
            restoreAutoCommit();
        }
    }

    /*
     * Kiểm tra StudentCode đã tồn tại chưa.
     */
    public boolean checkStudentCode(
            String studentCode) {

        String sql
                = "SELECT 1 "
                + "FROM Students "
                + "WHERE StudentCode = ?";

        return existsString(sql, studentCode);
    }

    /*
     * Kiểm tra StudentCode trùng,
     * nhưng bỏ qua Student đang update.
     */
    public boolean checkStudentCodeExceptId(
            String studentCode,
            int studentId) {

        String sql
                = "SELECT 1 "
                + "FROM Students "
                + "WHERE StudentCode = ? "
                + "AND Id <> ?";

        try (PreparedStatement ps
                = connection.prepareStatement(sql)) {

            ps.setString(1, studentCode);
            ps.setInt(2, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println(
                    "checkStudentCodeExceptId error: "
                    + e.getMessage()
            );
            e.printStackTrace();

            return false;
        }
    }

    /*
     * Kiểm tra một User đã có hồ sơ Student chưa.
     */
    public boolean checkUserAlreadyHasStudent(
            int userId) {

        String sql
                = "SELECT 1 "
                + "FROM Students "
                + "WHERE UserId = ?";

        try (PreparedStatement ps
                = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println(
                    "checkUserAlreadyHasStudent error: "
                    + e.getMessage()
            );
            e.printStackTrace();

            return false;
        }
    }

    /*
     * Kiểm tra User tồn tại, có Role Student
     * và chưa có hồ sơ trong Students.
     */
    public boolean checkAvailableStudentUser(
            int userId) {

        String sql
                = "SELECT 1 "
                + "FROM Users u "
                + "LEFT JOIN Students s "
                + "ON u.Id = s.UserId "
                + "WHERE u.Id = ? "
                + "AND u.Role = 'Student' "
                + "AND s.Id IS NULL";

        try (PreparedStatement ps
                = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println(
                    "checkAvailableStudentUser error: "
                    + e.getMessage()
            );
            e.printStackTrace();

            return false;
        }
    }

    /*
     * Kiểm tra một String có tồn tại
     * trong database hay không.
     */
    private boolean existsString(
            String sql,
            String value) {

        if (value == null
                || value.trim().isEmpty()) {

            return false;
        }

        try (PreparedStatement ps
                = connection.prepareStatement(sql)) {

            ps.setString(1, value.trim());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println(
                    "existsString error: "
                    + e.getMessage()
            );
            e.printStackTrace();

            return false;
        }
    }

    /*
     * Gán String nullable cho PreparedStatement.
     */
    private void setNullableString(
            PreparedStatement ps,
            int index,
            String value)
            throws SQLException {

        if (value == null
                || value.trim().isEmpty()) {

            ps.setNull(index, Types.NVARCHAR);

        } else {
            ps.setString(index, value.trim());
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
            System.out.println(
                    "Rollback error: "
                    + e.getMessage()
            );
        }
    }

    /*
     * Khôi phục autoCommit sau transaction.
     */
    private void restoreAutoCommit() {

        try {
            if (connection != null) {
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Restore autoCommit error: "
                    + e.getMessage()
            );
        }
    }
}