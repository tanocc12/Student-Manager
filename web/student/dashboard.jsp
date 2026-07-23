<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<jsp:include page="/layout/header.jsp">
    <jsp:param
        name="pageTitle"
        value="Student Dashboard | Student Manager"
    />
</jsp:include>

<h2 class="page-title">
    Student Dashboard
</h2>

<p class="page-subtitle">
    Theo dõi thông tin lớp học và kết quả học tập của bạn.
</p>

<div class="row g-3">

    <!-- My Class -->
    <div class="col-md-6">
        <div class="card menu-card h-100">

            <div class="card-body d-flex flex-column">

                <h5 class="card-title">
                    Lớp học của tôi
                </h5>

                <p class="text-muted mb-3 flex-grow-1">
                    Xem thông tin lớp học hiện tại của bạn.
                </p>

                <div>
                    <a
                        class="btn btn-teal btn-sm"
                        href="${pageContext.request.contextPath}/StudentClassServlet"
                    >
                        Xem lớp học
                    </a>
                </div>

            </div>

        </div>
    </div>

    <!-- My Grades -->
    <div class="col-md-6">
        <div class="card menu-card h-100">

            <div class="card-body d-flex flex-column">

                <h5 class="card-title">
                    Điểm của tôi
                </h5>

                <p class="text-muted mb-3 flex-grow-1">
                    Xem điểm Assignment, Practical Exam, Final Exam và Average.
                </p>

                <div>
                    <a
                        class="btn btn-teal btn-sm"
                        href="${pageContext.request.contextPath}/StudentGradeServlet"
                    >
                        Xem điểm
                    </a>
                </div>

            </div>

        </div>
    </div>

</div>

<jsp:include page="/layout/footer.jsp"/>