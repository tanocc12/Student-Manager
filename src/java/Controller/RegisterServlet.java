package Controller;

import DAO.UserDAO;
import Models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;

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

        String username = request.getParameter("username");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String role = request.getParameter("role");

        UserDAO dao = new UserDAO();

        if (!password.equals(confirmPassword)) {

            request.setAttribute("error", "Confirm password does not match.");
            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);
            return;
        }

        if (dao.checkUsername(username)) {

            request.setAttribute("error", "Username already exists.");
            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);
            return;
        }

        if (dao.checkEmail(email)) {

            request.setAttribute("error", "Email already exists.");
            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);
            return;
        }

        User user = new User();

        user.setUsername(username);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setGender(gender);
        user.setPhone(phone);
        user.setPassword(password);
        user.setRole(role);

        if (dob != null && !dob.isEmpty()) {
            user.setDob(Date.valueOf(dob));
        }

        if (dao.register(user)) {

            response.sendRedirect(request.getContextPath() + "/login.jsp");
            
        } else {

            request.setAttribute("error", "Register failed.");

            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);
        }
    }
}