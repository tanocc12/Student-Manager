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

    public List<ClassRoom> getAllClasses() {

        List<ClassRoom> classes = new ArrayList<>();

        String sql = """
                     SELECT c.Id,
                            c.ClassCode,
                            c.ClassName,
                            c.MajorId,
                            m.MajorName
                     FROM Classes c
                     INNER JOIN Majors m
                             ON c.MajorId = m.Id
                     ORDER BY c.ClassName
                     """;

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ClassRoom classRoom = new ClassRoom();

                classRoom.setId(rs.getInt("Id"));
                classRoom.setClassCode(
                        rs.getString("ClassCode")
                );
                classRoom.setClassName(
                        rs.getString("ClassName")
                );
                classRoom.setMajorId(
                        rs.getInt("MajorId")
                );
                classRoom.setMajorName(
                        rs.getString("MajorName")
                );

                classes.add(classRoom);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classes;
    }

    public ClassRoom getClassById(int id) {

        String sql = """
                     SELECT c.Id,
                            c.ClassCode,
                            c.ClassName,
                            c.MajorId,
                            m.MajorName
                     FROM Classes c
                     INNER JOIN Majors m
                             ON c.MajorId = m.Id
                     WHERE c.Id = ?
                     """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    ClassRoom classRoom = new ClassRoom();

                    classRoom.setId(rs.getInt("Id"));
                    classRoom.setClassCode(
                            rs.getString("ClassCode")
                    );
                    classRoom.setClassName(
                            rs.getString("ClassName")
                    );
                    classRoom.setMajorId(
                            rs.getInt("MajorId")
                    );
                    classRoom.setMajorName(
                            rs.getString("MajorName")
                    );

                    return classRoom;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean checkClassId(int id) {

        String sql = """
                     SELECT Id
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

    public boolean checkClassBelongsToMajor(
            int classId,
            int majorId) {

        String sql = """
                     SELECT Id
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
}