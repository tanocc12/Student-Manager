<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<jsp:include page="/layout/header.jsp">
    <jsp:param
        name="pageTitle"
        value="Quản lý phân công giảng dạy | Student Manager"
    />
</jsp:include>

<div class="d-flex flex-column flex-md-row
            justify-content-between align-items-md-center
            gap-2 mb-3">

    <div>

        <h2 class="page-title mb-0">
            Quản lý phân công giảng dạy
        </h2>

        <p class="page-subtitle mb-0">
            Danh sách · tìm kiếm · thêm · sửa · xóa
        </p>

    </div>

    <a
        class="btn btn-teal"
        href="${pageContext.request.contextPath}/assignment?action=add"
    >
        + Thêm phân công
    </a>

</div>

<!-- Search -->
<div class="card table-card mb-3">

    <div class="card-body">

        <form
            class="row g-2"
            method="get"
            action="${pageContext.request.contextPath}/assignment"
        >

            <input
                type="hidden"
                name="action"
                value="search"
            >

            <div class="col-md-8">

                <input
                    type="text"
                    class="form-control"
                    name="keyword"
                    placeholder="Tìm theo giáo viên, lớp hoặc môn học..."
                    value="<c:out value='${keyword}'/>"
                >

            </div>

            <div class="col-md-4 d-grid d-md-flex gap-2">

                <button
                    type="submit"
                    class="btn btn-outline-secondary flex-fill"
                >
                    Tìm kiếm
                </button>

                <a
                    class="btn btn-light flex-fill"
                    href="${pageContext.request.contextPath}/assignment?action=list"
                >
                    Reset
                </a>

            </div>

        </form>

    </div>

</div>

<!-- Success -->
<c:if test="${not empty success}">
    <div class="alert alert-success">
        <c:out value="${success}"/>
    </div>
</c:if>

<!-- Error -->
<c:if test="${not empty error}">
    <div class="alert alert-danger">
        <c:out value="${error}"/>
    </div>
</c:if>

<!-- Assignment Table -->
<div class="card table-card">

    <div class="table-responsive">

        <table class="table table-hover align-middle mb-0">

            <thead class="table-light">

                <tr>

                    <th width="60">
                        STT
                    </th>

                    <th>
                        Giáo viên
                    </th>

                    <th>
                        Lớp
                    </th>

                    <th>
                        Môn học
                    </th>

                    <th>
                        Học kỳ
                    </th>

                    <th>
                        Năm học
                    </th>

                    <th class="text-end">
                        Thao tác
                    </th>

                </tr>

            </thead>

            <tbody>

                <c:choose>

                    <c:when test="${not empty assignmentList}">

                        <c:forEach
                            var="assignment"
                            items="${assignmentList}"
                            varStatus="loop"
                        >

                            <tr>

                                <td>
                                    ${loop.count}
                                </td>

                                <!-- Teacher -->
                                <td>

                                    <div class="fw-semibold">
                                        <c:out value="${assignment.teacherCode}"/>
                                    </div>

                                    <div class="text-muted small">
                                        <c:out value="${assignment.teacherName}"/>
                                    </div>

                                </td>

                                <!-- Class -->
                                <td>

                                    <div class="fw-semibold">
                                        <c:out value="${assignment.classCode}"/>
                                    </div>

                                    <div class="text-muted small">
                                        <c:out value="${assignment.className}"/>
                                    </div>

                                </td>

                                <!-- Course -->
                                <td>

                                    <div class="fw-semibold">
                                        <c:out value="${assignment.courseCode}"/>
                                    </div>

                                    <div class="text-muted small">
                                        <c:out value="${assignment.courseName}"/>
                                    </div>

                                </td>

                                <td>
                                    <c:out value="${assignment.semesterName}"/>
                                </td>

                                <td>
                                    <c:out value="${assignment.schoolYear}"/>
                                </td>

                                <td class="text-end">

                                    <a
                                        class="btn btn-sm btn-outline-primary"
                                        href="${pageContext.request.contextPath}/assignment?action=edit&id=${assignment.id}"
                                    >
                                        Sửa
                                    </a>

                                    <form
                                        class="d-inline"
                                        method="post"
                                        action="${pageContext.request.contextPath}/assignment"
                                        onsubmit="return confirm('Bạn có chắc muốn xóa phân công này?');"
                                    >

                                        <input
                                            type="hidden"
                                            name="action"
                                            value="delete"
                                        >

                                        <input
                                            type="hidden"
                                            name="id"
                                            value="${assignment.id}"
                                        >

                                        <button
                                            type="submit"
                                            class="btn btn-sm btn-outline-danger"
                                        >
                                            Xóa
                                        </button>

                                    </form>

                                </td>

                            </tr>

                        </c:forEach>

                    </c:when>

                    <c:otherwise>

                        <tr>

                            <td
                                colspan="7"
                                class="text-center text-muted py-4"
                            >
                                Không có phân công giảng dạy nào.
                            </td>

                        </tr>

                    </c:otherwise>

                </c:choose>

            </tbody>

        </table>

    </div>

</div>

<jsp:include page="/layout/footer.jsp"/>