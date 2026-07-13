<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Quản lý lớp học | Student Manager"/>
</jsp:include>

<div class="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-2 mb-3">
    <div>
        <h2 class="page-title mb-0">Quản lý lớp học</h2>
        <p class="page-subtitle mb-0">Danh sách lớp · gán giáo viên</p>
    </div>
    <a class="btn btn-teal" href="${pageContext.request.contextPath}/admin/classes/form.jsp">+ Thêm lớp</a>
</div>

<div class="card table-card">
    <div class="table-responsive">
        <table class="table table-hover align-middle mb-0">
            <thead class="table-light">
                <tr>
                    <th>Mã lớp</th>
                    <th>Tên lớp</th>
                    <th>Giáo viên</th>
                    <th>Sĩ số</th>
                    <th class="text-end">Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty classes}">
                        <c:forEach var="cl" items="${classes}">
                            <tr>
                                <td>${cl.classCode}</td>
                                <td>${cl.className}</td>
                                <td>${cl.teacherName}</td>
                                <td>${cl.studentCount}</td>
                                <td class="text-end">
                                    <a class="btn btn-sm btn-outline-primary"
                                       href="${pageContext.request.contextPath}/admin/classes/form.jsp?id=${cl.classId}">Sửa</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td>SE1801</td>
                            <td>Software Engineering 1801</td>
                            <td>Thầy Nam</td>
                            <td>30</td>
                            <td class="text-end">
                                <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/admin/classes/form.jsp?id=1">Sửa</a>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="5" class="text-muted small">* Dữ liệu mẫu UI. Backend set <code>classes</code>.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>
