<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Trang chủ | Student Manager"/>
</jsp:include>

<div class="hero-home mb-4">
    <div class="row align-items-center g-3">
        <div class="col-lg-8">
            <h1 class="mb-2">Student Manager</h1>
            <p class="mb-0 opacity-75">
                Quản lý học sinh, khóa học, lớp học và điểm số theo mô hình MVC (Servlet + JSP).
            </p>
        </div>
        <div class="col-lg-4 text-lg-end">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <c:choose>
                        <c:when test="${sessionScope.user.role == 'Admin'}">
                            <a class="btn btn-light fw-semibold" href="${pageContext.request.contextPath}/admin/dashboard.jsp">Vào Dashboard</a>
                        </c:when>
                        <c:when test="${sessionScope.user.role == 'Teacher'}">
                            <a class="btn btn-light fw-semibold" href="${pageContext.request.contextPath}/teacher/dashboard.jsp">Vào Dashboard</a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-light fw-semibold" href="${pageContext.request.contextPath}/student/dashboard.jsp">Vào Dashboard</a>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <a class="btn btn-light fw-semibold me-2" href="${pageContext.request.contextPath}/login.jsp">Đăng nhập</a>
                    <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/register.jsp">Đăng ký</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<div class="row g-3">
    <div class="col-md-4">
        <div class="card menu-card">
            <div class="card-body">
                <h5 class="card-title">Admin</h5>
                <p class="card-text text-muted">Quản lý học sinh, khóa học, lớp học, ghi danh.</p>
                <a href="${pageContext.request.contextPath}/admin/dashboard.jsp" class="btn btn-sm btn-teal">Xem dashboard</a>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card menu-card">
            <div class="card-body">
                <h5 class="card-title">Giáo viên</h5>
                <p class="card-text text-muted">Xem lớp phụ trách và nhập / sửa điểm.</p>
                <a href="${pageContext.request.contextPath}/teacher/dashboard.jsp" class="btn btn-sm btn-teal">Xem dashboard</a>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card menu-card">
            <div class="card-body">
                <h5 class="card-title">Học sinh</h5>
                <p class="card-text text-muted">Xem khóa học đã đăng ký và bảng điểm.</p>
                <a href="${pageContext.request.contextPath}/student/dashboard.jsp" class="btn btn-sm btn-teal">Xem dashboard</a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>
