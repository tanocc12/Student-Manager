<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Ghi danh | Student Manager"/>
</jsp:include>

<div class="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-2 mb-3">
    <div>
        <h2 class="page-title mb-0">Ghi danh (Enrollment)</h2>
        <p class="page-subtitle mb-0">Đăng ký học sinh vào lớp / khóa học</p>
    </div>
</div>

<% if (request.getAttribute("success") != null) { %>
<div class="alert alert-success"><%= request.getAttribute("success") %></div>
<% } %>

<div class="card table-card mb-3">
    <div class="card-body">
        <form method="post" action="${pageContext.request.contextPath}/EnrollmentServlet" class="row g-3">
            <input type="hidden" name="action" value="create">
            <div class="col-md-4">
                <label class="form-label" for="studentId">Học sinh</label>
                <select class="form-select" id="studentId" name="studentId" required>
                    <option value="">-- Chọn học sinh --</option>
                    <option value="1">Nguyễn Văn A (mẫu)</option>
                    <option value="2">Trần Thị B (mẫu)</option>
                </select>
            </div>
            <div class="col-md-4">
                <label class="form-label" for="classId">Lớp / Khóa</label>
                <select class="form-select" id="classId" name="classId" required>
                    <option value="">-- Chọn lớp --</option>
                    <option value="1">SE1801 - PRJ301 (mẫu)</option>
                </select>
            </div>
            <div class="col-md-4 d-flex align-items-end">
                <button type="submit" class="btn btn-teal w-100">Ghi danh</button>
            </div>
        </form>
    </div>
</div>

<div class="card table-card">
    <div class="table-responsive">
        <table class="table table-hover align-middle mb-0">
            <thead class="table-light">
                <tr>
                    <th>Học sinh</th>
                    <th>Lớp / Khóa</th>
                    <th>Ngày ghi danh</th>
                    <th class="text-end">Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty enrollments}">
                        <c:forEach var="e" items="${enrollments}">
                            <tr>
                                <td>${e.studentName}</td>
                                <td>${e.className}</td>
                                <td>${e.enrollDate}</td>
                                <td class="text-end">
                                    <button type="button" class="btn btn-sm btn-outline-danger">Hủy</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td>Nguyễn Văn A</td>
                            <td>SE1801 - PRJ301</td>
                            <td>2026-07-01</td>
                            <td class="text-end">
                                <button type="button" class="btn btn-sm btn-outline-danger" disabled>Hủy</button>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4" class="text-muted small">* Dữ liệu mẫu UI. Backend set <code>enrollments</code>.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>
