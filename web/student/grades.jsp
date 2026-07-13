<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Điểm của tôi | Student Manager"/>
</jsp:include>

<h2 class="page-title">Điểm của tôi</h2>
<p class="page-subtitle">Bảng điểm theo từng môn.</p>

<div class="card table-card">
    <div class="table-responsive">
        <table class="table table-hover align-middle mb-0">
            <thead class="table-light">
                <tr>
                    <th>Mã môn</th>
                    <th>Tên môn</th>
                    <th>Giữa kỳ</th>
                    <th>Cuối kỳ</th>
                    <th>Tổng kết</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty myGrades}">
                        <c:forEach var="g" items="${myGrades}">
                            <tr>
                                <td>${g.courseCode}</td>
                                <td>${g.courseName}</td>
                                <td>${g.midterm}</td>
                                <td>${g.finalScore}</td>
                                <td><strong>${g.average}</strong></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td>PRJ301</td>
                            <td>Java Web Application</td>
                            <td>7.5</td>
                            <td>8.0</td>
                            <td><strong>7.8</strong></td>
                        </tr>
                        <tr>
                            <td>DBI202</td>
                            <td>Database Systems</td>
                            <td>8.0</td>
                            <td>8.5</td>
                            <td><strong>8.3</strong></td>
                        </tr>
                        <tr>
                            <td colspan="5" class="text-muted small">* Dữ liệu mẫu UI. Backend set <code>myGrades</code>.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>
