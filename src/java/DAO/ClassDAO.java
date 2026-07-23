package DAO;

import DAL.DBContext;
import Models.ClassRoom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassDAO extends DBContext {

    public ClassDAO() {
        super();
    }

    /*
     * Lấy toàn bộ lớp học, kèm tên chuyên ngành và môn học.
     */
    public List<ClassRoom> getAllClasses() {

        List<ClassRoom> classes = new ArrayList<>();

        String sql = """
                     SELECT c.Id,
                            c.ClassCode,
                            c.ClassName,
                            c.CourseId,
                            co.CourseCode,
                            co.CourseName,
                            c.MajorId,
                            m.MajorCode,
                            m.MajorName
                     FROM Classes c
                     INNER JOIN Courses co
                             ON c.CourseId = co.Id
                     INNER JOIN Majors m
                             ON c.MajorId = m.Id
                     ORDER BY c.ClassCode
                     """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ClassRoom classRoom = mapClassRoom(rs);

                classes.add(classRoom);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classes;
    }

    /*
     * Tìm lớp theo từ khóa mã lớp hoặc tên lớp.
     */
    public List<ClassRoom> searchClasses(String keyword) {

        List<ClassRoom> classes = new ArrayList<>();

        String sql = """
                     SELECT c.Id,
                            c.ClassCode,
                            c.ClassName,
                            c.CourseId,
                            co.CourseCode,
                            co.CourseName,
                            c.MajorId,
                            m.MajorCode,
                            m.MajorName
                     FROM Classes c
                     INNER JOIN Courses co
                             ON c.CourseId = co.Id
                     INNER JOIN Majors m
                             ON c.MajorId = m.Id
                     WHERE c.ClassCode LIKE ?
                        OR c.ClassName LIKE ?
                        OR co.CourseCode LIKE ?
                        OR co.CourseName LIKE ?
                        OR m.MajorCode LIKE ?
                        OR m.MajorName LIKE ?
                     ORDER BY c.ClassCode
                     """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            String searchValue = "%" + keyword.trim() + "%";

            ps.setString(1, searchValue);
            ps.setString(2, searchValue);
            ps.setString(3, searchValue);
            ps.setString(4, searchValue);
            ps.setString(5, searchValue);
            ps.setString(6, searchValue);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    classes.add(mapClassRoom(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classes;
    }

    /*
     * Lấy một lớp theo Id.
     */
    public ClassRoom getClassById(int id) {

        String sql = """
                     SELECT c.Id,
                            c.ClassCode,
                            c.ClassName,
                            c.CourseId,
                            co.CourseCode,
                            co.CourseName,
                            c.MajorId,
                            m.MajorCode,
                            m.MajorName
                     FROM Classes c
                     INNER JOIN Courses co
                             ON c.CourseId = co.Id
                     INNER JOIN Majors m
                             ON c.MajorId = m.Id
                     WHERE c.Id = ?
                     """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapClassRoom(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
     * Thêm lớp mới.
     */
    public boolean insertClass(ClassRoom classroom) {

        String sql
                = "INSERT INTO Classes "
                + "(ClassCode, ClassName, CourseId, MajorId) "
                + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, classroom.getClassCode());
            ps.setString(2, classroom.getClassName());
            ps.setInt(3, classroom.getCourseId());
            ps.setInt(4, classroom.getMajorId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /*
     * Cập nhật lớp.
     */
    public boolean updateClass(ClassRoom classRoom) {

        String sql = """
                     UPDATE Classes
                     SET ClassCode = ?,
                         ClassName = ?,
                         CourseId = ?,
                         MajorId = ?
                     WHERE Id = ?
                     """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, classRoom.getClassCode());
            ps.setString(2, classRoom.getClassName());
            ps.setInt(3, classRoom.getCourseId());
            ps.setInt(4, classRoom.getMajorId());
            ps.setInt(5, classRoom.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /*
     * Xóa lớp.
     */
    public boolean deleteClass(int id) {

        String sql = """
                     DELETE FROM Classes
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
     * Kiểm tra Id lớp có tồn tại không.
     */
    public boolean checkClassId(int id) {

        String sql = """
                     SELECT 1
                     FROM Classes
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
     * Kiểm tra mã lớp đã tồn tại chưa.
     * Dùng khi thêm mới.
     */
    public boolean checkClassCode(String classCode) {

        String sql = """
                     SELECT 1
                     FROM Classes
                     WHERE ClassCode = ?
                     """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, classCode);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /*
     * Kiểm tra mã lớp trùng với lớp khác.
     * Dùng khi cập nhật.
     */
    public boolean checkClassCodeExceptId(
            String classCode,
            int id) {

        String sql = """
                     SELECT 1
                     FROM Classes
                     WHERE ClassCode = ?
                       AND Id <> ?
                     """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, classCode);
            ps.setInt(2, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /*
     * Kiểm tra lớp có thuộc chuyên ngành được chọn không.
     * Phương thức này đang được StudentServlet sử dụng.
     */
    public boolean checkClassBelongsToMajor(
            int classId,
            int majorId) {

        String sql = """
                     SELECT 1
                     FROM Classes
                     WHERE Id = ?
                       AND MajorId = ?
                     """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, classId);
            ps.setInt(2, majorId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /*
     * Chuyển một dòng ResultSet thành ClassRoom.
     */
    private ClassRoom mapClassRoom(ResultSet rs)
            throws SQLException {

        ClassRoom classRoom = new ClassRoom();

        classRoom.setId(rs.getInt("Id"));
        classRoom.setClassCode(rs.getString("ClassCode"));
        classRoom.setClassName(rs.getString("ClassName"));

        classRoom.setCourseId(rs.getInt("CourseId"));
        classRoom.setCourseCode(rs.getString("CourseCode"));
        classRoom.setCourseName(rs.getString("CourseName"));

        classRoom.setMajorId(rs.getInt("MajorId"));
        classRoom.setMajorCode(rs.getString("MajorCode"));
        classRoom.setMajorName(rs.getString("MajorName"));

        return classRoom;
    }

    public List<ClassRoom> getClasses() {

        List<ClassRoom> list = new ArrayList<>();

        String sql = """
        SELECT
            c.Id,
            c.ClassCode,
            c.ClassName,
            c.CourseId,
            co.CourseCode,
            co.CourseName,
            c.MajorId,
            m.MajorCode,
            m.MajorName
        FROM Classes c

        INNER JOIN Courses co
            ON c.CourseId = co.Id

        INNER JOIN Majors m
            ON c.MajorId = m.Id

        ORDER BY c.Id
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                list.add(mapResultSetToClassRoom(rs));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private ClassRoom mapResultSetToClassRoom(ResultSet rs)
            throws SQLException {

        return new ClassRoom(
                rs.getInt("Id"),
                rs.getString("ClassCode"),
                rs.getString("ClassName"),
                rs.getInt("CourseId"),
                rs.getString("CourseCode"),
                rs.getString("CourseName"),
                rs.getInt("MajorId"),
                rs.getString("MajorCode"),
                rs.getString("MajorName")
        );

    }
}
