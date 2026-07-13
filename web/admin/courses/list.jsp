<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Quản lý khóa học | Student Manager"/>
</jsp:include>

<div class="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-2 mb-3">
    <div>
        <h2 class="page-title mb-0">Quản lý khóa học</h2>
        <p class="page-subtitle mb-0">Danh sách · tìm kiếm · CRUD</p>
    </div>
    <a class="btn btn-teal" href="${pageContext.request.contextPath}/admin/courses/form.jsp">+ Thêm khóa học</a>
</div>

<div class="card table-card mb-3">
    <div class="card-body">
        <form class="row g-2" method="get" action="${pageContext.request.contextPath}/CourseServlet">
            <input type="hidden" name="action" value="search">
            <div class="col-md-8">
                <input type="text" class="form-control" name="keyword"
                       placeholder="Tìm theo mã, tên khóa học..."
                       value="${param.keyword}">
            </div>
            <div class="col-md-4 d-grid d-md-flex gap-2">
                <button type="submit" class="btn btn-outline-secondary flex-fill">Tìm kiếm</button>
                <a class="btn btn-light flex-fill" href="${pageContext.request.contextPath}/admin/courses/list.jsp">Reset</a>
            </div>
        </form>
    </div>
</div>

<% if (request.getAttribute("success") != null) { %>
<div class="alert alert-success"><%= request.getAttribute("success") %></div>
<% } %>

<div class="card table-card">
    <div class="table-responsive">
        <table class="table table-hover align-middle mb-0">
            <thead class="table-light">
                <tr>
                    <th>Mã</th>
                    <th>Tên khóa học</th>
                    <th>Tín chỉ</th>
                    <th class="text-end">Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty courses}">
                        <c:forEach var="course" items="${courses}">
                            <tr>
                                <td>${course.courseCode}</td>
                                <td>${course.courseName}</td>
                                <td>${course.credits}</td>
                                <td class="text-end">
                                    <a class="btn btn-sm btn-outline-primary"
                                       href="${pageContext.request.contextPath}/admin/courses/form.jsp?id=${course.courseId}">Sửa</a>
                                    <form class="d-inline" method="post"
                                          action="${pageContext.request.contextPath}/CourseServlet"
                                          onsubmit="return confirm('Xóa khóa học này?');">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="id" value="${course.courseId}">
                                        <button type="submit" class="btn btn-sm btn-outline-danger">Xóa</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td>PRJ301</td>
                            <td>Java Web Application</td>
                            <td>3</td>
                            <td class="text-end">
                                <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/admin/courses/form.jsp?id=1">Sửa</a>
                                <button type="button" class="btn btn-sm btn-outline-danger" disabled>Xóa</button>
                            </td>
                        </tr>
                        <tr>
                            <td>DBI202</td>
                            <td>Database Systems</td>
                            <td>3</td>
                            <td class="text-end">
                                <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/admin/courses/form.jsp?id=2">Sửa</a>
                                <button type="button" class="btn btn-sm btn-outline-danger" disabled>Xóa</button>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4" class="text-muted small">
                                * Dữ liệu mẫu UI. Backend set <code>courses</code> vào request để hiển thị thật.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>
