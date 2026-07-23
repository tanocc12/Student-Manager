<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<jsp:include page="/layout/header.jsp">
    <jsp:param
        name="pageTitle"
        value="Lớp học của tôi | Student Manager"
    />
</jsp:include>

<h2 class="page-title">
    Lớp học của tôi
</h2>

<p class="page-subtitle">
    Thông tin lớp học hiện tại của bạn.
</p>

<c:if test="${not empty error}">
    <div class="alert alert-danger">
        ${error}
    </div>
</c:if>

<c:choose>

    <c:when test="${not empty student}">

        <div class="card table-card">

            <div class="card-body">

                <div class="row g-4">

                    <div class="col-md-6">

                        <div class="text-muted small mb-1">
                            Mã sinh viên
                        </div>

                        <div class="fw-semibold">
                            ${student.studentCode}
                        </div>

                    </div>

                    <div class="col-md-6">

                        <div class="text-muted small mb-1">
                            Họ và tên
                        </div>

                        <div class="fw-semibold">
                            ${student.fullName}
                        </div>

                    </div>

                    <div class="col-md-6">

                        <div class="text-muted small mb-1">
                            Mã lớp
                        </div>

                        <div class="fw-semibold">

                            <c:choose>

                                <c:when test="${not empty student.classCode}">
                                    ${student.classCode}
                                </c:when>

                                <c:otherwise>
                                    Chưa được xếp lớp
                                </c:otherwise>

                            </c:choose>

                        </div>

                    </div>

                    <div class="col-md-6">

                        <div class="text-muted small mb-1">
                            Tên lớp
                        </div>

                        <div class="fw-semibold">

                            <c:choose>

                                <c:when test="${not empty student.className}">
                                    ${student.className}
                                </c:when>

                                <c:otherwise>
                                    Chưa có thông tin
                                </c:otherwise>

                            </c:choose>

                        </div>

                    </div>

                    <div class="col-md-6">

                        <div class="text-muted small mb-1">
                            Mã chuyên ngành
                        </div>

                        <div class="fw-semibold">

                            <c:choose>

                                <c:when test="${not empty student.majorCode}">
                                    ${student.majorCode}
                                </c:when>

                                <c:otherwise>
                                    Chưa có thông tin
                                </c:otherwise>

                            </c:choose>

                        </div>

                    </div>

                    <div class="col-md-6">

                        <div class="text-muted small mb-1">
                            Chuyên ngành
                        </div>

                        <div class="fw-semibold">

                            <c:choose>

                                <c:when test="${not empty student.majorName}">
                                    ${student.majorName}
                                </c:when>

                                <c:otherwise>
                                    Chưa có thông tin
                                </c:otherwise>

                            </c:choose>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    </c:when>

    <c:otherwise>

        <div class="alert alert-warning">
            Không tìm thấy thông tin sinh viên hoặc tài khoản này chưa được liên kết với sinh viên.
        </div>

    </c:otherwise>

</c:choose>

<div class="mt-3">

    <a
        class="btn btn-outline-secondary btn-sm"
        href="${pageContext.request.contextPath}/student/dashboard.jsp"
    >
        Quay lại Dashboard
    </a>

</div>

<jsp:include page="/layout/footer.jsp"/>