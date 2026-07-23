<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle"
               value="My Classes | Student Manager"/>
</jsp:include>

<h2 class="page-title">My Classes</h2>

<p class="page-subtitle">
    View the classes currently assigned to you.
</p>

<c:if test="${not empty sessionScope.success}">
    <div class="alert alert-success alert-dismissible fade show"
         role="alert">

        ${sessionScope.success}

        <button type="button"
                class="btn-close"
                data-bs-dismiss="alert">
        </button>
    </div>

    <c:remove var="success" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.error}">
    <div class="alert alert-danger alert-dismissible fade show"
         role="alert">

        ${sessionScope.error}

        <button type="button"
                class="btn-close"
                data-bs-dismiss="alert">
        </button>
    </div>

    <c:remove var="error" scope="session"/>
</c:if>

<div class="card table-card">

    <div class="table-responsive">

        <table class="table table-hover align-middle mb-0">

            <thead class="table-light">
                <tr>
                    <th>Class code</th>
                    <th>Class name</th>
                    <th>Course</th>
                    <th>Semester</th>
                    <th>Students</th>
                    <th class="text-end">Action</th>
                </tr>
            </thead>

            <tbody>

                <c:choose>

                    <c:when test="${not empty myClasses}">

                        <c:forEach var="cl"
                                   items="${myClasses}">

                            <tr>

                                <td>
                                    ${cl.classCode}
                                </td>

                                <td>
                                    ${cl.className}
                                </td>

                                <td>
                                    ${cl.courseCode}
                                    -
                                    ${cl.courseName}
                                </td>

                                <td>
                                    ${cl.semesterName}
                                    ${cl.schoolYear}
                                </td>

                                <td>
                                    ${cl.studentCount}
                                </td>

                                <td class="text-end">

                                    <a class="btn btn-sm btn-teal"
                                       href="${pageContext.request.contextPath}/GradeServlet?action=list&teachingAssignmentId=${cl.teachingAssignmentId}">

                                        Manage grades
                                    </a>

                                </td>

                            </tr>

                        </c:forEach>

                    </c:when>

                    <c:otherwise>

                        <tr>
                            <td colspan="6"
                                class="text-center text-muted py-4">

                                You have not been assigned to any classes.

                            </td>
                        </tr>

                    </c:otherwise>

                </c:choose>

            </tbody>

        </table>

    </div>

</div>

<div class="mt-3">

    <a class="btn btn-outline-secondary"
       href="${pageContext.request.contextPath}/teacher/dashboard.jsp">

        Back to dashboard
    </a>

</div>

<jsp:include page="/layout/footer.jsp"/>  