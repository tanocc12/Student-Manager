<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle"
               value="Grade Management | Student Manager"/>
</jsp:include>

<h2 class="page-title">Grade Management</h2>

<p class="page-subtitle">
    Enter and update grades for students in your assigned classes.
</p>

<!-- Success message -->
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

<!-- Error message -->
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

<!-- Select teaching assignment -->
<div class="card table-card mb-4">
    <div class="card-body">

        <form method="get"
              action="${pageContext.request.contextPath}/GradeServlet"
              class="row g-3 align-items-end">

            <input type="hidden"
                   name="action"
                   value="list"/>

            <div class="col-md-9">

                <label for="teachingAssignmentId"
                       class="form-label">
                    Assigned class
                </label>

                <select id="teachingAssignmentId"
                        name="teachingAssignmentId"
                        class="form-select"
                        required>

                    <option value="">
                        -- Select assigned class --
                    </option>

                    <c:forEach var="assignment"
                               items="${teachingAssignmentList}">

                        <option value="${assignment.id}"
                            ${selectedTeachingAssignmentId == assignment.id
                              ? 'selected' : ''}>

                            ${assignment.classCode}
                            -
                            ${assignment.courseCode}
                            -
                            ${assignment.semesterName}
                            ${assignment.schoolYear}

                        </option>

                    </c:forEach>

                </select>

                <c:if test="${empty teachingAssignmentList}">
                    <div class="text-danger small mt-2">
                        You have not been assigned to any classes.
                    </div>
                </c:if>

            </div>

            <div class="col-md-3">
                <button type="submit"
                        class="btn btn-outline-secondary w-100"
                        ${empty teachingAssignmentList ? 'disabled' : ''}>

                    View students
                </button>
            </div>

        </form>

    </div>
</div>

<!-- Grade form -->
<c:if test="${not empty selectedTeachingAssignmentId}">

    <form method="post"
          action="${pageContext.request.contextPath}/GradeServlet">

        <input type="hidden"
               name="action"
               value="save"/>

        <input type="hidden"
               name="teachingAssignmentId"
               value="${selectedTeachingAssignmentId}"/>

        <div class="card table-card">

            <div class="table-responsive">

                <table class="table table-hover align-middle mb-0">

                    <thead class="table-light">
                        <tr>
                            <th>Student code</th>
                            <th>Full name</th>

                            <th style="width: 145px;">
                                Assignment
                            </th>

                            <th style="width: 145px;">
                                Practical exam
                            </th>

                            <th style="width: 145px;">
                                Final exam
                            </th>

                            <th style="width: 120px;">
                                Average
                            </th>
                        </tr>
                    </thead>

                    <tbody>

                        <c:choose>

                            <c:when test="${not empty gradeList}">

                                <c:forEach var="grade"
                                           items="${gradeList}">

                                    <tr>

                                        <td>
                                            ${grade.studentCode}
                                        </td>

                                        <td>
                                            ${grade.studentName}
                                        </td>

                                        <td>
                                            <input type="number"
                                                   class="form-control"
                                                   name="assignment_${grade.studentCourseId}"
                                                   value="${grade.assignment}"
                                                   min="0"
                                                   max="10"
                                                   step="0.01"
                                                   required/>
                                        </td>

                                        <td>
                                            <input type="number"
                                                   class="form-control"
                                                   name="practicalExam_${grade.studentCourseId}"
                                                   value="${grade.practicalExam}"
                                                   min="0"
                                                   max="10"
                                                   step="0.01"
                                                   required/>
                                        </td>

                                        <td>
                                            <input type="number"
                                                   class="form-control"
                                                   name="finalExam_${grade.studentCourseId}"
                                                   value="${grade.finalExam}"
                                                   min="0"
                                                   max="10"
                                                   step="0.01"
                                                   required/>
                                        </td>

                                        <td>
                                            <input type="text"
                                                   class="form-control"
                                                   value="${grade.average}"
                                                   readonly/>
                                        </td>

                                    </tr>

                                </c:forEach>

                            </c:when>

                            <c:otherwise>

                                <tr>
                                    <td colspan="6"
                                        class="text-center text-muted py-4">

                                        No students are enrolled in this class.

                                    </td>
                                </tr>

                            </c:otherwise>

                        </c:choose>

                    </tbody>

                </table>

            </div>

        </div>

        <div class="mt-3 d-flex gap-2">

            <button type="submit"
                    class="btn btn-teal"
                    ${empty gradeList ? 'disabled' : ''}>

                Save grades
            </button>

            <a class="btn btn-outline-secondary"
               href="${pageContext.request.contextPath}/TeacherAssignmentServlet">

                Back to my classes
            </a>

        </div>

    </form>

</c:if>

<jsp:include page="/layout/footer.jsp"/>