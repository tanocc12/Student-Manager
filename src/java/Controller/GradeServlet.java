package Controller;

import DAO.GradeDAO;
import DAO.TeachingAssignmentDAO;
import Models.Grade;
import Models.TeachingAssignment;
import Models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/GradeServlet")
public class GradeServlet extends HttpServlet {

    private GradeDAO gradeDAO;
    private TeachingAssignmentDAO teachingAssignmentDAO;

    @Override
    public void init() throws ServletException {
        gradeDAO = new GradeDAO();
        teachingAssignmentDAO = new TeachingAssignmentDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("user") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );

            return;
        }

        User user = (User) session.getAttribute("user");

        if (!"Teacher".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect(
                    request.getContextPath() + "/home.jsp"
            );

            return;
        }

        List<TeachingAssignment> teachingAssignmentList
                = teachingAssignmentDAO
                        .getTeachingAssignmentsByTeacherUserId(
                                user.getId()
                        );

        request.setAttribute(
                "teachingAssignmentList",
                teachingAssignmentList
        );

        String teachingAssignmentIdRaw
                = request.getParameter("teachingAssignmentId");

        if (teachingAssignmentIdRaw != null
                && !teachingAssignmentIdRaw.trim().isEmpty()) {

            try {

                int teachingAssignmentId
                        = Integer.parseInt(
                                teachingAssignmentIdRaw
                        );

                /*
                 * Kiểm tra lớp này có thực sự thuộc giáo viên
                 * đang đăng nhập hay không.
                 */
                boolean belongsToTeacher
                        = teachingAssignmentDAO
                                .belongsToTeacherUser(
                                        teachingAssignmentId,
                                        user.getId()
                                );

                if (!belongsToTeacher) {

                    request.setAttribute(
                            "error",
                            "You are not allowed to manage grades "
                            + "for this class."
                    );

                    request.getRequestDispatcher(
                            "/teacher/grades.jsp"
                    ).forward(request, response);

                    return;
                }

                List<Grade> gradeList
                        = gradeDAO
                                .getGradesByTeachingAssignment(
                                        teachingAssignmentId
                                );

                request.setAttribute(
                        "selectedTeachingAssignmentId",
                        teachingAssignmentId
                );

                request.setAttribute(
                        "gradeList",
                        gradeList
                );

            } catch (NumberFormatException exception) {

                request.setAttribute(
                        "error",
                        "Invalid teaching assignment."
                );
            }
        }

        request.getRequestDispatcher(
                "/teacher/grades.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("user") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );

            return;
        }

        User user = (User) session.getAttribute("user");

        if (!"Teacher".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect(
                    request.getContextPath() + "/home.jsp"
            );

            return;
        }

        String action = request.getParameter("action");

        if (!"save".equalsIgnoreCase(action)) {

            response.sendRedirect(
                    request.getContextPath() + "/GradeServlet"
            );

            return;
        }

        String teachingAssignmentIdRaw
                = request.getParameter("teachingAssignmentId");

        if (teachingAssignmentIdRaw == null
                || teachingAssignmentIdRaw.trim().isEmpty()) {

            session.setAttribute(
                    "error",
                    "Please select an assigned class."
            );

            response.sendRedirect(
                    request.getContextPath() + "/GradeServlet"
            );

            return;
        }

        try {

            int teachingAssignmentId
                    = Integer.parseInt(
                            teachingAssignmentIdRaw
                    );

            /*
             * Không cho giáo viên sửa điểm của lớp
             * không thuộc quyền quản lý của mình.
             */
            boolean belongsToTeacher
                    = teachingAssignmentDAO
                            .belongsToTeacherUser(
                                    teachingAssignmentId,
                                    user.getId()
                            );

            if (!belongsToTeacher) {

                session.setAttribute(
                        "error",
                        "You are not allowed to manage grades "
                        + "for this class."
                );

                response.sendRedirect(
                        request.getContextPath()
                        + "/GradeServlet"
                );

                return;
            }

            Map<String, String[]> parameterMap
                    = request.getParameterMap();

            int successCount = 0;
            int failedCount = 0;

            /*
             * Tìm tất cả parameter có dạng:
             *
             * assignment_12
             * assignment_13
             *
             * Phần số phía sau là StudentCourseId.
             */
            for (String parameterName
                    : parameterMap.keySet()) {

                if (!parameterName.startsWith(
                        "assignment_")) {

                    continue;
                }

                String studentCourseIdRaw
                        = parameterName.substring(
                                "assignment_".length()
                        );

                try {

                    int studentCourseId
                            = Integer.parseInt(
                                    studentCourseIdRaw
                            );

                    /*
                     * Kiểm tra StudentCourse này có thuộc đúng
                     * TeachingAssignment đang được chọn không.
                     */
                    boolean validStudentCourse
                            = gradeDAO
                                    .belongsToTeachingAssignment(
                                            studentCourseId,
                                            teachingAssignmentId
                                    );

                    if (!validStudentCourse) {
                        failedCount++;
                        continue;
                    }

                    String assignmentRaw
                            = request.getParameter(
                                    "assignment_"
                                    + studentCourseId
                            );

                    String practicalExamRaw
                            = request.getParameter(
                                    "practicalExam_"
                                    + studentCourseId
                            );

                    String finalExamRaw
                            = request.getParameter(
                                    "finalExam_"
                                    + studentCourseId
                            );

                    if (isEmpty(assignmentRaw)
                            || isEmpty(practicalExamRaw)
                            || isEmpty(finalExamRaw)) {

                        failedCount++;
                        continue;
                    }

                    double assignment
                            = Double.parseDouble(
                                    assignmentRaw
                            );

                    double practicalExam
                            = Double.parseDouble(
                                    practicalExamRaw
                            );

                    double finalExam
                            = Double.parseDouble(
                                    finalExamRaw
                            );

                    if (!isValidGrade(assignment)
                            || !isValidGrade(practicalExam)
                            || !isValidGrade(finalExam)) {

                        failedCount++;
                        continue;
                    }

                    Grade grade = new Grade();

                    grade.setStudentCourseId(
                            studentCourseId
                    );

                    grade.setAssignment(
                            assignment
                    );

                    grade.setPracticalExam(
                            practicalExam
                    );

                    grade.setFinalExam(
                            finalExam
                    );

                    boolean saved
                            = gradeDAO
                                    .saveOrUpdateGrade(
                                            grade
                                    );

                    if (saved) {
                        successCount++;
                    } else {
                        failedCount++;
                    }

                } catch (NumberFormatException exception) {
                    failedCount++;
                }
            }

            if (failedCount == 0) {

                session.setAttribute(
                        "success",
                        "Grades saved successfully for "
                        + successCount
                        + " student(s)."
                );

            } else {

                session.setAttribute(
                        "error",
                        "Saved "
                        + successCount
                        + " student(s), but "
                        + failedCount
                        + " record(s) could not be saved."
                );
            }

            response.sendRedirect(
                    request.getContextPath()
                    + "/GradeServlet?action=list"
                    + "&teachingAssignmentId="
                    + teachingAssignmentId
            );

        } catch (NumberFormatException exception) {

            session.setAttribute(
                    "error",
                    "Invalid teaching assignment."
            );

            response.sendRedirect(
                    request.getContextPath()
                    + "/GradeServlet"
            );
        }
    }

    private boolean isValidGrade(double grade) {
        return grade >= 0 && grade <= 10;
    }

    private boolean isEmpty(String value) {
        return value == null
                || value.trim().isEmpty();
    }
}