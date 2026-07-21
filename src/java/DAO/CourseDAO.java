package DAO;

import DAL.DBContext;
import Models.Course;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO extends DBContext {

    public List<Course> getCourses() {
        List<Course> list = new ArrayList<>();

        String sql = "SELECT Id, CourseCode, CourseName, Credits "
                + "FROM Courses "
                + "ORDER BY Id";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToCourse(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Course getCourseById(int id) {

        String sql = "SELECT Id, CourseCode, CourseName, Credits "
                + "FROM Courses "
                + "WHERE Id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapResultSetToCourse(rs);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int createCourse(Course course) {

        String sql = "INSERT INTO Courses (CourseCode, CourseName, Credits) "
                + "VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, course.getCourseCode());
            ps.setString(2, course.getCourseName());
            ps.setInt(3, course.getCredits());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int editCourse(Course course) {

        String sql = "UPDATE Courses "
                + "SET CourseCode = ?, "
                + "CourseName = ?, "
                + "Credits = ? "
                + "WHERE Id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, course.getCourseCode());
            ps.setString(2, course.getCourseName());
            ps.setInt(3, course.getCredits());
            ps.setInt(4, course.getId());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int deleteCourse(int id) {

        String sql = "DELETE FROM Courses WHERE Id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<Course> searchCourse(String keyword) {

        List<Course> list = new ArrayList<>();

        String sql = "SELECT Id, CourseCode, CourseName, Credits "
                + "FROM Courses "
                + "WHERE CourseCode LIKE ? "
                + "OR CourseName LIKE ? "
                + "ORDER BY Id";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            String search = "%" + keyword + "%";

            ps.setString(1, search);
            ps.setString(2, search);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(mapResultSetToCourse(rs));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Convert a row in ResultSet to a Course object.
     */
    private Course mapResultSetToCourse(ResultSet rs) throws SQLException {
        return new Course(
                rs.getInt("Id"),
                rs.getString("CourseCode"),
                rs.getString("CourseName"),
                rs.getInt("Credits")
        );
    }

    public boolean checkCourseId(int id) {

        String sql = "SELECT 1 FROM Courses WHERE Id = ?";

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
