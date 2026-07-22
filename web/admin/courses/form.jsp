<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<jsp:include page="/layout/header.jsp">
    <jsp:param
        name="pageTitle"
        value="Form môn học | Student Manager"
    />
</jsp:include>

<c:set
    var="isEdit"
    value="${formAction == 'edit'}"
/>

<h2 class="page-title">
    ${isEdit ? 'Sửa môn học' : 'Thêm môn học'}
</h2>

<p class="page-subtitle">
    Nhập thông tin môn học.
</p>

<div class="card table-card">
    <div class="card-body">

        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                <c:out value="${error}"/>
            </div>
        </c:if>

        <form
            method="post"
            action="${pageContext.request.contextPath}/course"
            class="row g-3"
        >

            <input
                type="hidden"
                name="action"
                value="${isEdit ? 'edit' : 'add'}"
            >

            <c:if test="${isEdit}">
                <input
                    type="hidden"
                    name="id"
                    value="${course.id}"
                >
            </c:if>

            <!-- Course Code -->
            <div class="col-md-6">

                <label
                    class="form-label"
                    for="courseCode"
                >
                    Mã môn học
                </label>

                <input
                    type="text"
                    class="form-control"
                    id="courseCode"
                    name="courseCode"
                    maxlength="30"
                    required
                    value="<c:out value='${course.courseCode}'/>"
                >

            </div>

            <!-- Credits -->
            <div class="col-md-6">

                <label
                    class="form-label"
                    for="credits"
                >
                    Số tín chỉ
                </label>

                <input
                    type="number"
                    class="form-control"
                    id="credits"
                    name="credits"
                    min="1"
                    max="10"
                    required
                    value="${empty course ? 3 : course.credits}"
                >

            </div>

            <!-- Course Name -->
            <div class="col-12">

                <label
                    class="form-label"
                    for="courseName"
                >
                    Tên môn học
                </label>

                <input
                    type="text"
                    class="form-control"
                    id="courseName"
                    name="courseName"
                    maxlength="100"
                    required
                    value="<c:out value='${course.courseName}'/>"
                >

            </div>

            <!-- Buttons -->
            <div class="col-12 d-flex gap-2">

                <button
                    type="submit"
                    class="btn btn-teal"
                >
                    ${isEdit ? 'Cập nhật' : 'Thêm môn học'}
                </button>

                <a
                    class="btn btn-outline-secondary"
                    href="${pageContext.request.contextPath}/course?action=list"
                >
                    Hủy
                </a>

            </div>

        </form>

    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>