package Controller;

import DAO.ClassDAO;
import DAO.CourseDAO;
import DAO.TeachingAssignmentDAO;
import Models.ClassRoom;
import Models.Course;
import Models.Semester;
import Models.Teacher;
import Models.TeachingAssignment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.List;

@WebServlet(
        name = "AssignmentManagementServlet",
        urlPatterns = {"/assignment"}
)
public class AssignmentManagementServlet extends HttpServlet {

    private TeachingAssignmentDAO assignmentDAO;
    private CourseDAO courseDAO;
    private ClassDAO classDAO;

    @Override
    public void init() throws ServletException {

        assignmentDAO = new TeachingAssignmentDAO();

        courseDAO = new CourseDAO();

        classDAO = new ClassDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            action = "list";
        }

        switch (action) {

            case "add":
                showAddForm(req, resp);
                break;

            case "edit":
                showEditForm(req, resp);
                break;

            case "search":
                searchAssignment(req, resp);
                break;

            case "list":
            default:
                listAssignment(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");

        if (action == null || action.trim().isEmpty()) {

            redirectToList(req, resp);
            return;
        }

        switch (action) {

            case "add":
                addAssignment(req, resp);
                break;

            case "edit":
                editAssignment(req, resp);
                break;

            case "delete":
                deleteAssignment(req, resp);
                break;

            default:
                redirectToList(req, resp);
                break;
        }
    }

    private void listAssignment(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        List<TeachingAssignment> assignmentList
                = assignmentDAO.getAllTeachingAssignments();

        req.setAttribute(
                "assignmentList",
                assignmentList);

        req.getRequestDispatcher(
                "/admin/assignments/list.jsp")
                .forward(req, resp);
    }

    private void showAddForm(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        loadDropdownData(req);

        req.setAttribute(
                "formAction",
                "add");

        req.getRequestDispatcher(
                "/admin/assignments/form.jsp")
                .forward(req, resp);
    }

    private void showEditForm(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        int id = parseInt(
                req.getParameter("id"));

        if (id <= 0) {

            redirectToList(req, resp);
            return;
        }

        TeachingAssignment assignment
                = assignmentDAO.getTeachingAssignmentById(id);

        if (assignment == null) {

            redirectToList(req, resp);
            return;
        }

        loadDropdownData(req);

        req.setAttribute(
                "assignment",
                assignment);

        req.setAttribute(
                "formAction",
                "edit");

        req.getRequestDispatcher(
                "/admin/assignments/form.jsp")
                .forward(req, resp);
    }

    private void searchAssignment(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String keyword = req.getParameter("keyword");

        if (keyword == null) {
            keyword = "";
        }

        keyword = keyword.trim();

        List<TeachingAssignment> assignmentList
                = assignmentDAO.searchTeachingAssignment(keyword);

        req.setAttribute("assignmentList", assignmentList);
        req.setAttribute("keyword", keyword);

        req.getRequestDispatcher(
                "/admin/assignments/list.jsp")
                .forward(req, resp);
    }

    private void addAssignment(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        int teacherId = parseInt(req.getParameter("teacherId"));
        int classId = parseInt(req.getParameter("classId"));
        int courseId = parseInt(req.getParameter("courseId"));
        int semesterId = parseInt(req.getParameter("semesterId"));

        TeachingAssignment assignment
                = new TeachingAssignment(
                        0,
                        teacherId,
                        classId,
                        courseId,
                        semesterId
                );

        String error = validateAssignment(assignment);

        if (error != null) {

            loadDropdownData(req);

            req.setAttribute("assignment", assignment);
            req.setAttribute("formAction", "add");
            req.setAttribute("error", error);

            req.getRequestDispatcher(
                    "/admin/assignments/form.jsp")
                    .forward(req, resp);

            return;
        }

        if (assignmentDAO.isTeachingAssignmentExists(
                teacherId,
                classId,
                courseId,
                semesterId)) {

            loadDropdownData(req);

            req.setAttribute("assignment", assignment);
            req.setAttribute("formAction", "add");
            req.setAttribute(
                    "error",
                    "Phân công giảng dạy đã tồn tại.");

            req.getRequestDispatcher(
                    "/admin/assignments/form.jsp")
                    .forward(req, resp);

            return;
        }

        int result
                = assignmentDAO.createTeachingAssignment(assignment);

        if (result > 0) {

            redirectToList(req, resp);

        } else {

            loadDropdownData(req);

            req.setAttribute("assignment", assignment);
            req.setAttribute("formAction", "add");
            req.setAttribute(
                    "error",
                    "Thêm phân công thất bại.");

            req.getRequestDispatcher(
                    "/admin/assignments/form.jsp")
                    .forward(req, resp);
        }

    }

    private void editAssignment(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        int id = parseInt(req.getParameter("id"));

        int teacherId = parseInt(req.getParameter("teacherId"));
        int classId = parseInt(req.getParameter("classId"));
        int courseId = parseInt(req.getParameter("courseId"));
        int semesterId = parseInt(req.getParameter("semesterId"));

        if (id <= 0
                || assignmentDAO.getTeachingAssignmentById(id) == null) {

            redirectToList(req, resp);
            return;
        }

        TeachingAssignment assignment
                = new TeachingAssignment(
                        id,
                        teacherId,
                        classId,
                        courseId,
                        semesterId
                );

        String error = validateAssignment(assignment);

        if (error != null) {

            loadDropdownData(req);

            req.setAttribute("assignment", assignment);
            req.setAttribute("formAction", "edit");
            req.setAttribute("error", error);

            req.getRequestDispatcher(
                    "/admin/assignments/form.jsp")
                    .forward(req, resp);

            return;
        }

        if (assignmentDAO.isTeachingAssignmentExists(
                id,
                teacherId,
                classId,
                courseId,
                semesterId)) {

            loadDropdownData(req);

            req.setAttribute("assignment", assignment);
            req.setAttribute("formAction", "edit");
            req.setAttribute(
                    "error",
                    "Phân công giảng dạy đã tồn tại.");

            req.getRequestDispatcher(
                    "/admin/assignments/form.jsp")
                    .forward(req, resp);

            return;
        }

        int result
                = assignmentDAO.editTeachingAssignment(assignment);

        if (result > 0) {

            redirectToList(req, resp);

        } else {

            loadDropdownData(req);

            req.setAttribute("assignment", assignment);
            req.setAttribute("formAction", "edit");
            req.setAttribute(
                    "error",
                    "Cập nhật phân công thất bại.");

            req.getRequestDispatcher(
                    "/admin/assignments/form.jsp")
                    .forward(req, resp);
        }

    }

    private void deleteAssignment(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {

        int id = parseInt(req.getParameter("id"));

        if (id > 0) {

            assignmentDAO.deleteTeachingAssignment(id);

        }

        redirectToList(req, resp);

    }

    private void loadDropdownData(
            HttpServletRequest req) {

        req.setAttribute(
                "teacherList",
                assignmentDAO.getTeacherOptions());

        req.setAttribute(
                "classList",
                classDAO.getClasses());

        req.setAttribute(
                "courseList",
                courseDAO.getCourses());

        req.setAttribute(
                "semesterList",
                assignmentDAO.getSemesterOptions());

    }

    private String validateAssignment(
            TeachingAssignment assignment) {

        if (assignment.getTeacherId() <= 0) {
            return "Vui lòng chọn giáo viên.";
        }

        if (assignment.getClassId() <= 0) {
            return "Vui lòng chọn lớp học.";
        }

        if (assignment.getCourseId() <= 0) {
            return "Vui lòng chọn môn học.";
        }

        if (assignment.getSemesterId() <= 0) {
            return "Vui lòng chọn học kỳ.";
        }

        return null;
    }

    private int parseInt(String value) {

        if (value == null || value.trim().isEmpty()) {
            return 0;
        }

        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }

    }

    private void redirectToList(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {

        resp.sendRedirect(
                req.getContextPath()
                + "/assignment?action=list");

    }
}
