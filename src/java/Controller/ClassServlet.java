package Controllers;

import DAO.ClassDAO;
import DAO.CourseDAO;
import DAO.MajorDAO;
import Models.ClassRoom;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ClassServlet", urlPatterns = {"/ClassServlet"})
public class ClassServlet extends HttpServlet {

    private ClassDAO classDAO;
    private MajorDAO majorDAO;
    private CourseDAO courseDAO;

    @Override
    public void init() throws ServletException {
        classDAO = new ClassDAO();
        majorDAO = new MajorDAO();
        courseDAO = new CourseDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            action = "list";
        }

        try {
            switch (action) {
                case "list":
                    showClassList(request, response);
                    break;

                case "create":
                    showCreateForm(request, response);
                    break;

                case "edit":
                    showEditForm(request, response);
                    break;

                case "delete":
                    deleteClass(request, response);
                    break;

                default:
                    response.sendRedirect(
                            request.getContextPath()
                            + "/ClassServlet?action=list"
                    );
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Đã xảy ra lỗi khi xử lý lớp học."
            );

            showClassList(request, response);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/ClassServlet?action=list"
            );
            return;
        }

        try {
            switch (action) {
                case "insert":
                    insertClass(request, response);
                    break;

                case "update":
                    updateClass(request, response);
                    break;

                default:
                    response.sendRedirect(
                            request.getContextPath()
                            + "/ClassServlet?action=list"
                    );
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Đã xảy ra lỗi khi lưu thông tin lớp học."
            );

            if ("update".equals(action)) {
                request.setAttribute("formAction", "update");
            } else {
                request.setAttribute("formAction", "insert");
            }

            loadFormData(request);

            request.getRequestDispatcher(
                    "/admin/classes/form.jsp"
            ).forward(request, response);
        }
    }

    /*
     * Hiển thị danh sách lớp.
     */
    private void showClassList(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<ClassRoom> classes = classDAO.getAllClasses();

        request.setAttribute("classes", classes);

        HttpSession session = request.getSession();

        String success = (String) session.getAttribute(
                "classSuccess"
        );

        String error = (String) session.getAttribute(
                "classError"
        );

        if (success != null) {
            request.setAttribute("success", success);
            session.removeAttribute("classSuccess");
        }

        if (error != null) {
            request.setAttribute("error", error);
            session.removeAttribute("classError");
        }

        request.getRequestDispatcher(
                "/admin/classes/list.jsp"
        ).forward(request, response);
    }

    /*
     * Hiển thị form thêm lớp.
     */
    private void showCreateForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("formAction", "insert");

        loadFormData(request);

        request.getRequestDispatcher(
                "/admin/classes/form.jsp"
        ).forward(request, response);
    }

    /*
     * Hiển thị form sửa lớp.
     */
    private void showEditForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Integer id = parsePositiveInt(
                request.getParameter("id")
        );

        if (id == null) {
            setSessionError(
                    request,
                    "ID lớp học không hợp lệ."
            );

            redirectToList(request, response);
            return;
        }

        ClassRoom classroom = classDAO.getClassById(id);

        if (classroom == null) {
            setSessionError(
                    request,
                    "Không tìm thấy lớp học."
            );

            redirectToList(request, response);
            return;
        }

        request.setAttribute("classroom", classroom);
        request.setAttribute("formAction", "update");

        loadFormData(request);

        request.getRequestDispatcher(
                "/admin/classes/form.jsp"
        ).forward(request, response);
    }

    /*
     * Thêm lớp học.
     */
    private void insertClass(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String classCode = getTrimmedParameter(
                request,
                "classCode"
        );

        String className = getTrimmedParameter(
                request,
                "className"
        );

        Integer majorId = parsePositiveInt(
                request.getParameter("majorId")
        );

        Integer courseId = parsePositiveInt(
                request.getParameter("courseId")
        );

        ClassRoom classroom = new ClassRoom();

        classroom.setClassCode(classCode);
        classroom.setClassName(className);

        if (majorId != null) {
            classroom.setMajorId(majorId);
        }

        if (courseId != null) {
            classroom.setCourseId(courseId);
        }

        String error = validateClass(
                classroom,
                false
        );

        if (error != null) {
            forwardFormError(
                    request,
                    response,
                    classroom,
                    "insert",
                    error
            );

            return;
        }

        boolean inserted = classDAO.insertClass(classroom);

        if (inserted) {
            setSessionSuccess(
                    request,
                    "Thêm lớp học thành công."
            );
        } else {
            setSessionError(
                    request,
                    "Không thể thêm lớp học."
            );
        }

        redirectToList(request, response);
    }

    /*
     * Cập nhật lớp học.
     */
    private void updateClass(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Integer id = parsePositiveInt(
                request.getParameter("id")
        );

        String classCode = getTrimmedParameter(
                request,
                "classCode"
        );

        String className = getTrimmedParameter(
                request,
                "className"
        );

        Integer majorId = parsePositiveInt(
                request.getParameter("majorId")
        );

        Integer courseId = parsePositiveInt(
                request.getParameter("courseId")
        );

        ClassRoom classroom = new ClassRoom();

        if (id != null) {
            classroom.setId(id);
        }

        classroom.setClassCode(classCode);
        classroom.setClassName(className);

        if (majorId != null) {
            classroom.setMajorId(majorId);
        }

        if (courseId != null) {
            classroom.setCourseId(courseId);
        }

        if (id == null || !classDAO.checkClassId(id)) {
            forwardFormError(
                    request,
                    response,
                    classroom,
                    "update",
                    "Lớp học không tồn tại."
            );

            return;
        }

        String error = validateClass(
                classroom,
                true
        );

        if (error != null) {
            forwardFormError(
                    request,
                    response,
                    classroom,
                    "update",
                    error
            );

            return;
        }

        boolean updated = classDAO.updateClass(classroom);

        if (updated) {
            setSessionSuccess(
                    request,
                    "Cập nhật lớp học thành công."
            );
        } else {
            setSessionError(
                    request,
                    "Không thể cập nhật lớp học."
            );
        }

        redirectToList(request, response);
    }


    private void deleteClass(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Integer id = parsePositiveInt(
                request.getParameter("id")
        );

        if (id == null) {
            setSessionError(
                    request,
                    "ID lớp học không hợp lệ."
            );

            redirectToList(request, response);
            return;
        }

        if (!classDAO.checkClassId(id)) {
            setSessionError(
                    request,
                    "Không tìm thấy lớp học."
            );

            redirectToList(request, response);
            return;
        }

        boolean deleted = classDAO.deleteClass(id);

        if (deleted) {
            setSessionSuccess(
                    request,
                    "Xóa lớp học thành công."
            );
        } else {
            setSessionError(
                    request,
                    "Không thể xóa lớp học. "
                    + "Lớp có thể đang được sử dụng bởi sinh viên "
                    + "hoặc phân công giảng dạy."
            );
        }

        redirectToList(request, response);
    }

    /*
     * Kiểm tra dữ liệu lớp học.
     */
    private String validateClass(
            ClassRoom classroom,
            boolean isUpdate) {

        String classCode = classroom.getClassCode();
        String className = classroom.getClassName();

        if (classCode == null || classCode.isEmpty()) {
            return "Mã lớp không được để trống.";
        }

        if (classCode.length() > 30) {
            return "Mã lớp không được vượt quá 30 ký tự.";
        }

        if (!classCode.matches("[A-Za-z0-9_-]+")) {
            return "Mã lớp chỉ được chứa chữ cái, chữ số, "
                    + "dấu gạch dưới hoặc dấu gạch ngang.";
        }

        if (className == null || className.isEmpty()) {
            return "Tên lớp không được để trống.";
        }

        if (className.length() > 100) {
            return "Tên lớp không được vượt quá 100 ký tự.";
        }

        if (classroom.getMajorId() <= 0) {
            return "Vui lòng chọn chuyên ngành.";
        }

        if (!majorDAO.checkMajorId(
                classroom.getMajorId())) {

            return "Chuyên ngành không tồn tại.";
        }

        if (classroom.getCourseId() <= 0) {
            return "Vui lòng chọn môn học.";
        }

        if (!courseDAO.checkCourseId(
                classroom.getCourseId())) {

            return "Môn học không tồn tại.";
        }

        if (isUpdate) {
            if (classDAO.checkClassCodeExceptId(
                    classCode,
                    classroom.getId())) {

                return "Mã lớp đã được sử dụng bởi lớp khác.";
            }
        } else {
            if (classDAO.checkClassCode(classCode)) {
                return "Mã lớp đã tồn tại.";
            }
        }

        return null;
    }

    /*
     * Gửi lại form khi validation thất bại.
     */
    private void forwardFormError(
            HttpServletRequest request,
            HttpServletResponse response,
            ClassRoom classroom,
            String formAction,
            String error)
            throws ServletException, IOException {

        request.setAttribute("classroom", classroom);
        request.setAttribute("formAction", formAction);
        request.setAttribute("error", error);

        loadFormData(request);

        request.getRequestDispatcher(
                "/admin/classes/form.jsp"
        ).forward(request, response);
    }

    /*
     * Load dữ liệu cho dropdown chuyên ngành và môn học.
     */
    private void loadFormData(HttpServletRequest request) {

        request.setAttribute(
                "majors",
                majorDAO.getAllMajors()
        );

        request.setAttribute(
                "courses",
                courseDAO.getCourses()
        );
    }

    /*
     * Lấy parameter String và loại bỏ khoảng trắng.
     */
    private String getTrimmedParameter(
            HttpServletRequest request,
            String parameterName) {

        String value = request.getParameter(
                parameterName
        );

        if (value == null) {
            return "";
        }

        return value.trim();
    }

    /*
     * Parse số nguyên dương.
     */
    private Integer parsePositiveInt(String value) {

        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            int number = Integer.parseInt(
                    value.trim()
            );

            if (number <= 0) {
                return null;
            }

            return number;

        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void setSessionSuccess(
            HttpServletRequest request,
            String message) {

        request.getSession().setAttribute(
                "classSuccess",
                message
        );
    }

    private void setSessionError(
            HttpServletRequest request,
            String message) {

        request.getSession().setAttribute(
                "classError",
                message
        );
    }

    private void redirectToList(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/ClassServlet?action=list"
        );
    }
}