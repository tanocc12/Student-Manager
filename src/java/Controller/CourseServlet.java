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

@WebServlet("/course")
public class CourseServlet extends HttpServlet {

    private CourseDAO courseDAO;

    @Override
    public void init() {
        courseDAO = new CourseDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null || action.isEmpty()) {
            action = "list";
        }

        switch (action) {

            case "add":
                showCourseForm(req, resp);
                break;

            case "edit":
                showCourseForm(req, resp);
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
    protected void doPost(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            resp.sendRedirect("course");
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
                resp.sendRedirect("course");
                break;
        }
    }

    private void listCourse(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        List<Course> courseList = courseDAO.getCourses();

        req.setAttribute("courseList", courseList);

        req.getRequestDispatcher("/admin/courses/list.jsp")
                .forward(req, resp);
    }

    private void showCourseForm(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        int id = parseInt(req.getParameter("id"));

        if (id > 0) {
            Course course = courseDAO.getCourseById(id);

            if (course == null) {
                resp.sendRedirect("course");
                return;
            }

            req.setAttribute("course", course);
        }

        req.getRequestDispatcher("/admin/courses/form.jsp")
                .forward(req, resp);
    }

    private void searchCourse(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String keyword = req.getParameter("keyword");

        if (keyword == null) {
            keyword = "";
        }

        keyword = keyword.trim();

        List<Course> courseList = courseDAO.searchCourse(keyword);

        req.setAttribute("courseList", courseList);
        req.setAttribute("keyword", keyword);

        req.getRequestDispatcher("/admin/courses/list.jsp")
                .forward(req, resp);
    }

    private void addCourse(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String courseCode = req.getParameter("courseCode");
        String courseName = req.getParameter("courseName");
        int credits = parseInt(req.getParameter("credits"));

        courseCode = courseCode == null ? "" : courseCode.trim();
        courseName = courseName == null ? "" : courseName.trim();

        if (courseCode.isEmpty()
                || courseName.isEmpty()
                || credits <= 0) {

            req.setAttribute("error", "Please fill in all required fields.");

            req.getRequestDispatcher("/admin/courses/form.jsp")
                    .forward(req, resp);
            return;
        }

        Course course = new Course(0, courseCode, courseName, credits);

        int result = courseDAO.createCourse(course);

        if (result > 0) {
            resp.sendRedirect("course");
        } else {
            req.setAttribute("error", "Add course failed.");
            req.getRequestDispatcher("/admin/courses/form.jsp")
                    .forward(req, resp);
        }
    }

    private void editCourse(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        int id = parseInt(req.getParameter("id"));

        String courseCode = req.getParameter("courseCode");
        String courseName = req.getParameter("courseName");
        int credits = parseInt(req.getParameter("credits"));

        courseCode = courseCode == null ? "" : courseCode.trim();
        courseName = courseName == null ? "" : courseName.trim();

        if (id <= 0
                || courseCode.isEmpty()
                || courseName.isEmpty()
                || credits <= 0) {

            resp.sendRedirect("course");
            return;
        }

        Course course = new Course(id, courseCode, courseName, credits);

        int result = courseDAO.editCourse(course);

        if (result > 0) {
            resp.sendRedirect("course");
        } else {

            req.setAttribute("course", course);
            req.setAttribute("error", "Update failed.");

            req.getRequestDispatcher("/admin/courses/form.jsp")
                    .forward(req, resp);
        }
    }

    private void deleteCourse(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        int id = parseInt(req.getParameter("id"));

        if (id > 0) {
            courseDAO.deleteCourse(id);
        }

        resp.sendRedirect("course");
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
