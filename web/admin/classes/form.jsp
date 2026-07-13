<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Form lớp học | Student Manager"/>
</jsp:include>

<c:set var="isEdit" value="${not empty param.id || not empty classroom}"/>

<h2 class="page-title">${isEdit ? 'Sửa lớp học' : 'Thêm lớp học'}</h2>
<p class="page-subtitle">Nhập thông tin lớp.</p>

<div class="card table-card">
    <div class="card-body">
        <form method="post" action="${pageContext.request.contextPath}/ClassServlet" class="row g-3">
            <input type="hidden" name="action" value="${isEdit ? 'update' : 'create'}">
            <c:if test="${isEdit}">
                <input type="hidden" name="id" value="${classroom.classId != null ? classroom.classId : param.id}">
            </c:if>

            <div class="col-md-6">
                <label class="form-label" for="classCode">Mã lớp</label>
                <input type="text" class="form-control" id="classCode" name="classCode" required
                       value="${classroom.classCode != null ? classroom.classCode : ''}">
            </div>
            <div class="col-md-6">
                <label class="form-label" for="className">Tên lớp</label>
                <input type="text" class="form-control" id="className" name="className" required
                       value="${classroom.className != null ? classroom.className : ''}">
            </div>
            <div class="col-md-6">
                <label class="form-label" for="teacherId">Giáo viên</label>
                <select class="form-select" id="teacherId" name="teacherId">
                    <option value="">-- Chọn giáo viên --</option>
                    <c:forEach var="t" items="${teachers}">
                        <option value="${t.userId}">${t.fullName}</option>
                    </c:forEach>
                    <c:if test="${empty teachers}">
                        <option value="1">Thầy Nam (mẫu)</option>
                        <option value="2">Cô Lan (mẫu)</option>
                    </c:if>
                </select>
            </div>
            <div class="col-12 d-flex gap-2">
                <button type="submit" class="btn btn-teal">Lưu</button>
                <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/admin/classes/list.jsp">Hủy</a>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>
