<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<jsp:include page="/layout/header.jsp">
    <jsp:param
        name="pageTitle"
        value="Form phân công giảng dạy | Student Manager"
    />
</jsp:include>

<c:set
    var="isEdit"
    value="${formAction == 'edit'}"
/>

<h2 class="page-title">
    ${isEdit ? 'Sửa phân công giảng dạy' : 'Thêm phân công giảng dạy'}
</h2>

<p class="page-subtitle">
    Chọn giáo viên, lớp, môn học và học kỳ.
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
            action="${pageContext.request.contextPath}/assignment"
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
                    value="${assignment.id}"
                >
            </c:if>

            <!-- Teacher -->
            <div class="col-md-6">

                <label
                    class="form-label"
                    for="teacherId"
                >
                    Giáo viên
                </label>

                <select
                    class="form-select"
                    id="teacherId"
                    name="teacherId"
                    required
                >

                    <option value="">
                        -- Chọn giáo viên --
                    </option>

                    <c:forEach
                        var="teacher"
                        items="${teacherList}"
                    >

                        <option
                            value="${teacher.id}"
                            ${assignment.teacherId == teacher.id ? 'selected' : ''}
                        >

                            ${teacher.teacherCode}
                            -
                            ${teacher.fullName}

                        </option>

                    </c:forEach>

                </select>

            </div>

            <!-- Class -->
            <div class="col-md-6">

                <label
                    class="form-label"
                    for="classId"
                >
                    Lớp học
                </label>

                <select
                    class="form-select"
                    id="classId"
                    name="classId"
                    required
                >

                    <option value="">
                        -- Chọn lớp --
                    </option>

                    <c:forEach
                        var="classRoom"
                        items="${classList}"
                    >

                        <option
                            value="${classRoom.id}"
                            ${assignment.classId == classRoom.id ? 'selected' : ''}
                        >

                            ${classRoom.classCode}
                            -
                            ${classRoom.className}

                        </option>

                    </c:forEach>

                </select>

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
                        items="${courseList}"
                    >

                        <option
                            value="${course.id}"
                            ${assignment.courseId == course.id ? 'selected' : ''}
                        >

                            ${course.courseCode}
                            -
                            ${course.courseName}

                        </option>

                    </c:forEach>

                </select>

            </div>

            <!-- Semester -->
            <div class="col-md-6">

                <label
                    class="form-label"
                    for="semesterId"
                >
                    Học kỳ
                </label>

                <select
                    class="form-select"
                    id="semesterId"
                    name="semesterId"
                    required
                >

                    <option value="">
                        -- Chọn học kỳ --
                    </option>

                    <c:forEach
                        var="semester"
                        items="${semesterList}"
                    >

                        <option
                            value="${semester.id}"
                            ${assignment.semesterId == semester.id ? 'selected' : ''}
                        >

                            ${semester.semesterName}
                            -
                            ${semester.schoolYear}

                        </option>

                    </c:forEach>

                </select>

            </div>

            <!-- Buttons -->
            <div class="col-12 d-flex gap-2">

                <button
                    type="submit"
                    class="btn btn-teal"
                >
                    ${isEdit ? 'Cập nhật' : 'Thêm phân công'}
                </button>

                <a
                    class="btn btn-outline-secondary"
                    href="${pageContext.request.contextPath}/assignment?action=list"
                >
                    Hủy
                </a>

            </div>

        </form>

    </div>

</div>

<jsp:include page="/layout/footer.jsp"/>