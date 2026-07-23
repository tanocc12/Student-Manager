package Controller;

import DAO.GradeDAO;
import Models.Grade;
import Models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "StudentGradeServlet",
        urlPatterns = {"/StudentGradeServlet"})
public class StudentGradeServlet extends HttpServlet {

    private static final String GRADE_PAGE
            = "/student/grades.jsp";

    private static final String LOGIN_PAGE
            = "/LoginServlet";

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            return;
        }

        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            return;
        }

        if (!"Student".equalsIgnoreCase(user.getRole())) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Access denied."
            );

            return;
        }

        GradeDAO gradeDAO = new GradeDAO();

        List<Grade> myGrades
                = gradeDAO.getGradesByStudentUserId(
                        user.getId()
                );

        request.setAttribute(
                "myGrades",
                myGrades
        );

        request.getRequestDispatcher(GRADE_PAGE)
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}