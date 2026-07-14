<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Student Dashboard | Student Manager"/>
</jsp:include>

<h2 class="page-title">Student Dashboard</h2>
<p class="page-subtitle">Theo dõi khóa học và kết quả học tập của bạn.</p>
<div class="row g-3">
    <div class="col-md-6">
        <div class="card menu-card">
            <div class="card-body">
                <h5 class="card-title">Khóa học của tôi</h5>
                <p class="text-muted mb-3">Danh sách môn / khóa đã đăng ký.</p>
                <a class="btn btn-teal btn-sm" href="${pageContext.request.contextPath}/student/courses.jsp">Xem khóa học</a>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card menu-card">
            <div class="card-body">
                <h5 class="card-title">Điểm của tôi</h5>
                <p class="text-muted mb-3">Bảng điểm theo từng môn.</p>
                <a class="btn btn-teal btn-sm" href="${pageContext.request.contextPath}/student/grades.jsp">Xem điểm</a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>
