<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Quản lý học sinh | Student Manager"/>
</jsp:include>

<div class="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-2 mb-3">
    <div>
        <h2 class="page-title mb-0">Quản lý học sinh</h2>
        <p class="page-subtitle mb-0">Danh sách · tìm kiếm · CRUD</p>
    </div>
    <a class="btn btn-teal"
       href="${pageContext.request.contextPath}/StudentServlet?action=create">
        + Thêm học sinh
    </a>
</div>

<div class="card table-card mb-3">
    <div class="card-body">
        <form class="row g-2" method="get" action="${pageContext.request.contextPath}/StudentServlet">
            <input type="hidden" name="action" value="search">
            <div class="col-md-8">
                <input type="text" class="form-control" name="keyword"
                       placeholder="Tìm theo tên, mã, email..."
                       value="${param.keyword}">
            </div>
            <div class="col-md-4 d-grid d-md-flex gap-2">
                <button type="submit" class="btn btn-outline-secondary flex-fill">Tìm kiếm</button>
                <a class="btn btn-light flex-fill"
                   href="${pageContext.request.contextPath}/StudentServlet?action=list">
                    Reset
                </a>
            </div>
        </form>
    </div>
</div>

<c:if test="${not empty error}">
    <div class="alert alert-danger">
        ${error}
    </div>
</c:if>

<c:if test="${not empty success}">
    <div class="alert alert-success">
        ${success}
    </div>
</c:if>
<div class="card table-card">
    <div class="table-responsive">
        <table class="table table-hover align-middle mb-0">
            <thead class="table-light">
                <tr>
                    <th>Mã</th>
                    <th>Họ tên</th>
                    <th>Email</th>
                    <th>Lớp</th>
                    <th class="text-end">Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty students}">
                        <c:forEach var="s" items="${students}">
                            <tr>
                                <td>${s.studentCode}</td>
                                <td>${s.fullName}</td>
                                <td>${s.email}</td>
                                <td>${s.className}</td>
                                <td class="text-end">
                                    <a class="btn btn-sm btn-outline-primary"
                                       href="${pageContext.request.contextPath}/StudentServlet?action=edit&id=${s.id}">Sửa</a>
                                    <form class="d-inline" method="post"
                                          action="${pageContext.request.contextPath}/StudentServlet"
                                          onsubmit="return confirm('Xóa học sinh này?');">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="id" value="${s.id}">
                                        <button type="submit" class="btn btn-sm btn-outline-danger">Xóa</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when> 
                    <c:otherwise>
                        <tr>
                            <td colspan="5" class="text-center text-muted py-4">
                                Không tìm thấy học sinh nào.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<nav class="mt-3" aria-label="Pagination">
    <ul class="pagination justify-content-end mb-0">
        <li class="page-item disabled"><a class="page-link" href="#">Trước</a></li>
        <li class="page-item active"><a class="page-link" href="#">1</a></li>
        <li class="page-item"><a class="page-link" href="#">2</a></li>
        <li class="page-item"><a class="page-link" href="#">Sau</a></li>
    </ul>
</nav>

<jsp:include page="/layout/footer.jsp"/>
