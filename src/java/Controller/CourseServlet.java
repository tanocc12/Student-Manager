package Controller;

import DAO.CourseDAO;
import Models.Course;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CourseServlet", urlPatterns = {"/course"})
public class CourseServlet extends HttpServlet {

    private CourseDAO courseDAO;

    @Override
    public void init() throws ServletException {
        courseDAO = new CourseDAO();
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
                searchCourse(req, resp);
                break;

            case "list":
            default:
                listCourse(req, resp);
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
                addCourse(req, resp);
                break;

            case "edit":
                editCourse(req, resp);
                break;

            case "delete":
                deleteCourse(req, resp);
                break;

            default:
                redirectToList(req, resp);
                break;
        }
    }

    private void listCourse(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        List<Course> courseList = courseDAO.getCourses();

        req.setAttribute("courseList", courseList);

        req.getRequestDispatcher("/admin/courses/list.jsp")
                .forward(req, resp);
    }

    private void showAddForm(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("formAction", "add");

        req.getRequestDispatcher("/admin/courses/form.jsp")
                .forward(req, resp);
    }

    private void showEditForm(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        int id = parseInt(req.getParameter("id"));

        if (id <= 0) {
            redirectToList(req, resp);
            return;
        }

        Course course = courseDAO.getCourseById(id);

        if (course == null) {
            redirectToList(req, resp);
            return;
        }

        req.setAttribute("course", course);
        req.setAttribute("formAction", "edit");

        req.getRequestDispatcher("/admin/courses/form.jsp")
                .forward(req, resp);
    }

    private void searchCourse(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String keyword = getTrimmedParameter(req, "keyword");

        List<Course> courseList =
                courseDAO.searchCourse(keyword);

        req.setAttribute("courseList", courseList);
        req.setAttribute("keyword", keyword);

        req.getRequestDispatcher("/admin/courses/list.jsp")
                .forward(req, resp);
    }

    private void addCourse(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String courseCode =
                getTrimmedParameter(req, "courseCode");

        String courseName =
                getTrimmedParameter(req, "courseName");

        int credits =
                parseInt(req.getParameter("credits"));

        Course course = new Course(
                0,
                courseCode,
                courseName,
                credits
        );

        String error = validateCourse(course);

        if (error != null) {
            req.setAttribute("course", course);
            req.setAttribute("formAction", "add");
            req.setAttribute("error", error);

            req.getRequestDispatcher("/admin/courses/form.jsp")
                    .forward(req, resp);

            return;
        }

        int result = courseDAO.createCourse(course);

        if (result > 0) {
            redirectToList(req, resp);
        } else {
            req.setAttribute("course", course);
            req.setAttribute("formAction", "add");
            req.setAttribute(
                    "error",
                    "Thêm môn học thất bại. Mã môn học có thể đã tồn tại."
            );

            req.getRequestDispatcher("/admin/courses/form.jsp")
                    .forward(req, resp);
        }
    }

    private void editCourse(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        int id = parseInt(req.getParameter("id"));

        String courseCode =
                getTrimmedParameter(req, "courseCode");

        String courseName =
                getTrimmedParameter(req, "courseName");

        int credits =
                parseInt(req.getParameter("credits"));

        Course course = new Course(
                id,
                courseCode,
                courseName,
                credits
        );

        if (id <= 0 || courseDAO.getCourseById(id) == null) {
            redirectToList(req, resp);
            return;
        }

        String error = validateCourse(course);

        if (error != null) {
            req.setAttribute("course", course);
            req.setAttribute("formAction", "edit");
            req.setAttribute("error", error);

            req.getRequestDispatcher("/admin/courses/form.jsp")
                    .forward(req, resp);

            return;
        }

        int result = courseDAO.editCourse(course);

        if (result > 0) {
            redirectToList(req, resp);
        } else {
            req.setAttribute("course", course);
            req.setAttribute("formAction", "edit");
            req.setAttribute(
                    "error",
                    "Cập nhật môn học thất bại."
            );

            req.getRequestDispatcher("/admin/courses/form.jsp")
                    .forward(req, resp);
        }
    }

    private void deleteCourse(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {

        int id = parseInt(req.getParameter("id"));

        if (id > 0) {
            courseDAO.deleteCourse(id);
        }

        redirectToList(req, resp);
    }

    private String validateCourse(Course course) {

        if (course.getCourseCode() == null
                || course.getCourseCode().trim().isEmpty()) {

            return "Mã môn học không được để trống.";
        }

        if (course.getCourseName() == null
                || course.getCourseName().trim().isEmpty()) {

            return "Tên môn học không được để trống.";
        }

        if (course.getCourseCode().length() > 20) {
            return "Mã môn học không được vượt quá 20 ký tự.";
        }

        if (course.getCourseName().length() > 100) {
            return "Tên môn học không được vượt quá 100 ký tự.";
        }

        if (course.getCredits() <= 0) {
            return "Số tín chỉ phải lớn hơn 0.";
        }

        return null;
    }

    private String getTrimmedParameter(
            HttpServletRequest req,
            String parameterName) {

        String value = req.getParameter(parameterName);

        if (value == null) {
            return "";
        }

        return value.trim();
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
                req.getContextPath() + "/course?action=list"
        );
    }
}