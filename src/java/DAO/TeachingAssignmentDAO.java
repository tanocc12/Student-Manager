package DAO;

import DAL.DBContext;
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

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
}