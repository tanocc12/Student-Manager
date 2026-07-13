<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Lớp của tôi | Student Manager"/>
</jsp:include>

<h2 class="page-title">Lớp của tôi</h2>
<p class="page-subtitle">Các lớp bạn đang phụ trách.</p>

<div class="card table-card">
    <div class="table-responsive">
        <table class="table table-hover align-middle mb-0">
            <thead class="table-light">
                <tr>
                    <th>Mã lớp</th>
                    <th>Tên lớp</th>
                    <th>Khóa học</th>
                    <th>Sĩ số</th>
                    <th class="text-end">Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty myClasses}">
                        <c:forEach var="cl" items="${myClasses}">
                            <tr>
                                <td>${cl.classCode}</td>
                                <td>${cl.className}</td>
                                <td>${cl.courseName}</td>
                                <td>${cl.studentCount}</td>
                                <td class="text-end">
                                    <a class="btn btn-sm btn-teal"
                                       href="${pageContext.request.contextPath}/teacher/grades.jsp?classId=${cl.classId}">Nhập điểm</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td>SE1801</td>
                            <td>SE1801</td>
                            <td>PRJ301</td>
                            <td>30</td>
                            <td class="text-end">
                                <a class="btn btn-sm btn-teal" href="${pageContext.request.contextPath}/teacher/grades.jsp?classId=1">Nhập điểm</a>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="5" class="text-muted small">* Dữ liệu mẫu UI. Backend set <code>myClasses</code>.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>
