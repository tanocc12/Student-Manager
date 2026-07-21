<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<jsp:include page="/layout/header.jsp">
    <jsp:param
        name="pageTitle"
        value="Quản lý lớp học | Student Manager"/>
</jsp:include>

<div class="d-flex justify-content-between align-items-center mb-3">

    <div>
        <h2 class="page-title mb-0">
            Quản lý lớp học
        </h2>

        <p class="page-subtitle mb-0">
            Danh sách lớp học
        </p>
    </div>

    <a
        href="${pageContext.request.contextPath}/ClassServlet?action=create"
        class="btn btn-teal">

        + Thêm lớp

    </a>

</div>

<c:if test="${not empty success}">
    <div class="alert alert-success">
        ${success}
    </div>
</c:if>

<c:if test="${not empty error}">
    <div class="alert alert-danger">
        ${error}
    </div>
</c:if>

<div class="card table-card">

    <div class="table-responsive">

        <table class="table table-hover align-middle">

            <thead class="table-light">

                <tr>

                    <th>Mã lớp</th>

                    <th>Tên lớp</th>

                    <th>Chuyên ngành</th>

                    <th>Môn học</th>

                    <th class="text-center">
                        Thao tác
                    </th>

                </tr>

            </thead>

            <tbody>

                <c:choose>

                    <c:when test="${not empty classes}">

                        <c:forEach
                            var="cl"
                            items="${classes}">

                            <tr>

                                <td>
                                    ${cl.classCode}
                                </td>

                                <td>
                                    ${cl.className}
                                </td>

                                <td>
                                    ${cl.majorName}
                                </td>

                                <td>
                                    ${cl.courseName}
                                </td>

                                <td class="text-center">

                                    <a
                                        href="${pageContext.request.contextPath}/ClassServlet?action=edit&id=${cl.id}"
                                        class="btn btn-sm btn-outline-primary">

                                        Sửa

                                    </a>

                                    <a
                                        href="${pageContext.request.contextPath}/ClassServlet?action=delete&id=${cl.classId}"
                                        class="btn btn-sm btn-outline-danger"
                                        onclick="return confirm('Bạn có chắc muốn xóa lớp này?');">

                                        Xóa

                                    </a>

                                </td>

                            </tr>

                        </c:forEach>

                    </c:when>

                    <c:otherwise>

                        <tr>

                            <td colspan="5"
                                class="text-center text-muted">

                                Chưa có dữ liệu lớp học.

                            </td>

                        </tr>

                    </c:otherwise>

                </c:choose>

            </tbody>

        </table>

    </div>

</div>

<jsp:include page="/layout/footer.jsp"/>