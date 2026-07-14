<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Teacher Dashboard | Student Manager"/>
</jsp:include>

<h2 class="page-title">Teacher Dashboard</h2>
<p class="page-subtitle">Quản lý lớp phụ trách và điểm số học sinh.</p>
<div class="row g-3">
    <div class="col-md-6">
        <div class="card menu-card">
            <div class="card-body">
                <h5 class="card-title">Lớp của tôi</h5>
                <p class="text-muted mb-3">Danh sách lớp đang giảng dạy.</p>
                <a class="btn btn-teal btn-sm" href="${pageContext.request.contextPath}/teacher/classes.jsp">Xem lớp</a>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card menu-card">
            <div class="card-body">
                <h5 class="card-title">Nhập / sửa điểm</h5>
                <p class="text-muted mb-3">Cập nhật điểm cho học sinh trong lớp.</p>
                <a class="btn btn-teal btn-sm" href="${pageContext.request.contextPath}/teacher/grades.jsp">Nhập điểm</a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>
