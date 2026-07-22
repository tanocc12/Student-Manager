<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<jsp:include page="/layout/header.jsp">
    <jsp:param
        name="pageTitle"
        value="Quản lý môn học | Student Manager"
    />
</jsp:include>

<div class="d-flex flex-column flex-md-row
            justify-content-between align-items-md-center
            gap-2 mb-3">

    <div>
        <h2 class="page-title mb-0">
            Quản lý môn học
        </h2>

        <p class="page-subtitle mb-0">
            Danh sách · tìm kiếm · thêm · sửa · xóa
        </p>
    </div>

    <a
        class="btn btn-teal"
        href="${pageContext.request.contextPath}/course?action=add"
    >
        + Thêm môn học
    </a>

</div>

<!-- Search -->
<div class="card table-card mb-3">
    <div class="card-body">

        <form
            class="row g-2"
            method="get"
            action="${pageContext.request.contextPath}/course"
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
                    placeholder="Tìm theo mã hoặc tên môn học..."
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
                    href="${pageContext.request.contextPath}/course?action=list"
                >
                    Reset
                </a>

            </div>

        </form>

    </div>
</div>

<!-- Success message -->
<c:if test="${not empty success}">
    <div class="alert alert-success">
        <c:out value="${success}"/>
    </div>
</c:if>

<!-- Error message -->
<c:if test="${not empty error}">
    <div class="alert alert-danger">
        <c:out value="${error}"/>
    </div>
</c:if>

<!-- Course table -->
<div class="card table-card">
    <div class="table-responsive">

        <table class="table table-hover align-middle mb-0">

            <thead class="table-light">
                <tr>
                    <th>Mã môn học</th>
                    <th>Tên môn học</th>
                    <th>Tín chỉ</th>
                    <th class="text-end">
                        Thao tác
                    </th>
                </tr>
            </thead>

            <tbody>

                <c:choose>

                    <c:when test="${not empty courseList}">

                        <c:forEach
                            var="course"
                            items="${courseList}"
                        >

                            <tr>

                                <td>
                                    <c:out value="${course.courseCode}"/>
                                </td>

                                <td>
                                    <c:out value="${course.courseName}"/>
                                </td>

                                <td>
                                    <c:out value="${course.credits}"/>
                                </td>

                                <td class="text-end">

                                    <a
                                        class="btn btn-sm btn-outline-primary"
                                        href="${pageContext.request.contextPath}/course?action=edit&id=${course.id}"
                                    >
                                        Sửa
                                    </a>

                                    <form
                                        class="d-inline"
                                        method="post"
                                        action="${pageContext.request.contextPath}/course"
                                        onsubmit="return confirm('Bạn có chắc muốn xóa môn học này?');"
                                    >

                                        <input
                                            type="hidden"
                                            name="action"
                                            value="delete"
                                        >

                                        <input
                                            type="hidden"
                                            name="id"
                                            value="${course.id}"
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
                                colspan="4"
                                class="text-center text-muted py-4"
                            >
                                Không có môn học nào.
                            </td>
                        </tr>

                    </c:otherwise>

                </c:choose>

            </tbody>

        </table>

    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>