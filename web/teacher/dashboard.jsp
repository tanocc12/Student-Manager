<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle" value="Teacher Dashboard | Student Manager"/>
</jsp:include>

<div class="container-fluid">

    <h2 class="page-title">Teacher Dashboard</h2>
    <p class="page-subtitle">
        Manage your classes and student grades.
    </p>

    <div class="row g-4">

        <!-- My Classes -->
        <div class="col-md-6">
            <div class="card menu-card h-100">
                <div class="card-body d-flex flex-column">

                    <h5 class="card-title">
                        My Classes
                    </h5>

                    <p class="text-muted flex-grow-1">
                        View all classes assigned to you.
                    </p>

                    <a class="btn btn-teal"
                       href="${pageContext.request.contextPath}/TeacherAssignmentServlet">
                        View Classes
                    </a>

                </div>
            </div>
        </div>

        <!-- Grade Management -->
        <div class="col-md-6">
            <div class="card menu-card h-100">
                <div class="card-body d-flex flex-column">

                    <h5 class="card-title">
                        Grade Management
                    </h5>

                    <p class="text-muted flex-grow-1">
                        Enter and update student grades.
                    </p>

                    <a class="btn btn-teal"
                       href="${pageContext.request.contextPath}/GradeServlet">
                        Manage Grades
                    </a>

                </div>
            </div>
        </div>

    </div>

</div>

<jsp:include page="/layout/footer.jsp"/>