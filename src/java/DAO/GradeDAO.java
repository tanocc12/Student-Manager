package DAO;

import DAL.DBContext;
import Models.Grade;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class GradeDAO extends DBContext {

    public List<Grade> getGradesByTeachingAssignment(
            int teachingAssignmentId) {

        List<Grade> gradeList = new ArrayList<>();

        String sql = """
                     SELECT
                         sc.Id AS StudentCourseId,
                         st.Id AS StudentId,
                         st.StudentCode,
                         u.FullName AS StudentName,
                         g.Id AS GradeId,
                         g.Assignment,
                         g.PracticalExam,
                         g.FinalExam,
                         g.Average
                     FROM StudentCourses sc
                     INNER JOIN Students st
                         ON sc.StudentId = st.Id
                     INNER JOIN Users u
                         ON st.UserId = u.Id
                     LEFT JOIN Grades g
                         ON sc.Id = g.StudentCourseId
                     WHERE sc.TeachingAssignmentId = ?
                     ORDER BY u.FullName, st.StudentCode
                     """;

        if (connection == null) {
            System.out.println("GradeDAO: connection is null.");
            return gradeList;
        }

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, teachingAssignmentId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Grade grade = new Grade();

                    grade.setStudentCourseId(
                            resultSet.getInt("StudentCourseId")
                    );

                    grade.setStudentId(
                            resultSet.getInt("StudentId")
                    );

                    grade.setStudentCode(
                            resultSet.getString("StudentCode")
                    );

                    grade.setStudentName(
                            resultSet.getString("StudentName")
                    );

                    int gradeId = resultSet.getInt("GradeId");

                    if (resultSet.wasNull()) {
                        grade.setId(0);
                    } else {
                        grade.setId(gradeId);
                    }

                    grade.setAssignment(
                            getNullableDouble(
                                    resultSet,
                                    "Assignment"
                            )
                    );

                    grade.setPracticalExam(
                            getNullableDouble(
                                    resultSet,
                                    "PracticalExam"
                            )
                    );

                    grade.setFinalExam(
                            getNullableDouble(
                                    resultSet,
                                    "FinalExam"
                            )
                    );

                    grade.setAverage(
                            getNullableDouble(
                                    resultSet,
                                    "Average"
                            )
                    );

                    gradeList.add(grade);
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return gradeList;
    }


    public boolean gradeExists(int studentCourseId) {

        String sql = """
                     SELECT 1
                     FROM Grades
                     WHERE StudentCourseId = ?
                     """;

        if (connection == null) {
            return false;
        }

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentCourseId);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }


    public boolean insertGrade(Grade grade) {

        String sql = """
                     INSERT INTO Grades
                     (
                         StudentCourseId,
                         Assignment,
                         PracticalExam,
                         FinalExam,
                         Average
                     )
                     VALUES (?, ?, ?, ?, ?)
                     """;

        if (connection == null) {
            return false;
        }

        double average = calculateAverage(
                grade.getAssignment(),
                grade.getPracticalExam(),
                grade.getFinalExam()
        );

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    grade.getStudentCourseId()
            );

            setNullableDouble(
                    statement,
                    2,
                    grade.getAssignment()
            );

            setNullableDouble(
                    statement,
                    3,
                    grade.getPracticalExam()
            );

            setNullableDouble(
                    statement,
                    4,
                    grade.getFinalExam()
            );

            statement.setDouble(5, average);

            return statement.executeUpdate() > 0;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }


    public boolean updateGrade(Grade grade) {

        String sql = """
                     UPDATE Grades
                     SET Assignment = ?,
                         PracticalExam = ?,
                         FinalExam = ?,
                         Average = ?
                     WHERE StudentCourseId = ?
                     """;

        if (connection == null) {
            return false;
        }

        double average = calculateAverage(
                grade.getAssignment(),
                grade.getPracticalExam(),
                grade.getFinalExam()
        );

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            setNullableDouble(
                    statement,
                    1,
                    grade.getAssignment()
            );

            setNullableDouble(
                    statement,
                    2,
                    grade.getPracticalExam()
            );

            setNullableDouble(
                    statement,
                    3,
                    grade.getFinalExam()
            );

            statement.setDouble(4, average);

            statement.setInt(
                    5,
                    grade.getStudentCourseId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }


    public boolean saveOrUpdateGrade(Grade grade) {

        if (gradeExists(grade.getStudentCourseId())) {
            return updateGrade(grade);
        }

        return insertGrade(grade);
    }


    public boolean belongsToTeachingAssignment(
            int studentCourseId,
            int teachingAssignmentId) {

        String sql = """
                     SELECT 1
                     FROM StudentCourses
                     WHERE Id = ?
                       AND TeachingAssignmentId = ?
                     """;

        if (connection == null) {
            return false;
        }

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentCourseId);
            statement.setInt(2, teachingAssignmentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }


    private double calculateAverage(
            Double assignment,
            Double practicalExam,
            Double finalExam) {

        double assignmentValue =
                assignment == null ? 0 : assignment;

        double practicalValue =
                practicalExam == null ? 0 : practicalExam;

        double finalValue =
                finalExam == null ? 0 : finalExam;

        double average =
                assignmentValue * 0.3
                + practicalValue * 0.3
                + finalValue * 0.4;

        return Math.round(average * 100.0) / 100.0;
    }


    private Double getNullableDouble(
            ResultSet resultSet,
            String columnName)
            throws SQLException {

        double value = resultSet.getDouble(columnName);

        if (resultSet.wasNull()) {
            return null;
        }

        return value;
    }


    private void setNullableDouble(
            PreparedStatement statement,
            int parameterIndex,
            Double value)
            throws SQLException {

        if (value == null) {
            statement.setNull(
                    parameterIndex,
                    java.sql.Types.DECIMAL
            );
        } else {
            statement.setDouble(
                    parameterIndex,
                    value
            );
        }
    }
}