package Controller;

import DAO.StudentDAO;
import Models.Student;
import Models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "StudentClassServlet",
        urlPatterns = {"/StudentClassServlet"})
public class StudentClassServlet extends HttpServlet {

    private static final String CLASS_PAGE = "/student/class.jsp";
    private static final String LOGIN_PAGE = "/LoginServlet";

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

        StudentDAO studentDAO = new StudentDAO();

        Student student = studentDAO.getStudentByUserId(user.getId());

        if (student == null) {

            request.setAttribute(
                    "error",
                    "Không tìm thấy thông tin sinh viên."
            );

        } else {

            request.setAttribute(
                    "student",
                    student
            );
        }

        request.getRequestDispatcher(CLASS_PAGE)
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}