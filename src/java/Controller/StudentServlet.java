package Controller;

import DAO.ClassDAO;
import DAO.StudentDAO;
import Models.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import DAO.MajorDAO;

@WebServlet(name = "StudentServlet", urlPatterns = {"/StudentServlet"})
public class StudentServlet extends HttpServlet {

    private static final String LIST_PAGE
            = "/admin/students/list.jsp";

    private static final String FORM_PAGE
            = "/admin/students/form.jsp";

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

        switch (action) {
            case "list":
                listStudents(request, response);
                break;

            case "search":
                searchStudents(request, response);
                break;

            case "create":
                showCreateForm(request, response);
                break;

            case "edit":
                showEditForm(request, response);
                break;

            default:
                response.sendRedirect(
                        request.getContextPath()
                        + "/StudentServlet?action=list"
                );
                break;
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
                    + "/StudentServlet?action=list"
            );
            return;
        }

        switch (action) {
            case "insert":
                insertStudent(request, response);
                break;

            case "update":
                updateStudent(request, response);
                break;

            case "delete":
                deleteStudent(request, response);
                break;

            default:
                response.sendRedirect(
                        request.getContextPath()
                        + "/StudentServlet?action=list"
                );
                break;
        }
    }

    /*
     * Hiển thị toàn bộ danh sách Student.
     */
    private void listStudents(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        StudentDAO dao = new StudentDAO();
        List<Student> students = dao.getAllStudents();

        request.setAttribute("students", students);

        loadFlashMessage(request);

        request.getRequestDispatcher(LIST_PAGE)
                .forward(request, response);
    }

    /*
     * Tìm kiếm theo mã, tên, email, username hoặc classCode.
     */
    private void searchStudents(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");

        if (keyword == null) {
            keyword = "";
        }

        StudentDAO dao = new StudentDAO();
        List<Student> students
                = dao.searchStudents(keyword.trim());

        request.setAttribute("students", students);
        request.setAttribute("keyword", keyword.trim());

        loadFlashMessage(request);

        request.getRequestDispatcher(LIST_PAGE)
                .forward(request, response);
    }

    /*
     * Mở form thêm Student.
     */
    private void showCreateForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        ClassDAO classDAO = new ClassDAO();
        MajorDAO majorDAO = new MajorDAO();

        request.setAttribute("classes", classDAO.getAllClasses());
        request.setAttribute("majors", majorDAO.getAllMajors());

        request.setAttribute("formAction", "insert");

        request.getRequestDispatcher(FORM_PAGE)
                .forward(request, response);
    }

    /*
     * Mở form sửa Student.
     */
    private void showEditForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String idRaw = request.getParameter("id");

        try {
            int id = Integer.parseInt(idRaw);

            StudentDAO dao = new StudentDAO();
            Student student = dao.getStudentById(id);

            if (student == null) {
                setFlashError(
                        request,
                        "Không tìm thấy học sinh cần sửa."
                );

                redirectToList(request, response);
                return;
            }

            request.setAttribute("student", student);
            ClassDAO classDAO = new ClassDAO();
            MajorDAO majorDAO = new MajorDAO();

            request.setAttribute("classes", classDAO.getAllClasses());
            request.setAttribute("majors", majorDAO.getAllMajors());
            request.setAttribute("formAction", "update");

            request.getRequestDispatcher(FORM_PAGE)
                    .forward(request, response);

        } catch (NumberFormatException e) {
            setFlashError(
                    request,
                    "ID học sinh không hợp lệ."
            );

            redirectToList(request, response);
        }
    }

    /*
     * Thêm Student mới.
     *
     * StudentDAO sẽ:
     * 1. INSERT Users
     * 2. Lấy UserId
     * 3. INSERT Students
     */
    private void insertStudent(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Student student;

        try {
            student = getStudentFromRequest(request, false);

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("formAction", "insert");

            request.getRequestDispatcher(FORM_PAGE)
                    .forward(request, response);
            return;
        }

        String password = getTrimmedParameter(
                request,
                "password"
        );

        String confirmPassword = getTrimmedParameter(
                request,
                "confirmPassword"
        );

        if (password.isEmpty()) {
            forwardCreateError(
                    request,
                    response,
                    student,
                    "Mật khẩu không được để trống."
            );
            return;
        }

        if (password.length() < 6) {
            forwardCreateError(
                    request,
                    response,
                    student,
                    "Mật khẩu phải có ít nhất 6 ký tự."
            );
            return;
        }

        if (!password.equals(confirmPassword)) {
            forwardCreateError(
                    request,
                    response,
                    student,
                    "Xác nhận mật khẩu không khớp."
            );
            return;
        }

        StudentDAO dao = new StudentDAO();

        if (dao.checkStudentCode(student.getStudentCode())) {
            forwardCreateError(
                    request,
                    response,
                    student,
                    "Mã học sinh đã tồn tại."
            );
            return;
        }

        if (dao.checkUsername(student.getUsername())) {
            forwardCreateError(
                    request,
                    response,
                    student,
                    "Tên đăng nhập đã tồn tại."
            );
            return;
        }

        if (dao.checkEmail(student.getEmail())) {
            forwardCreateError(
                    request,
                    response,
                    student,
                    "Email đã tồn tại."
            );
            return;
        }

        MajorDAO majorDAO = new MajorDAO();

        if (!majorDAO.checkMajorId(student.getMajorId())) {
            forwardCreateError(
                    request,
                    response,
                    student,
                    "Chuyên ngành không tồn tại."
            );
            return;
        }

        boolean inserted = dao.insertStudent(
                student,
                password
        );

        if (inserted) {
            setFlashSuccess(
                    request,
                    "Thêm học sinh thành công."
            );

            redirectToList(request, response);

        } else {
            forwardCreateError(
                    request,
                    response,
                    student,
                    "Thêm học sinh thất bại. "
                    + "Vui lòng kiểm tra lại dữ liệu."
            );
        }
    }

    /*
     * Cập nhật Student.
     */
    private void updateStudent(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Student student;

        try {
            student = getStudentFromRequest(request, true);

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("formAction", "update");

            request.getRequestDispatcher(FORM_PAGE)
                    .forward(request, response);
            return;
        }

        StudentDAO dao = new StudentDAO();

        Student oldStudent
                = dao.getStudentById(student.getId());

        if (oldStudent == null) {
            setFlashError(
                    request,
                    "Không tìm thấy học sinh cần cập nhật."
            );

            redirectToList(request, response);
            return;
        }

        /*
         * UserId không nên lấy trực tiếp từ form vì có thể bị sửa.
         * Lấy UserId thật từ database.
         */
        student.setUserId(oldStudent.getUserId());

        if (dao.checkStudentCodeExceptId(
                student.getStudentCode(),
                student.getId())) {

            forwardUpdateError(
                    request,
                    response,
                    student,
                    "Mã học sinh đã tồn tại."
            );
            return;
        }

        if (dao.checkUsernameExceptUserId(
                student.getUsername(),
                student.getUserId())) {

            forwardUpdateError(
                    request,
                    response,
                    student,
                    "Tên đăng nhập đã tồn tại."
            );
            return;
        }

        if (dao.checkEmailExceptUserId(
                student.getEmail(),
                student.getUserId())) {

            forwardUpdateError(
                    request,
                    response,
                    student,
                    "Email đã tồn tại."
            );
            return;
        }

        MajorDAO majorDAO = new MajorDAO();

        if (!majorDAO.checkMajorId(student.getMajorId())) {
            forwardUpdateError(
                    request,
                    response,
                    student,
                    "Chuyên ngành không tồn tại."
            );
            return;
        }

        boolean updated = dao.updateStudent(student);

        if (!updated) {
            forwardUpdateError(
                    request,
                    response,
                    student,
                    "Cập nhật học sinh thất bại."
            );
            return;
        }

        /*
         * Password là tùy chọn khi update.
         * Để trống thì giữ mật khẩu cũ.
         */
        String newPassword = getTrimmedParameter(
                request,
                "password"
        );

        String confirmPassword = getTrimmedParameter(
                request,
                "confirmPassword"
        );

        if (!newPassword.isEmpty()) {

            if (newPassword.length() < 6) {
                /*
                 * Thông tin đã update trước đó.
                 * Để transaction tuyệt đối thì nên đưa update password
                 * vào chung StudentDAO.updateStudent.
                 *
                 * Bản hiện tại coi password là bước riêng.
                 */
                setFlashError(
                        request,
                        "Thông tin đã cập nhật, nhưng mật khẩu "
                        + "phải có ít nhất 6 ký tự."
                );

                redirectToList(request, response);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                setFlashError(
                        request,
                        "Thông tin đã cập nhật, nhưng xác nhận "
                        + "mật khẩu không khớp."
                );

                redirectToList(request, response);
                return;
            }

            boolean passwordUpdated
                    = dao.updateStudentPassword(
                            student.getUserId(),
                            newPassword
                    );

            if (!passwordUpdated) {
                setFlashError(
                        request,
                        "Thông tin đã cập nhật, nhưng không thể "
                        + "cập nhật mật khẩu."
                );

                redirectToList(request, response);
                return;
            }
        }

        setFlashSuccess(
                request,
                "Cập nhật học sinh thành công."
        );

        redirectToList(request, response);
    }

    /*
     * Xóa Student.
     */
    private void deleteStudent(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idRaw = request.getParameter("id");

        try {
            int id = Integer.parseInt(idRaw);

            StudentDAO dao = new StudentDAO();
            boolean deleted = dao.deleteStudent(id);

            if (deleted) {
                setFlashSuccess(
                        request,
                        "Xóa học sinh thành công."
                );
            } else {
                setFlashError(
                        request,
                        "Không thể xóa học sinh."
                );
            }

        } catch (NumberFormatException e) {
            setFlashError(
                    request,
                    "ID học sinh không hợp lệ."
            );
        }

        redirectToList(request, response);
    }

    /*
     * Đọc dữ liệu từ form và tạo Student.
     *
     * isUpdate = false:
     * - Không cần đọc id.
     *
     * isUpdate = true:
     * - Bắt buộc phải có id.
     */
    private Student getStudentFromRequest(
            HttpServletRequest request,
            boolean isUpdate) {

        Student student = new Student();

        if (isUpdate) {
            String idRaw = getTrimmedParameter(
                    request,
                    "id"
            );

            try {
                student.setId(Integer.parseInt(idRaw));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "ID học sinh không hợp lệ."
                );
            }
        }

        String studentCode = getTrimmedParameter(
                request,
                "studentCode"
        );

        String username = getTrimmedParameter(
                request,
                "username"
        );

        String fullName = getTrimmedParameter(
                request,
                "fullName"
        );

        String email = getTrimmedParameter(
                request,
                "email"
        );

        String gender = getTrimmedParameter(
                request,
                "gender"
        );

        String dobRaw = getTrimmedParameter(
                request,
                "dob"
        );

        String phone = getTrimmedParameter(
                request,
                "phone"
        );

        String classIdRaw = getTrimmedParameter(
                request,
                "classId"
        );

        String majorIdRaw = getTrimmedParameter(
                request,
                "majorId"
        );

        String address = getTrimmedParameter(
                request,
                "address"
        );

        String status = getTrimmedParameter(
                request,
                "status"
        );

        if (studentCode.isEmpty()) {
            throw new IllegalArgumentException(
                    "Mã học sinh không được để trống."
            );
        }

        if (username.isEmpty()) {
            throw new IllegalArgumentException(
                    "Tên đăng nhập không được để trống."
            );
        }

        if (fullName.isEmpty()) {
            throw new IllegalArgumentException(
                    "Họ tên không được để trống."
            );
        }

        if (email.isEmpty()) {
            throw new IllegalArgumentException(
                    "Email không được để trống."
            );
        }

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new IllegalArgumentException(
                    "Email không đúng định dạng."
            );
        }

        if (!gender.isEmpty()
                && !gender.equals("Male")
                && !gender.equals("Female")) {

            throw new IllegalArgumentException(
                    "Giới tính phải là Male hoặc Female."
            );
        }

        if (!status.equals("Studying")
                && !status.equals("Graduated")
                && !status.equals("Dropped")) {

            throw new IllegalArgumentException(
                    "Trạng thái học sinh không hợp lệ."
            );
        }

        int classId;

        try {
            classId = Integer.parseInt(classIdRaw);

            if (classId <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Lớp học không hợp lệ."
            );
        }

        int majorId;

        try {
            majorId = Integer.parseInt(majorIdRaw);

            if (majorId <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Chuyên ngành không hợp lệ."
            );
        }

        Date dob = null;

        if (!dobRaw.isEmpty()) {
            try {
                dob = Date.valueOf(dobRaw);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        "Ngày sinh không hợp lệ."
                );
            }
        }

        student.setStudentCode(studentCode);
        student.setUsername(username);
        student.setFullName(fullName);
        student.setEmail(email);
        student.setGender(
                gender.isEmpty() ? null : gender
        );
        student.setDob(dob);
        student.setPhone(
                phone.isEmpty() ? null : phone
        );
        student.setClassId(classId);
        student.setMajorId(majorId);
        student.setAddress(
                address.isEmpty() ? null : address
        );
        student.setStatus(status);

        return student;
    }

    /*
     * Forward lại form create khi lỗi.
     */
    private void forwardCreateError(
            HttpServletRequest request,
            HttpServletResponse response,
            Student student,
            String message)
            throws ServletException, IOException {

        request.setAttribute("student", student);
        request.setAttribute("error", message);
        ClassDAO classDAO = new ClassDAO();
        MajorDAO majorDAO = new MajorDAO();

        request.setAttribute("classes", classDAO.getAllClasses());
        request.setAttribute("majors", majorDAO.getAllMajors());
        request.setAttribute("formAction", "insert");

        request.getRequestDispatcher(FORM_PAGE)
                .forward(request, response);
    }

    /*
     * Forward lại form update khi lỗi.
     */
    private void forwardUpdateError(
            HttpServletRequest request,
            HttpServletResponse response,
            Student student,
            String message)
            throws ServletException, IOException {

        request.setAttribute("student", student);
        request.setAttribute("error", message);

        ClassDAO classDAO = new ClassDAO();
        MajorDAO majorDAO = new MajorDAO();

        request.setAttribute("classes", classDAO.getAllClasses());
        request.setAttribute("majors", majorDAO.getAllMajors());

        request.setAttribute("formAction", "update");

        request.getRequestDispatcher(FORM_PAGE)
                .forward(request, response);
    }

    /*
     * Đọc parameter và loại bỏ khoảng trắng hai đầu.
     */
    private String getTrimmedParameter(
            HttpServletRequest request,
            String parameterName) {

        String value = request.getParameter(parameterName);

        return value == null ? "" : value.trim();
    }

    /*
     * Flash message dùng session.
     */
    private void setFlashSuccess(
            HttpServletRequest request,
            String message) {

        request.getSession().setAttribute(
                "flashSuccess",
                message
        );
    }

    private void setFlashError(
            HttpServletRequest request,
            String message) {

        request.getSession().setAttribute(
                "flashError",
                message
        );
    }

    /*
     * Chuyển flash message từ session sang request,
     * sau đó xóa khỏi session.
     */
    private void loadFlashMessage(
            HttpServletRequest request) {

        HttpSession session
                = request.getSession(false);

        if (session == null) {
            return;
        }

        Object success
                = session.getAttribute("flashSuccess");

        Object error
                = session.getAttribute("flashError");

        if (success != null) {
            request.setAttribute(
                    "success",
                    success.toString()
            );

            session.removeAttribute("flashSuccess");
        }

        if (error != null) {
            request.setAttribute(
                    "error",
                    error.toString()
            );

            session.removeAttribute("flashError");
        }
    }

    /*
     * Redirect về danh sách.
     */
    private void redirectToList(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/StudentServlet?action=list"
        );
    }
}
