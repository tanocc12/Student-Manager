<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Form khóa học | Student Manager"/>
</jsp:include>

<c:set var="isEdit" value="${not empty param.id || not empty course}"/>

<h2 class="page-title">${isEdit ? 'Sửa khóa học' : 'Thêm khóa học'}</h2>
<p class="page-subtitle">Nhập thông tin khóa học.</p>

<% if (request.getAttribute("error") != null) { %>
<div class="alert alert-danger"><%= request.getAttribute("error") %></div>
<% } %>

<div class="card table-card">
    <div class="card-body">
        <form method="post" action="${pageContext.request.contextPath}/CourseServlet" class="row g-3">
            <input type="hidden" name="action" value="${isEdit ? 'update' : 'create'}">
            <c:if test="${isEdit}">
                <input type="hidden" name="id" value="${course.courseId != null ? course.courseId : param.id}">
            </c:if>

            <div class="col-md-6">
                <label class="form-label" for="courseCode">Mã khóa học</label>
                <input type="text" class="form-control" id="courseCode" name="courseCode" required
                       value="${course.courseCode != null ? course.courseCode : ''}">
            </div>
            <div class="col-md-6">
                <label class="form-label" for="credits">Số tín chỉ</label>
                <input type="number" class="form-control" id="credits" name="credits" min="1" max="10" required
                       value="${course.credits != null ? course.credits : '3'}">
            </div>
            <div class="col-12">
                <label class="form-label" for="courseName">Tên khóa học</label>
                <input type="text" class="form-control" id="courseName" name="courseName" required
                       value="${course.courseName != null ? course.courseName : ''}">
            </div>
            <div class="col-12">
                <label class="form-label" for="description">Mô tả</label>
                <textarea class="form-control" id="description" name="description" rows="3">${course.description != null ? course.description : ''}</textarea>
            </div>
            <div class="col-12 d-flex gap-2">
                <button type="submit" class="btn btn-teal">${isEdit ? 'Cập nhật' : 'Lưu'}</button>
                <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/admin/courses/list.jsp">Hủy</a>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>
