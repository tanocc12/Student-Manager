package Controller;

import DAO.UserDAO;
import Models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();

        User user = dao.login(username, password);

        if (user == null) {

            request.setAttribute("error",
                    "Username hoặc Password không đúng.");

            request.getRequestDispatcher("login.jsp")
                    .forward(request, response);

            return;
        }

        HttpSession session = request.getSession();

        session.setAttribute("user", user);

        switch (user.getRole()) {

            case "Admin":
                response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
                break;

            case "Teacher":
                response.sendRedirect(request.getContextPath() + "/teacher/dashboard.jsp");
                break;

            case "Student":
                response.sendRedirect(request.getContextPath() + "/student/dashboard.jsp");
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/home.jsp");
                break;
        }
    }
}