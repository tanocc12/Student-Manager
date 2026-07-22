package DAO;

import DAL.DBContext;
import Models.StudentCourse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentCourseDAO extends DBContext {

    public StudentCourseDAO() {
        super();
    }

    /*
     * Lấy toàn bộ danh sách sinh viên đã đăng ký môn học
     */
    public ArrayList<StudentCourse> getAllStudentCourses() {

        ArrayList<StudentCourse> list = new ArrayList<>();

        String sql = """
            SELECT
                sc.Id,
                sc.StudentId,
                sc.ClassId,
                sc.TeachingAssignmentId,

                s.StudentCode,
                su.FullName AS StudentName,

                c.ClassCode,
                c.ClassName,

                co.CourseCode,
                co.CourseName,

                t.TeacherCode,
                tu.FullName AS TeacherName,

                sem.SemesterName,
                sem.SchoolYear

            FROM StudentCourses sc

            INNER JOIN Students s
                ON sc.StudentId = s.Id

            INNER JOIN Users su
                ON s.UserId = su.Id

            INNER JOIN Classes c
                ON sc.ClassId = c.Id

            INNER JOIN TeachingAssignment ta
                ON sc.TeachingAssignmentId = ta.Id

            INNER JOIN Courses co
                ON ta.CourseId = co.Id

            INNER JOIN Teachers t
                ON ta.TeacherId = t.Id

            INNER JOIN Users tu
                ON t.UserId = tu.Id

            INNER JOIN Semesters sem
                ON ta.SemesterId = sem.Id

            ORDER BY sc.Id DESC
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                StudentCourse studentCourse = mapStudentCourse(rs);
                list.add(studentCourse);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /*
     * Tìm kiếm theo mã sinh viên, tên sinh viên, mã lớp,
     * mã môn học, tên môn học, mã giáo viên hoặc tên giáo viên
     */
    public ArrayList<StudentCourse> searchStudentCourses(String keyword) {

        ArrayList<StudentCourse> list = new ArrayList<>();

        String sql = """
            SELECT
                sc.Id,
                sc.StudentId,
                sc.ClassId,
                sc.TeachingAssignmentId,

                s.StudentCode,
                su.FullName AS StudentName,

                c.ClassCode,
                c.ClassName,

                co.CourseCode,
                co.CourseName,

                t.TeacherCode,
                tu.FullName AS TeacherName,

                sem.SemesterName,
                sem.SchoolYear

            FROM StudentCourses sc

            INNER JOIN Students s
                ON sc.StudentId = s.Id

            INNER JOIN Users su
                ON s.UserId = su.Id

            INNER JOIN Classes c
                ON sc.ClassId = c.Id

            INNER JOIN TeachingAssignment ta
                ON sc.TeachingAssignmentId = ta.Id

            INNER JOIN Courses co
                ON ta.CourseId = co.Id

            INNER JOIN Teachers t
                ON ta.TeacherId = t.Id

            INNER JOIN Users tu
                ON t.UserId = tu.Id

            INNER JOIN Semesters sem
                ON ta.SemesterId = sem.Id

            WHERE
                s.StudentCode LIKE ?
                OR su.FullName LIKE ?
                OR c.ClassCode LIKE ?
                OR c.ClassName LIKE ?
                OR co.CourseCode LIKE ?
                OR co.CourseName LIKE ?
                OR t.TeacherCode LIKE ?
                OR tu.FullName LIKE ?
                OR sem.SemesterName LIKE ?
                OR sem.SchoolYear LIKE ?

            ORDER BY sc.Id DESC
        """;

        String searchValue = "%" + keyword.trim() + "%";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            for (int i = 1; i <= 10; i++) {
                ps.setString(i, searchValue);
            }

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    StudentCourse studentCourse = mapStudentCourse(rs);
                    list.add(studentCourse);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /*
     * Lấy enrollment theo id
     */
    public StudentCourse getStudentCourseById(int id) {

        String sql = """
            SELECT
                sc.Id,
                sc.StudentId,
                sc.ClassId,
                sc.TeachingAssignmentId,

                s.StudentCode,
                su.FullName AS StudentName,

                c.ClassCode,
                c.ClassName,

                co.CourseCode,
                co.CourseName,

                t.TeacherCode,
                tu.FullName AS TeacherName,

                sem.SemesterName,
                sem.SchoolYear

            FROM StudentCourses sc

            INNER JOIN Students s
                ON sc.StudentId = s.Id

            INNER JOIN Users su
                ON s.UserId = su.Id

            INNER JOIN Classes c
                ON sc.ClassId = c.Id

            INNER JOIN TeachingAssignment ta
                ON sc.TeachingAssignmentId = ta.Id

            INNER JOIN Courses co
                ON ta.CourseId = co.Id

            INNER JOIN Teachers t
                ON ta.TeacherId = t.Id

            INNER JOIN Users tu
                ON t.UserId = tu.Id

            INNER JOIN Semesters sem
                ON ta.SemesterId = sem.Id

            WHERE sc.Id = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapStudentCourse(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
     * Thêm sinh viên vào TeachingAssignment
     */
    public boolean insertStudentCourse(StudentCourse studentCourse) {

        String sql = """
            INSERT INTO StudentCourses
            (
                StudentId,
                ClassId,
                TeachingAssignmentId
            )
            VALUES (?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, studentCourse.getStudentId());
            ps.setInt(2, studentCourse.getClassId());
            ps.setInt(3, studentCourse.getTeachingAssignmentId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /*
     * Xóa enrollment
     */
    public boolean deleteStudentCourse(int id) {

        String sql = """
            DELETE FROM StudentCourses
            WHERE Id = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /*
     * Kiểm tra id enrollment có tồn tại không
     */
    public boolean checkStudentCourseId(int id) {

        String sql = """
            SELECT 1
            FROM StudentCourses
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

    /*
     * Kiểm tra sinh viên đã đăng ký TeachingAssignment này chưa
     */
    public boolean checkEnrollmentExists(
            int studentId,
            int teachingAssignmentId) {

        String sql = """
            SELECT 1
            FROM StudentCourses
            WHERE StudentId = ?
              AND TeachingAssignmentId = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, teachingAssignmentId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /*
     * Kiểm tra ClassId có đúng với TeachingAssignment không
     */
    public boolean checkTeachingAssignmentClass(
            int teachingAssignmentId,
            int classId) {

        String sql = """
            SELECT 1
            FROM TeachingAssignment
            WHERE Id = ?
              AND ClassId = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, teachingAssignmentId);
            ps.setInt(2, classId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /*
     * Lấy ClassId của TeachingAssignment.
     * Hàm này giúp form chỉ cần gửi studentId và teachingAssignmentId.
     */
    public int getClassIdByTeachingAssignmentId(
            int teachingAssignmentId) {

        String sql = """
            SELECT ClassId
            FROM TeachingAssignment
            WHERE Id = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, teachingAssignmentId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("ClassId");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /*
     * Chuyển ResultSet thành StudentCourse
     */
    private StudentCourse mapStudentCourse(ResultSet rs)
            throws SQLException {

        return new StudentCourse(
                rs.getInt("Id"),
                rs.getInt("StudentId"),
                rs.getInt("ClassId"),
                rs.getInt("TeachingAssignmentId"),
                rs.getString("StudentCode"),
                rs.getString("StudentName"),
                rs.getString("ClassCode"),
                rs.getString("ClassName"),
                rs.getString("CourseCode"),
                rs.getString("CourseName"),
                rs.getString("TeacherCode"),
                rs.getString("TeacherName"),
                rs.getString("SemesterName"),
                rs.getString("SchoolYear")
        );
    }
}