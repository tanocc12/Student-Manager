<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<jsp:include page="/layout/header.jsp">
    <jsp:param
        name="pageTitle"
        value="Điểm của tôi | Student Manager"
    />
</jsp:include>

<h2 class="page-title">
    Điểm của tôi
</h2>

<p class="page-subtitle">
    Bảng điểm theo từng môn học.
</p>

<c:if test="${not empty error}">
    <div class="alert alert-danger">
        ${error}
    </div>
</c:if>

<div class="card table-card">
    <div class="table-responsive">

        <table class="table table-hover align-middle mb-0">

            <thead class="table-light">
                <tr>
                    <th>Mã môn</th>
                    <th>Tên môn</th>
                    <th>Assignment</th>
                    <th>Practical Exam</th>
                    <th>Final Exam</th>
                    <th>Average</th>
                </tr>
            </thead>

            <tbody>

                <c:choose>

                    <c:when test="${not empty myGrades}">

                        <c:forEach var="g" items="${myGrades}">

                            <tr>

                                <td>
                                    ${g.courseCode}
                                </td>

                                <td>
                                    ${g.courseName}
                                </td>

                                <td>
                                    <c:choose>
                                        <c:when test="${g.assignment != null}">
                                            ${g.assignment}
                                        </c:when>
                                        <c:otherwise>
                                            —
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <td>
                                    <c:choose>
                                        <c:when test="${g.practicalExam != null}">
                                            ${g.practicalExam}
                                        </c:when>
                                        <c:otherwise>
                                            —
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <td>
                                    <c:choose>
                                        <c:when test="${g.finalExam != null}">
                                            ${g.finalExam}
                                        </c:when>
                                        <c:otherwise>
                                            —
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <td>
                                    <strong>
                                        <c:choose>
                                            <c:when test="${g.average != null}">
                                                ${g.average}
                                            </c:when>
                                            <c:otherwise>
                                                —
                                            </c:otherwise>
                                        </c:choose>
                                    </strong>
                                </td>

                            </tr>

                        </c:forEach>

                    </c:when>

                    <c:otherwise>

                        <tr>
                            <td
                                colspan="6"
                                class="text-center text-muted py-4"
                            >
                                Hiện chưa có điểm.
                            </td>
                        </tr>

                    </c:otherwise>

                </c:choose>

            </tbody>

        </table>

    </div>
</div>

<div class="mt-3">
    <a
        class="btn btn-outline-secondary btn-sm"
        href="${pageContext.request.contextPath}/student/dashboard.jsp"
    >
        Quay lại Dashboard
    </a>
</div>

<jsp:include page="/layout/footer.jsp"/>