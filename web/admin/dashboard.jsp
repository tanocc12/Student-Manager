<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Admin Dashboard | Student Manager"/>
</jsp:include>

<h2 class="page-title">Admin Dashboard</h2>
<p class="page-subtitle">Tổng quan hệ thống quản lý học sinh.</p>

<div class="row g-3 mb-4">
    <div class="col-6 col-lg-3">
        <div class="card stat-card">
            <div class="card-body">
                <div class="text-muted small">Học sinh</div>
                <div class="stat-value">${stats.students != null ? stats.students : '—'}</div>
            </div>
        </div>
    </div>
    <div class="col-6 col-lg-3">
        <div class="card stat-card">
            <div class="card-body">
                <div class="text-muted small">Khóa học</div>
                <div class="stat-value">${stats.courses != null ? stats.courses : '—'}</div>
            </div>
        </div>
    </div>
    <div class="col-6 col-lg-3">
        <div class="card stat-card">
            <div class="card-body">
                <div class="text-muted small">Lớp học</div>
                <div class="stat-value">${stats.classes != null ? stats.classes : '—'}</div>
            </div>
        </div>
    </div>
    <div class="col-6 col-lg-3">
        <div class="card stat-card">
            <div class="card-body">
                <div class="text-muted small">Ghi danh</div>
                <div class="stat-value">${stats.enrollments != null ? stats.enrollments : '—'}</div>
            </div>
        </div>
    </div>
</div>

<div class="row g-3">
    <div class="col-md-6 col-lg-4">
        <div class="card menu-card">
            <div class="card-body">
                <h5 class="card-title">Quản lý học sinh</h5>
                <p class="text-muted mb-3">Thêm, sửa, xóa, tìm kiếm học sinh.</p>
                <a class="btn btn-teal btn-sm" href="${pageContext.request.contextPath}/admin/students/list.jsp">Mở</a>
            </div>
        </div>
    </div>
    <div class="col-md-6 col-lg-4">
        <div class="card menu-card">
            <div class="card-body">
                <h5 class="card-title">Quản lý khóa học</h5>
                <p class="text-muted mb-3">CRUD khóa học / môn học.</p>
                <a class="btn btn-teal btn-sm" href="${pageContext.request.contextPath}/admin/courses/list.jsp">Mở</a>
            </div>
        </div>
    </div>
    <div class="col-md-6 col-lg-4">
        <div class="card menu-card">
            <div class="card-body">
                <h5 class="card-title">Quản lý lớp học</h5>
                <p class="text-muted mb-3">Tạo lớp, gán giáo viên.</p>
                <a class="btn btn-teal btn-sm" href="${pageContext.request.contextPath}/admin/classes/list.jsp">Mở</a>
            </div>
        </div>
    </div>
    <div class="col-md-6 col-lg-4">
        <div class="card menu-card">
            <div class="card-body">
                <h5 class="card-title">Ghi danh</h5>
                <p class="text-muted mb-3">Enrollment học sinh vào lớp/khóa.</p>
                <a class="btn btn-teal btn-sm" href="${pageContext.request.contextPath}/admin/enrollments/list.jsp">Mở</a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>
