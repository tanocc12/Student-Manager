package Controller;

import DAO.StudentCourseDAO;
import DAO.StudentDAO;
import DAO.TeachingAssignmentDAO;

import Models.Student;
import Models.StudentCourse;
import Models.TeachingAssignment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "StudentCourseServlet",
        urlPatterns = {"/StudentCourseServlet"})
public class StudentCourseServlet extends HttpServlet {

    private static final String LIST_PAGE
            = "/admin/student-course/list.jsp";

    private static final String FORM_PAGE
            = "/admin/student-course/form.jsp";

    private StudentCourseDAO studentCourseDAO;
    private StudentDAO studentDAO;
    private TeachingAssignmentDAO teachingAssignmentDAO;

    @Override
    public void init() throws ServletException {
        studentCourseDAO = new StudentCourseDAO();
        studentDAO = new StudentDAO();
        teachingAssignmentDAO = new TeachingAssignmentDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            action = "list";
        }

        switch (action) {

            case "list":
                showStudentCourseList(request, response);
                break;

            case "create":
                showCreateForm(request, response);
                break;

            case "delete":
                deleteStudentCourse(request, response);
                break;

            default:
                showStudentCourseList(request, response);
                break;
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            action = "list";
        }

        switch (action) {

            case "insert":
                insertStudentCourse(request, response);
                break;

            case "delete":
                deleteStudentCourse(request, response);
                break;

            default:
                redirectToList(request, response);
                break;
        }
    }

    /*
     * Hiển thị danh sách hoặc tìm kiếm enrollment.
     */
    private void showStudentCourseList(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");

        ArrayList<StudentCourse> studentCourseList;

        if (keyword == null || keyword.trim().isEmpty()) {

            studentCourseList
                    = studentCourseDAO.getAllStudentCourses();

        } else {

            keyword = keyword.trim();

            studentCourseList
                    = studentCourseDAO.searchStudentCourses(keyword);
        }

        request.setAttribute(
                "studentCourseList",
                studentCourseList
        );

        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher(LIST_PAGE)
                .forward(request, response);
    }

    /*
     * Mở form tạo enrollment.
     */
    private void showCreateForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        loadFormData(request);

        request.getRequestDispatcher(FORM_PAGE)
                .forward(request, response);
    }

    /*
     * Thêm sinh viên vào Teaching Assignment.
     */
    private void insertStudentCourse(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String studentIdRaw
                = request.getParameter("studentId");

        String teachingAssignmentIdRaw
                = request.getParameter("teachingAssignmentId");

        int studentId;
        int teachingAssignmentId;

        /*
         * Kiểm tra dữ liệu rỗng.
         */
        if (studentIdRaw == null
                || studentIdRaw.trim().isEmpty()
                || teachingAssignmentIdRaw == null
                || teachingAssignmentIdRaw.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Please select a student and teaching assignment."
            );

            loadFormData(request);

            request.getRequestDispatcher(FORM_PAGE)
                    .forward(request, response);

            return;
        }

        /*
         * Kiểm tra dữ liệu có phải số không.
         */
        try {

            studentId = Integer.parseInt(studentIdRaw);
            teachingAssignmentId
                    = Integer.parseInt(teachingAssignmentIdRaw);

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "error",
                    "Invalid student or teaching assignment."
            );

            loadFormData(request);

            request.getRequestDispatcher(FORM_PAGE)
                    .forward(request, response);

            return;
        }

        /*
         * Giữ lại dữ liệu khi form báo lỗi.
         */
        request.setAttribute(
                "selectedStudentId",
                studentId
        );

        request.setAttribute(
                "selectedTeachingAssignmentId",
                teachingAssignmentId
        );

        /*
         * Kiểm tra student có tồn tại.
         */
        if (!studentDAO.checkStudentId(studentId)) {

            request.setAttribute(
                    "error",
                    "Student does not exist."
            );

            loadFormData(request);

            request.getRequestDispatcher(FORM_PAGE)
                    .forward(request, response);

            return;
        }

        /*
         * Kiểm tra Teaching Assignment có tồn tại.
         */
        if (!teachingAssignmentDAO
                .checkTeachingAssignmentId(
                        teachingAssignmentId)) {

            request.setAttribute(
                    "error",
                    "Teaching assignment does not exist."
            );

            loadFormData(request);

            request.getRequestDispatcher(FORM_PAGE)
                    .forward(request, response);

            return;
        }

        /*
         * Kiểm tra sinh viên đã đăng ký assignment này chưa.
         */
        if (studentCourseDAO.checkEnrollmentExists(
                studentId,
                teachingAssignmentId)) {

            request.setAttribute(
                    "error",
                    "This student is already enrolled "
                    + "in this teaching assignment."
            );

            loadFormData(request);

            request.getRequestDispatcher(FORM_PAGE)
                    .forward(request, response);

            return;
        }

        /*
         * ClassId phải lấy từ TeachingAssignment.
         * Không lấy ClassId trực tiếp từ JSP.
         */
        int classId
                = studentCourseDAO
                        .getClassIdByTeachingAssignmentId(
                                teachingAssignmentId
                        );

        if (classId <= 0) {

            request.setAttribute(
                    "error",
                    "Cannot determine the class "
                    + "of this teaching assignment."
            );

            loadFormData(request);

            request.getRequestDispatcher(FORM_PAGE)
                    .forward(request, response);

            return;
        }

        StudentCourse studentCourse
                = new StudentCourse(
                        studentId,
                        classId,
                        teachingAssignmentId
                );

        boolean inserted
                = studentCourseDAO
                        .insertStudentCourse(studentCourse);

        if (inserted) {

            request.getSession().setAttribute(
                    "success",
                    "Student enrolled successfully."
            );

            redirectToList(request, response);

        } else {

            request.setAttribute(
                    "error",
                    "Unable to enroll student."
            );

            loadFormData(request);

            request.getRequestDispatcher(FORM_PAGE)
                    .forward(request, response);
        }
    }

    /*
     * Xóa enrollment.
     */
    private void deleteStudentCourse(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idRaw = request.getParameter("id");

        if (idRaw == null || idRaw.trim().isEmpty()) {

            request.getSession().setAttribute(
                    "error",
                    "Enrollment ID is required."
            );

            redirectToList(request, response);
            return;
        }

        int id;

        try {

            id = Integer.parseInt(idRaw);

        } catch (NumberFormatException e) {

            request.getSession().setAttribute(
                    "error",
                    "Invalid enrollment ID."
            );

            redirectToList(request, response);
            return;
        }

        if (!studentCourseDAO.checkStudentCourseId(id)) {

            request.getSession().setAttribute(
                    "error",
                    "Enrollment does not exist."
            );

            redirectToList(request, response);
            return;
        }

        boolean deleted
                = studentCourseDAO.deleteStudentCourse(id);

        if (deleted) {

            request.getSession().setAttribute(
                    "success",
                    "Enrollment deleted successfully."
            );

        } else {

            request.getSession().setAttribute(
                    "error",
                    "Unable to delete enrollment. "
                    + "The enrollment may already have grades."
            );
        }

        redirectToList(request, response);
    }

    /*
     * Nạp dữ liệu cho dropdown Student
     * và Teaching Assignment.
     */
    private void loadFormData(
            HttpServletRequest request) {

        List<Student> studentList
                = studentDAO.getAllStudents();

        ArrayList<TeachingAssignment> teachingAssignmentList
                = teachingAssignmentDAO
                        .getAllTeachingAssignments();

        request.setAttribute(
                "studentList",
                studentList
        );

        request.setAttribute(
                "teachingAssignmentList",
                teachingAssignmentList
        );
    }

    /*
     * Redirect về danh sách enrollment.
     */
    private void redirectToList(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/StudentCourseServlet?action=list"
        );
    }
}