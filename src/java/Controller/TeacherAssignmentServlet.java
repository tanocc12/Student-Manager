package Controller;

import DAO.TeachingAssignmentDAO;
import Models.TeachingAssignment;
import Models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/TeacherAssignmentServlet")
public class TeacherAssignmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Chưa đăng nhập
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );
            return;
        }

        User user = (User) session.getAttribute("user");

        // Chỉ Teacher được vào
        if (!"Teacher".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(
                    request.getContextPath() + "/home.jsp"
            );
            return;
        }

        try {
            TeachingAssignmentDAO dao = new TeachingAssignmentDAO();

            /*
             * Lấy danh sách lớp mà giáo viên đang được phân công.
             *
             * Nếu bảng TeachingAssignments lưu TeacherId là User.Id
             * thì dùng user.getId().
             */
            List<TeachingAssignment> assignments
                    = dao.getTeachingAssignmentsByTeacherUserId(user.getId());

            request.setAttribute("assignments", assignments);

            request.getRequestDispatcher(
                    "/teacher/classes.jsp"
            ).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Không thể tải danh sách lớp được phân công."
            );

            request.getRequestDispatcher(
                    "/teacher/classes.jsp"
            ).forward(request, response);
        }
    }
}