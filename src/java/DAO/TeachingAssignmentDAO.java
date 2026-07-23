package DAO;

import DAL.DBContext;
import Models.Semester;
import Models.Teacher;
import Models.TeachingAssignment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeachingAssignmentDAO extends DBContext {

    public TeachingAssignmentDAO() {
        super();
    }

    public ArrayList<TeachingAssignment> getAllTeachingAssignments() {

        ArrayList<TeachingAssignment> list = new ArrayList<>();

        String sql = """
            SELECT
                ta.Id,
                ta.TeacherId,
                ta.ClassId,
                ta.CourseId,
                ta.SemesterId,

                t.TeacherCode,
                u.FullName AS TeacherName,

                c.ClassCode,
                c.ClassName,

                co.CourseCode,
                co.CourseName,

                s.SemesterName,
                s.SchoolYear

            FROM TeachingAssignment ta

            INNER JOIN Teachers t
                ON ta.TeacherId = t.Id

            INNER JOIN Users u
                ON t.UserId = u.Id

            INNER JOIN Classes c
                ON ta.ClassId = c.Id

            INNER JOIN Courses co
                ON ta.CourseId = co.Id

            INNER JOIN Semesters s
                ON ta.SemesterId = s.Id

            ORDER BY
                s.SchoolYear DESC,
                s.SemesterName,
                c.ClassCode,
                co.CourseCode
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                TeachingAssignment ta = new TeachingAssignment(
                        rs.getInt("Id"),
                        rs.getInt("TeacherId"),
                        rs.getInt("ClassId"),
                        rs.getInt("CourseId"),
                        rs.getInt("SemesterId"),
                        rs.getString("TeacherCode"),
                        rs.getString("TeacherName"),
                        rs.getString("ClassCode"),
                        rs.getString("ClassName"),
                        rs.getString("CourseCode"),
                        rs.getString("CourseName"),
                        rs.getString("SemesterName"),
                        rs.getString("SchoolYear")
                );

                list.add(ta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean checkTeachingAssignmentId(int id) {

        String sql = """
            SELECT 1
            FROM TeachingAssignment
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

    public ArrayList<TeachingAssignment>
            getTeachingAssignmentsByTeacherUserId(int teacherUserId) {

        ArrayList<TeachingAssignment> list = new ArrayList<>();

        String sql = """
        SELECT
            ta.Id,
            ta.TeacherId,
            ta.ClassId,
            ta.CourseId,
            ta.SemesterId,

            t.TeacherCode,
            u.FullName AS TeacherName,

            c.ClassCode,
            c.ClassName,

            co.CourseCode,
            co.CourseName,

            s.SemesterName,
            s.SchoolYear

        FROM TeachingAssignment ta

        INNER JOIN Teachers t
            ON ta.TeacherId = t.Id

        INNER JOIN Users u
            ON t.UserId = u.Id

        INNER JOIN Classes c
            ON ta.ClassId = c.Id

        INNER JOIN Courses co
            ON ta.CourseId = co.Id

        INNER JOIN Semesters s
            ON ta.SemesterId = s.Id

        WHERE t.UserId = ?

        ORDER BY
            s.SchoolYear DESC,
            s.SemesterName,
            c.ClassCode,
            co.CourseCode
    """;

        if (connection == null) {
            return list;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, teacherUserId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    TeachingAssignment ta = new TeachingAssignment(
                            rs.getInt("Id"),
                            rs.getInt("TeacherId"),
                            rs.getInt("ClassId"),
                            rs.getInt("CourseId"),
                            rs.getInt("SemesterId"),
                            rs.getString("TeacherCode"),
                            rs.getString("TeacherName"),
                            rs.getString("ClassCode"),
                            rs.getString("ClassName"),
                            rs.getString("CourseCode"),
                            rs.getString("CourseName"),
                            rs.getString("SemesterName"),
                            rs.getString("SchoolYear")
                    );

                    list.add(ta);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean belongsToTeacherUser(
            int teachingAssignmentId,
            int teacherUserId) {

        String sql = """
        SELECT 1
        FROM TeachingAssignment ta

        INNER JOIN Teachers t
            ON ta.TeacherId = t.Id

        WHERE ta.Id = ?
          AND t.UserId = ?
    """;

        if (connection == null) {
            return false;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, teachingAssignmentId);
            ps.setInt(2, teacherUserId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

// =====================================================
// Admin Module
// =====================================================
    public TeachingAssignment getTeachingAssignmentById(int id) {

        String sql = """
        SELECT
            ta.Id,
            ta.TeacherId,
            ta.ClassId,
            ta.CourseId,
            ta.SemesterId,

            t.TeacherCode,
            u.FullName AS TeacherName,

            c.ClassCode,
            c.ClassName,

            co.CourseCode,
            co.CourseName,

            s.SemesterName,
            s.SchoolYear

        FROM TeachingAssignment ta

        INNER JOIN Teachers t
            ON ta.TeacherId = t.Id

        INNER JOIN Users u
            ON t.UserId = u.Id

        INNER JOIN Classes c
            ON ta.ClassId = c.Id

        INNER JOIN Courses co
            ON ta.CourseId = co.Id

        INNER JOIN Semesters s
            ON ta.SemesterId = s.Id

        WHERE ta.Id = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new TeachingAssignment(
                            rs.getInt("Id"),
                            rs.getInt("TeacherId"),
                            rs.getInt("ClassId"),
                            rs.getInt("CourseId"),
                            rs.getInt("SemesterId"),
                            rs.getString("TeacherCode"),
                            rs.getString("TeacherName"),
                            rs.getString("ClassCode"),
                            rs.getString("ClassName"),
                            rs.getString("CourseCode"),
                            rs.getString("CourseName"),
                            rs.getString("SemesterName"),
                            rs.getString("SchoolYear")
                    );

                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int createTeachingAssignment(
            TeachingAssignment assignment) {

        String sql = """
        INSERT INTO TeachingAssignment
        (
            TeacherId,
            ClassId,
            CourseId,
            SemesterId
        )
        VALUES
        (
            ?, ?, ?, ?
        )
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, assignment.getTeacherId());
            ps.setInt(2, assignment.getClassId());
            ps.setInt(3, assignment.getCourseId());
            ps.setInt(4, assignment.getSemesterId());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int editTeachingAssignment(
            TeachingAssignment assignment) {

        String sql = """
        UPDATE TeachingAssignment
        SET
            TeacherId = ?,
            ClassId = ?,
            CourseId = ?,
            SemesterId = ?
        WHERE Id = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, assignment.getTeacherId());
            ps.setInt(2, assignment.getClassId());
            ps.setInt(3, assignment.getCourseId());
            ps.setInt(4, assignment.getSemesterId());
            ps.setInt(5, assignment.getId());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int deleteTeachingAssignment(int id) {

        String sql = """
        DELETE FROM TeachingAssignment
        WHERE Id = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public ArrayList<TeachingAssignment> searchTeachingAssignment(
            String keyword) {

        ArrayList<TeachingAssignment> list = new ArrayList<>();

        String sql = """
        SELECT
            ta.Id,
            ta.TeacherId,
            ta.ClassId,
            ta.CourseId,
            ta.SemesterId,

            t.TeacherCode,
            u.FullName AS TeacherName,

            c.ClassCode,
            c.ClassName,

            co.CourseCode,
            co.CourseName,

            s.SemesterName,
            s.SchoolYear

        FROM TeachingAssignment ta

        INNER JOIN Teachers t
            ON ta.TeacherId = t.Id

        INNER JOIN Users u
            ON t.UserId = u.Id

        INNER JOIN Classes c
            ON ta.ClassId = c.Id

        INNER JOIN Courses co
            ON ta.CourseId = co.Id

        INNER JOIN Semesters s
            ON ta.SemesterId = s.Id

        WHERE
            t.TeacherCode LIKE ?
            OR u.FullName LIKE ?
            OR c.ClassCode LIKE ?
            OR co.CourseCode LIKE ?

        ORDER BY
            s.SchoolYear DESC,
            s.SemesterName,
            c.ClassCode,
            co.CourseCode
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            String search = "%" + keyword + "%";

            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            ps.setString(4, search);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    TeachingAssignment ta = new TeachingAssignment(
                            rs.getInt("Id"),
                            rs.getInt("TeacherId"),
                            rs.getInt("ClassId"),
                            rs.getInt("CourseId"),
                            rs.getInt("SemesterId"),
                            rs.getString("TeacherCode"),
                            rs.getString("TeacherName"),
                            rs.getString("ClassCode"),
                            rs.getString("ClassName"),
                            rs.getString("CourseCode"),
                            rs.getString("CourseName"),
                            rs.getString("SemesterName"),
                            rs.getString("SchoolYear")
                    );

                    list.add(ta);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean isTeachingAssignmentExists(
            int teacherId,
            int classId,
            int courseId,
            int semesterId) {

        String sql = """
        SELECT 1
        FROM TeachingAssignment
        WHERE TeacherId = ?
          AND ClassId = ?
          AND CourseId = ?
          AND SemesterId = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, teacherId);
            ps.setInt(2, classId);
            ps.setInt(3, courseId);
            ps.setInt(4, semesterId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isTeachingAssignmentExists(
            int id,
            int teacherId,
            int classId,
            int courseId,
            int semesterId) {

        String sql = """
        SELECT 1
        FROM TeachingAssignment
        WHERE TeacherId = ?
          AND ClassId = ?
          AND CourseId = ?
          AND SemesterId = ?
          AND Id <> ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, teacherId);
            ps.setInt(2, classId);
            ps.setInt(3, courseId);
            ps.setInt(4, semesterId);
            ps.setInt(5, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<Teacher> getTeacherOptions() {

    ArrayList<Teacher> list = new ArrayList<>();

    String sql = """
        SELECT
            t.Id,
            t.TeacherCode,
            u.FullName
        FROM Teachers t
        INNER JOIN Users u
            ON t.UserId = u.Id
        ORDER BY
            t.TeacherCode
    """;

    try (PreparedStatement ps = connection.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

            Teacher teacher = new Teacher();

            teacher.setId(rs.getInt("Id"));
            teacher.setTeacherCode(rs.getString("TeacherCode"));
            teacher.setFullName(rs.getString("FullName"));

            list.add(teacher);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}

    public ArrayList<Semester> getSemesterOptions() {

        ArrayList<Semester> list = new ArrayList<>();

        String sql = """
        SELECT
            Id,
            SemesterName,
            SchoolYear
        FROM Semesters
        ORDER BY
            SchoolYear DESC,
            SemesterName
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Semester semester = new Semester();

                semester.setId(rs.getInt("Id"));
                semester.setSemesterName(
                        rs.getString("SemesterName"));

                semester.setSchoolYear(
                        rs.getString("SchoolYear"));

                list.add(semester);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
