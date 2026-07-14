package Filter;

import Models.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String context = req.getContextPath();

        // Cho phép truy cập tài nguyên công khai
        if (uri.endsWith("login.jsp")
                || uri.endsWith("register.jsp")
                || uri.contains("LoginServlet")
                || uri.contains("RegisterServlet")
                || uri.contains("LogoutServlet")
                || uri.contains("/assets/")
                || uri.endsWith("home.jsp")
                || uri.equals(context + "/")
                || uri.endsWith(".css")
                || uri.endsWith(".js")
                || uri.endsWith(".png")
                || uri.endsWith(".jpg")) {

            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {

            res.sendRedirect(context + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        String role = user.getRole();

        // ADMIN
        if (uri.contains("/admin/")
                && !role.equalsIgnoreCase("Admin")) {

            res.sendRedirect(context + "/home.jsp");
            return;
        }

        // TEACHER
        if (uri.contains("/teacher/")
                && !role.equalsIgnoreCase("Teacher")) {

            res.sendRedirect(context + "/home.jsp");
            return;
        }

        // STUDENT
        if (uri.contains("/student/")
                && !role.equalsIgnoreCase("Student")) {

            res.sendRedirect(context + "/home.jsp");
            return;
        }

        chain.doFilter(request, response);
    }
}