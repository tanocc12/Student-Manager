<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Khóa học của tôi | Student Manager"/>
</jsp:include>

<h2 class="page-title">Khóa học của tôi</h2>
<p class="page-subtitle">Các môn / khóa bạn đã đăng ký.</p>

<div class="card table-card mb-3">
    <div class="card-body">
        <form class="row g-2" method="get">
            <div class="col-md-8">
                <input type="text" class="form-control" name="keyword" placeholder="Tìm khóa học..."
                       value="${param.keyword}">
            </div>
            <div class="col-md-4">
                <button type="submit" class="btn btn-outline-secondary w-100">Tìm kiếm</button>
            </div>
        </form>
    </div>
</div>

<div class="card table-card">
    <div class="table-responsive">
        <table class="table table-hover align-middle mb-0">
            <thead class="table-light">
                <tr>
                    <th>Mã</th>
                    <th>Tên khóa học</th>
                    <th>Lớp</th>
                    <th>Tín chỉ</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty myCourses}">
                        <c:forEach var="course" items="${myCourses}">
                            <tr>
                                <td>${course.courseCode}</td>
                                <td>${course.courseName}</td>
                                <td>${course.className}</td>
                                <td>${course.credits}</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td>PRJ301</td>
                            <td>Java Web Application</td>
                            <td>SE1801</td>
                            <td>3</td>
                        </tr>
                        <tr>
                            <td>DBI202</td>
                            <td>Database Systems</td>
                            <td>SE1801</td>
                            <td>3</td>
                        </tr>
                        <tr>
                            <td colspan="4" class="text-muted small">* Dữ liệu mẫu UI. Backend set <code>myCourses</code>.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>
