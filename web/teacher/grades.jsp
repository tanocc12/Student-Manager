<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Nhập điểm | Student Manager"/>
</jsp:include>

<h2 class="page-title">Nhập / sửa điểm</h2>
<p class="page-subtitle">Cập nhật điểm học sinh theo lớp.</p>

<% if (request.getAttribute("success") != null) { %>
<div class="alert alert-success"><%= request.getAttribute("success") %></div>
<% } %>

<div class="card table-card mb-3">
    <div class="card-body">
        <form class="row g-2" method="get" action="${pageContext.request.contextPath}/GradeServlet">
            <div class="col-md-8">
                <select class="form-select" name="classId">
                    <option value="">-- Chọn lớp --</option>
                    <option value="1" ${param.classId == '1' ? 'selected' : ''}>SE1801 - PRJ301 (mẫu)</option>
                    <option value="2" ${param.classId == '2' ? 'selected' : ''}>SE1802 - DBI202 (mẫu)</option>
                </select>
            </div>
            <div class="col-md-4">
                <button type="submit" class="btn btn-outline-secondary w-100">Lọc</button>
            </div>
        </form>
    </div>
</div>

<form method="post" action="${pageContext.request.contextPath}/GradeServlet">
    <input type="hidden" name="action" value="save">
    <input type="hidden" name="classId" value="${param.classId}">

    <div class="card table-card">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th>Mã SV</th>
                        <th>Họ tên</th>
                        <th style="width:140px">Điểm giữa kỳ</th>
                        <th style="width:140px">Điểm cuối kỳ</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty gradeList}">
                            <c:forEach var="g" items="${gradeList}">
                                <tr>
                                    <td>${g.studentCode}</td>
                                    <td>${g.studentName}</td>
                                    <td>
                                        <input type="number" step="0.1" min="0" max="10" class="form-control"
                                               name="midterm_${g.studentId}" value="${g.midterm}">
                                    </td>
                                    <td>
                                        <input type="number" step="0.1" min="0" max="10" class="form-control"
                                               name="final_${g.studentId}" value="${g.finalScore}">
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td>SV001</td>
                                <td>Nguyễn Văn A</td>
                                <td><input type="number" step="0.1" min="0" max="10" class="form-control" name="midterm_1" value="7.5"></td>
                                <td><input type="number" step="0.1" min="0" max="10" class="form-control" name="final_1" value="8.0"></td>
                            </tr>
                            <tr>
                                <td>SV002</td>
                                <td>Trần Thị B</td>
                                <td><input type="number" step="0.1" min="0" max="10" class="form-control" name="midterm_2" value="8.0"></td>
                                <td><input type="number" step="0.1" min="0" max="10" class="form-control" name="final_2" value="8.5"></td>
                            </tr>
                            <tr>
                                <td colspan="4" class="text-muted small">* Dữ liệu mẫu UI. Backend set <code>gradeList</code>.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>

    <div class="mt-3">
        <button type="submit" class="btn btn-teal">Lưu điểm</button>
        <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/teacher/classes.jsp">Quay lại</a>
    </div>
</form>

<jsp:include page="/layout/footer.jsp"/>
