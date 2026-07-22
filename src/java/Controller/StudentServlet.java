package Controller;

import DAO.ClassDAO;
import DAO.MajorDAO;
import DAO.StudentDAO;
import DAO.UserDAO;
import Models.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "StudentServlet",
        urlPatterns = {"/StudentServlet"}
)
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
                redirectToList(request, response);
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
            redirectToList(request, response);
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
                redirectToList(request, response);
                break;
        }
    }

    private void listStudents(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        StudentDAO studentDAO = new StudentDAO();

        List<Student> students
                = studentDAO.getAllStudents();

        request.setAttribute("students", students);

        loadFlashMessage(request);

        request.getRequestDispatcher(LIST_PAGE)
                .forward(request, response);
    }

    private void searchStudents(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String keyword
                = getTrimmedParameter(request, "keyword");

        StudentDAO studentDAO = new StudentDAO();

        List<Student> students
                = studentDAO.searchStudents(keyword);

        request.setAttribute("students", students);
        request.setAttribute("keyword", keyword);

        loadFlashMessage(request);

        request.getRequestDispatcher(LIST_PAGE)
                .forward(request, response);
    }

    private void showCreateForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        loadCreateFormData(request);

        request.setAttribute("formAction", "insert");

        request.getRequestDispatcher(FORM_PAGE)
                .forward(request, response);
    }

    private void showEditForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String idRaw
                = getTrimmedParameter(request, "id");

        try {
            int id = Integer.parseInt(idRaw);

            if (id <= 0) {
                throw new NumberFormatException();
            }

            StudentDAO studentDAO = new StudentDAO();

            Student student
                    = studentDAO.getStudentById(id);

            if (student == null) {
                setFlashError(
                        request,
                        "Không tìm thấy học sinh cần sửa."
                );

                redirectToList(request, response);
                return;
            }

            request.setAttribute("student", student);

            loadUpdateFormData(request);

            request.setAttribute(
                    "formAction",
                    "update"
            );

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

    private void insertStudent(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Student student;

        try {
            student = getStudentFromRequest(
                    request,
                    false
            );

        } catch (IllegalArgumentException e) {

            forwardCreateError(
                    request,
                    response,
                    null,
                    e.getMessage()
            );
            return;
        }

        StudentDAO studentDAO = new StudentDAO();

        if (studentDAO.checkStudentCode(
                student.getStudentCode())) {

            forwardCreateError(
                    request,
                    response,
                    student,
                    "Mã học sinh đã tồn tại."
            );
            return;
        }

        if (!studentDAO.checkAvailableStudentUser(
                student.getUserId())) {

            forwardCreateError(
                    request,
                    response,
                    student,
                    "Tài khoản không hợp lệ hoặc đã có hồ sơ học sinh."
            );
            return;
        }

        MajorDAO majorDAO = new MajorDAO();

        if (!majorDAO.checkMajorId(
                student.getMajorId())) {

            forwardCreateError(
                    request,
                    response,
                    student,
                    "Chuyên ngành không tồn tại."
            );
            return;
        }

        ClassDAO classDAO = new ClassDAO();

        if (!classDAO.checkClassId(
                student.getClassId())) {

            forwardCreateError(
                    request,
                    response,
                    student,
                    "Lớp học không tồn tại."
            );
            return;
        }

        if (!classDAO.checkClassBelongsToMajor(
                student.getClassId(),
                student.getMajorId())) {

            forwardCreateError(
                    request,
                    response,
                    student,
                    "Lớp học không thuộc chuyên ngành đã chọn."
            );
            return;
        }

        boolean inserted
                = studentDAO.insertStudent(student);

        if (inserted) {

            setFlashSuccess(
                    request,
                    "Thêm hồ sơ học sinh thành công."
            );

            redirectToList(request, response);

        } else {

            forwardCreateError(
                    request,
                    response,
                    student,
                    "Thêm hồ sơ học sinh thất bại."
            );
        }
    }

    private void updateStudent(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Student student;

        try {
            student = getStudentFromRequest(
                    request,
                    true
            );

        } catch (IllegalArgumentException e) {

            forwardUpdateError(
                    request,
                    response,
                    null,
                    e.getMessage()
            );
            return;
        }

        StudentDAO studentDAO = new StudentDAO();

        Student oldStudent
                = studentDAO.getStudentById(
                        student.getId()
                );

        if (oldStudent == null) {

            setFlashError(
                    request,
                    "Không tìm thấy học sinh cần cập nhật."
            );

            redirectToList(request, response);
            return;
        }

        student.setUserId(
                oldStudent.getUserId()
        );

        student.setUsername(
                oldStudent.getUsername()
        );

        student.setFullName(
                oldStudent.getFullName()
        );

        student.setEmail(
                oldStudent.getEmail()
        );

        student.setGender(
                oldStudent.getGender()
        );

        student.setDob(
                oldStudent.getDob()
        );

        student.setPhone(
                oldStudent.getPhone()
        );
        
        if (studentDAO.checkStudentCodeExceptId(
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

        MajorDAO majorDAO = new MajorDAO();

        if (!majorDAO.checkMajorId(
                student.getMajorId())) {

            forwardUpdateError(
                    request,
                    response,
                    student,
                    "Chuyên ngành không tồn tại."
            );
            return;
        }

        ClassDAO classDAO = new ClassDAO();

        if (!classDAO.checkClassId(
                student.getClassId())) {

            forwardUpdateError(
                    request,
                    response,
                    student,
                    "Lớp học không tồn tại."
            );
            return;
        }

        if (!classDAO.checkClassBelongsToMajor(
                student.getClassId(),
                student.getMajorId())) {

            forwardUpdateError(
                    request,
                    response,
                    student,
                    "Lớp học không thuộc chuyên ngành đã chọn."
            );
            return;
        }

        boolean updated
                = studentDAO.updateStudent(student);

        if (!updated) {

            forwardUpdateError(
                    request,
                    response,
                    student,
                    "Cập nhật học sinh thất bại."
            );
            return;
        }

        setFlashSuccess(
                request,
                "Cập nhật học sinh thành công."
        );

        redirectToList(request, response);
    }

    private void deleteStudent(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idRaw
                = getTrimmedParameter(request, "id");

        try {
            int id = Integer.parseInt(idRaw);

            if (id <= 0) {
                throw new NumberFormatException();
            }

            StudentDAO studentDAO
                    = new StudentDAO();

            boolean deleted
                    = studentDAO.deleteStudent(id);

            if (deleted) {

                setFlashSuccess(
                        request,
                        "Xóa hồ sơ học sinh thành công."
                );

            } else {

                setFlashError(
                        request,
                        "Không thể xóa hồ sơ học sinh."
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

    private Student getStudentFromRequest(
            HttpServletRequest request,
            boolean isUpdate) {

        Student student = new Student();

        /*
         * Update cần Students.Id.
         */
        if (isUpdate) {

            String idRaw
                    = getTrimmedParameter(
                            request,
                            "id"
                    );

            try {
                int id = Integer.parseInt(idRaw);

                if (id <= 0) {
                    throw new NumberFormatException();
                }

                student.setId(id);

            } catch (NumberFormatException e) {

                throw new IllegalArgumentException(
                        "ID học sinh không hợp lệ."
                );
            }
        }

        if (!isUpdate) {

            String userIdRaw
                    = getTrimmedParameter(
                            request,
                            "userId"
                    );

            try {
                int userId
                        = Integer.parseInt(userIdRaw);

                if (userId <= 0) {
                    throw new NumberFormatException();
                }

                student.setUserId(userId);

            } catch (NumberFormatException e) {

                throw new IllegalArgumentException(
                        "Tài khoản sinh viên không hợp lệ."
                );
            }
        }

        String studentCode
                = getTrimmedParameter(
                        request,
                        "studentCode"
                );

        String classIdRaw
                = getTrimmedParameter(
                        request,
                        "classId"
                );

        String majorIdRaw
                = getTrimmedParameter(
                        request,
                        "majorId"
                );

        String address
                = getTrimmedParameter(
                        request,
                        "address"
                );

        String status
                = getTrimmedParameter(
                        request,
                        "status"
                );

        if (studentCode.isEmpty()) {

            throw new IllegalArgumentException(
                    "Mã học sinh không được để trống."
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

        if (!status.equals("Studying")
                && !status.equals("Graduated")
                && !status.equals("Dropped")) {

            throw new IllegalArgumentException(
                    "Trạng thái học sinh không hợp lệ."
            );
        }

        student.setStudentCode(studentCode);
        student.setClassId(classId);
        student.setMajorId(majorId);

        student.setAddress(
                address.isEmpty()
                        ? null
                        : address
        );

        student.setStatus(status);

        return student;
    }

    private void forwardCreateError(
            HttpServletRequest request,
            HttpServletResponse response,
            Student student,
            String message)
            throws ServletException, IOException {

        request.setAttribute("student", student);
        request.setAttribute("error", message);
        request.setAttribute("formAction", "insert");

        loadCreateFormData(request);

        request.getRequestDispatcher(FORM_PAGE)
                .forward(request, response);
    }

    private void forwardUpdateError(
            HttpServletRequest request,
            HttpServletResponse response,
            Student student,
            String message)
            throws ServletException, IOException {

        request.setAttribute("student", student);
        request.setAttribute("error", message);
        request.setAttribute("formAction", "update");

        loadUpdateFormData(request);

        request.getRequestDispatcher(FORM_PAGE)
                .forward(request, response);
    }

    private void loadCreateFormData(
            HttpServletRequest request) {

        ClassDAO classDAO = new ClassDAO();
        MajorDAO majorDAO = new MajorDAO();
        UserDAO userDAO = new UserDAO();

        request.setAttribute(
                "classes",
                classDAO.getAllClasses()
        );

        request.setAttribute(
                "majors",
                majorDAO.getAllMajors()
        );

        request.setAttribute(
                "availableUsers",
                userDAO.getAvailableStudentUsers()
        );
    }

    private void loadUpdateFormData(
            HttpServletRequest request) {

        ClassDAO classDAO = new ClassDAO();
        MajorDAO majorDAO = new MajorDAO();

        request.setAttribute(
                "classes",
                classDAO.getAllClasses()
        );

        request.setAttribute(
                "majors",
                majorDAO.getAllMajors()
        );
    }

    private String getTrimmedParameter(
            HttpServletRequest request,
            String parameterName) {

        String value
                = request.getParameter(parameterName);

        return value == null
                ? ""
                : value.trim();
    }

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

    private void loadFlashMessage(
            HttpServletRequest request) {

        HttpSession session
                = request.getSession(false);

        if (session == null) {
            return;
        }

        Object success
                = session.getAttribute(
                        "flashSuccess"
                );

        Object error
                = session.getAttribute(
                        "flashError"
                );

        if (success != null) {

            request.setAttribute(
                    "success",
                    success.toString()
            );

            session.removeAttribute(
                    "flashSuccess"
            );
        }

        if (error != null) {

            request.setAttribute(
                    "error",
                    error.toString()
            );

            session.removeAttribute(
                    "flashError"
            );
        }
    }

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