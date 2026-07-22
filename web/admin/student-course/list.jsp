<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Ghi danh | Student Manager"/>
</jsp:include>

<div class="d-flex flex-column flex-md-row
     justify-content-between align-items-md-center
     gap-2 mb-3">

    <div>
        <h2 class="page-title mb-0">
            Ghi danh (Enrollment)
        </h2>

        <p class="page-subtitle mb-0">
            Đăng ký sinh viên vào lớp học phần
        </p>
    </div>

    <a href="${pageContext.request.contextPath}/StudentCourseServlet?action=create"
       class="btn btn-teal">
        Thêm ghi danh
    </a>
</div>

<!-- Thông báo success từ session -->
<c:if test="${not empty sessionScope.success}">
    <div class="alert alert-success alert-dismissible fade show"
         role="alert">

        ${sessionScope.success}

        <button type="button"
                class="btn-close"
                data-bs-dismiss="alert">
        </button>
    </div>

    <c:remove var="success" scope="session"/>
</c:if>

<!-- Thông báo lỗi từ session -->
<c:if test="${not empty sessionScope.error}">
    <div class="alert alert-danger alert-dismissible fade show"
         role="alert">

        ${sessionScope.error}

        <button type="button"
                class="btn-close"
                data-bs-dismiss="alert">
        </button>
    </div>

    <c:remove var="error" scope="session"/>
</c:if>

<!-- Form tìm kiếm -->
<div class="card table-card mb-3">
    <div class="card-body">

        <form method="get"
              action="${pageContext.request.contextPath}/StudentCourseServlet"
              class="row g-2">

            <input type="hidden"
                   name="action"
                   value="list">

            <div class="col-md-9">
                <input type="text"
                       class="form-control"
                       name="keyword"
                       value="${keyword}"
                       placeholder="Tìm theo sinh viên, lớp, môn học, giáo viên hoặc học kỳ">
            </div>

            <div class="col-md-3 d-flex gap-2">

                <button type="submit"
                        class="btn btn-teal flex-grow-1">
                    Tìm kiếm
                </button>

                <a href="${pageContext.request.contextPath}/StudentCourseServlet?action=list"
                   class="btn btn-outline-secondary">
                    Làm mới
                </a>
            </div>
        </form>
    </div>
</div>

<!-- Bảng danh sách -->
<div class="card table-card">

    <div class="table-responsive">

        <table class="table table-hover align-middle mb-0">

            <thead class="table-light">
                <tr>
                    <th>#</th>
                    <th>Sinh viên</th>
                    <th>Lớp</th>
                    <th>Môn học</th>
                    <th>Giáo viên</th>
                    <th>Học kỳ</th>
                    <th class="text-end">Thao tác</th>
                </tr>
            </thead>

            <tbody>

                <c:choose>

                    <c:when test="${not empty studentCourseList}">

                        <c:forEach var="sc"
                                   items="${studentCourseList}"
                                   varStatus="status">

                            <tr>
                                <td>${status.index + 1}</td>

                                <td>
                                    <div class="fw-semibold">
                                        ${sc.studentName}
                                    </div>

                                    <div class="text-muted small">
                                        ${sc.studentCode}
                                    </div>
                                </td>

                                <td>
                                    <div class="fw-semibold">
                                        ${sc.classCode}
                                    </div>

                                    <div class="text-muted small">
                                        ${sc.className}
                                    </div>
                                </td>

                                <td>
                                    <div class="fw-semibold">
                                        ${sc.courseCode}
                                    </div>

                                    <div class="text-muted small">
                                        ${sc.courseName}
                                    </div>
                                </td>

                                <td>
                                    <div class="fw-semibold">
                                        ${sc.teacherName}
                                    </div>

                                    <div class="text-muted small">
                                        ${sc.teacherCode}
                                    </div>
                                </td>

                                <td>
                                    ${sc.semesterName}
                                    ${sc.schoolYear}
                                </td>

                                <td class="text-end">

                                    <form method="post"
                                          action="${pageContext.request.contextPath}/StudentCourseServlet"
                                          class="d-inline"
                                          onsubmit="return confirm('Bạn có chắc muốn hủy ghi danh này không?');">

                                        <input type="hidden"
                                               name="action"
                                               value="delete">

                                        <input type="hidden"
                                               name="id"
                                               value="${sc.id}">

                                        <button type="submit"
                                                class="btn btn-sm btn-outline-danger">
                                            Hủy
                                        </button>
                                    </form>

                                </td>
                            </tr>

                        </c:forEach>

                    </c:when>

                    <c:otherwise>

                        <tr>
                            <td colspan="7"
                                class="text-center text-muted py-4">
                                Chưa có dữ liệu ghi danh.
                            </td>
                        </tr>

                    </c:otherwise>

                </c:choose>

            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>