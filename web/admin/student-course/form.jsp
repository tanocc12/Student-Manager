<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Thêm ghi danh | Student Manager"/>
</jsp:include>

<div class="d-flex flex-column flex-md-row
     justify-content-between align-items-md-center
     gap-2 mb-3">

    <div>
        <h2 class="page-title mb-0">Thêm ghi danh</h2>
        <p class="page-subtitle mb-0">
            Đăng ký sinh viên vào một lớp học phần
        </p>
    </div>

    <a href="${pageContext.request.contextPath}/StudentCourseServlet?action=list"
       class="btn btn-outline-secondary">
        Quay lại
    </a>
</div>

<c:if test="${not empty error}">
    <div class="alert alert-danger alert-dismissible fade show"
         role="alert">

        ${error}

        <button type="button"
                class="btn-close"
                data-bs-dismiss="alert">
        </button>
    </div>
</c:if>

<div class="card table-card">
    <div class="card-body">

        <form method="post"
              action="${pageContext.request.contextPath}/StudentCourseServlet"
              class="row g-3">

            <input type="hidden"
                   name="action"
                   value="insert">

            <!-- Student -->
            <div class="col-md-6">

                <label for="studentId"
                       class="form-label">

                    Sinh viên
                    <span class="text-danger">*</span>
                </label>

                <select id="studentId"
                        name="studentId"
                        class="form-select"
                        required>

                    <option value="">
                        -- Chọn sinh viên --
                    </option>

                    <c:forEach var="student"
                               items="${studentList}">

                        <option value="${student.id}"
                            <c:if test="${selectedStudentId == student.id}">
                                selected
                            </c:if>>

                            ${student.studentCode}
                            - ${student.fullName}
                            - ${student.classCode}

                        </option>

                    </c:forEach>
                </select>

                <c:if test="${empty studentList}">
                    <div class="form-text text-danger">
                        Chưa có sinh viên trong hệ thống.
                    </div>
                </c:if>
            </div>

            <!-- Teaching Assignment -->
            <div class="col-md-6">

                <label for="teachingAssignmentId"
                       class="form-label">

                    Lớp học phần
                    <span class="text-danger">*</span>
                </label>

                <select id="teachingAssignmentId"
                        name="teachingAssignmentId"
                        class="form-select"
                        required>

                    <option value="">
                        -- Chọn lớp học phần --
                    </option>

                    <c:forEach var="ta"
                               items="${teachingAssignmentList}">

                        <option value="${ta.id}"
                            <c:if test="${selectedTeachingAssignmentId == ta.id}">
                                selected
                            </c:if>>

                            ${ta.classCode}
                            - ${ta.courseCode}
                            - ${ta.courseName}
                            - ${ta.teacherName}
                            - ${ta.semesterName} ${ta.schoolYear}

                        </option>

                    </c:forEach>
                </select>

                <c:if test="${empty teachingAssignmentList}">
                    <div class="form-text text-danger">
                        Chưa có phân công giảng dạy.
                    </div>
                </c:if>
            </div>

            <div class="col-12">
                <div class="alert alert-info mb-0">
                    Lớp, môn học, giáo viên và học kỳ sẽ được xác định
                    tự động từ phân công giảng dạy đã chọn.
                </div>
            </div>

            <div class="col-12 d-flex justify-content-end gap-2">

                <a href="${pageContext.request.contextPath}/StudentCourseServlet?action=list"
                   class="btn btn-outline-secondary">
                    Hủy
                </a>

                <button type="submit"
                        class="btn btn-teal"
                        <c:if test="${empty studentList
                                      or empty teachingAssignmentList}">
                            disabled
                        </c:if>>

                    Ghi danh
                </button>
            </div>

        </form>
    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>