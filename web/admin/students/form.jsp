<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Form học sinh | Student Manager"/>
</jsp:include>

<c:set var="isEdit" value="${not empty param.id || not empty student}"/>

<h2 class="page-title">${isEdit ? 'Sửa học sinh' : 'Thêm học sinh'}</h2>
<p class="page-subtitle">Nhập thông tin học sinh.</p>

<% if (request.getAttribute("error") != null) { %>
<div class="alert alert-danger"><%= request.getAttribute("error") %></div>
<% } %>

<div class="card table-card">
    <div class="card-body">
        <form method="post" action="${pageContext.request.contextPath}/StudentServlet" class="row g-3">
            <input type="hidden" name="action" value="${isEdit ? 'update' : 'create'}">
            <c:if test="${isEdit}">
                <input type="hidden" name="id" value="${student.studentId != null ? student.studentId : param.id}">
            </c:if>

            <div class="col-md-6">
                <label class="form-label" for="studentCode">Mã học sinh</label>
                <input type="text" class="form-control" id="studentCode" name="studentCode" required
                       value="${student.studentCode != null ? student.studentCode : ''}">
            </div>
            <div class="col-md-6">
                <label class="form-label" for="fullName">Họ và tên</label>
                <input type="text" class="form-control" id="fullName" name="fullName" required
                       value="${student.fullName != null ? student.fullName : ''}">
            </div>
            <div class="col-md-6">
                <label class="form-label" for="email">Email</label>
                <input type="email" class="form-control" id="email" name="email" required
                       value="${student.email != null ? student.email : ''}">
            </div>
            <div class="col-md-6">
                <label class="form-label" for="phone">Số điện thoại</label>
                <input type="text" class="form-control" id="phone" name="phone"
                       value="${student.phone != null ? student.phone : ''}">
            </div>
            <div class="col-md-6">
                <label class="form-label" for="classId">Lớp</label>
                <select class="form-select" id="classId" name="classId">
                    <option value="">-- Chọn lớp --</option>
                    <c:forEach var="cl" items="${classes}">
                        <option value="${cl.classId}" ${student.classId == cl.classId ? 'selected' : ''}>${cl.className}</option>
                    </c:forEach>
                    <c:if test="${empty classes}">
                        <option value="1">SE1801 (mẫu)</option>
                        <option value="2">SE1802 (mẫu)</option>
                    </c:if>
                </select>
            </div>
            <div class="col-12 d-flex gap-2">
                <button type="submit" class="btn btn-teal">${isEdit ? 'Cập nhật' : 'Lưu'}</button>
                <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/admin/students/list.jsp">Hủy</a>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>
