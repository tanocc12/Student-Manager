package Controller;

import DAO.UserDAO;
import Models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("register.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String role = request.getParameter("role");

        UserDAO dao = new UserDAO();

        if (!password.equals(confirmPassword)) {

            request.setAttribute("error",
                    "Confirm password does not match.");

            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);
            return;
        }

        if (dao.checkEmail(email)) {

            request.setAttribute("error",
                    "Email already exists.");

            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);
            return;
        }

        User user = new User();

        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        if (dao.register(user)) {

            response.sendRedirect(request.getContextPath() + "/login");

        } else {

            request.setAttribute("error",
                    "Register failed.");

            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);
        }
    }
}