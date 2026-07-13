<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Lỗi | Student Manager"/>
</jsp:include>

<div class="text-center py-5">
    <h2 class="page-title">Đã xảy ra lỗi</h2>
    <p class="page-subtitle">
        <c:choose>
            <c:when test="${not empty error}">${error}</c:when>
            <c:otherwise>Không thể xử lý yêu cầu. Vui lòng thử lại.</c:otherwise>
        </c:choose>
    </p>
    <a class="btn btn-teal" href="${pageContext.request.contextPath}/home.jsp">Về trang chủ</a>
</div>

<jsp:include page="/layout/footer.jsp"/>
