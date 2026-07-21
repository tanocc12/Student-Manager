<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<jsp:include page="/layout/header.jsp">
    <jsp:param
        name="pageTitle"
        value="Form lớp học | Student Manager"
    />
</jsp:include>

<c:set
    var="isEdit"
    value="${formAction == 'update'}"
/>

<h2 class="page-title">
    ${isEdit ? 'Sửa lớp học' : 'Thêm lớp học'}
</h2>

<p class="page-subtitle">
    Nhập thông tin lớp học.
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
            action="${pageContext.request.contextPath}/ClassServlet"
            class="row g-3"
        >

            <input
                type="hidden"
                name="action"
                value="${isEdit ? 'update' : 'insert'}"
            >

            <c:if test="${isEdit}">
                <input
                    type="hidden"
                    name="id"
                    value="${classroom.id}"
                >
            </c:if>

            <!-- Class Code -->
            <div class="col-md-6">

                <label
                    class="form-label"
                    for="classCode"
                >
                    Mã lớp
                </label>

                <input
                    type="text"
                    class="form-control"
                    id="classCode"
                    name="classCode"
                    maxlength="30"
                    required
                    value="<c:out value='${classroom.classCode}'/>"
                >

            </div>

            <!-- Class Name -->
            <div class="col-md-6">

                <label
                    class="form-label"
                    for="className"
                >
                    Tên lớp
                </label>

                <input
                    type="text"
                    class="form-control"
                    id="className"
                    name="className"
                    maxlength="100"
                    required
                    value="<c:out value='${classroom.className}'/>"
                >

            </div>

            <!-- Major -->
            <div class="col-md-6">

                <label
                    class="form-label"
                    for="majorId"
                >
                    Chuyên ngành
                </label>

                <select
                    class="form-select"
                    id="majorId"
                    name="majorId"
                    required
                >

                    <option value="">
                        -- Chọn chuyên ngành --
                    </option>

                    <c:forEach
                        var="major"
                        items="${majors}"
                    >

                        <c:choose>

                            <c:when test="${classroom.majorId == major.id}">

                                <option
                                    value="${major.id}"
                                    selected
                                >
                                    <c:out value="${major.majorCode}"/>
                                    -
                                    <c:out value="${major.majorName}"/>
                                </option>

                            </c:when>

                            <c:otherwise>

                                <option value="${major.id}">
                                    <c:out value="${major.majorCode}"/>
                                    -
                                    <c:out value="${major.majorName}"/>
                                </option>

                            </c:otherwise>

                        </c:choose>

                    </c:forEach>

                </select>

                <c:if test="${empty majors}">
                    <div class="text-danger mt-1">
                        Chưa có chuyên ngành trong hệ thống.
                    </div>
                </c:if>

            </div>

            <!-- Course -->
            <div class="col-md-6">

                <label
                    class="form-label"
                    for="courseId"
                >
                    Môn học
                </label>

                <select
                    class="form-select"
                    id="courseId"
                    name="courseId"
                    required
                >

                    <option value="">
                        -- Chọn môn học --
                    </option>

                    <c:forEach
                        var="course"
                        items="${courses}"
                    >

                        <c:choose>

                            <c:when test="${classroom.courseId == course.id}">

                                <option
                                    value="${course.id}"
                                    selected
                                >
                                    <c:out value="${course.courseCode}"/>
                                    -
                                    <c:out value="${course.courseName}"/>
                                </option>

                            </c:when>

                            <c:otherwise>

                                <option value="${course.id}">
                                    <c:out value="${course.courseCode}"/>
                                    -
                                    <c:out value="${course.courseName}"/>
                                </option>

                            </c:otherwise>

                        </c:choose>

                    </c:forEach>

                </select>

                <c:if test="${empty courses}">
                    <div class="text-danger mt-1">
                        Chưa có môn học trong hệ thống.
                    </div>
                </c:if>

            </div>

            <!-- Buttons -->
            <div class="col-12 d-flex gap-2">

                <button
                    type="submit"
                    class="btn btn-teal"
                    <c:if test="${empty majors || empty courses}">
                        disabled
                    </c:if>
                >
                    ${isEdit ? 'Cập nhật' : 'Thêm lớp'}
                </button>

                <a
                    class="btn btn-outline-secondary"
                    href="${pageContext.request.contextPath}/ClassServlet?action=list"
                >
                    Hủy
                </a>

            </div>

        </form>

    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>