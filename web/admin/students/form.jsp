<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle"
               value="Form học sinh | Student Manager"/>
</jsp:include>

<%-- 
    Không dùng: ${not empty student}
    Vì khi thêm thất bại, Servlet cũng gửi student về form.

    Chỉ xem là edit khi formAction = update.
--%>
<c:set var="isEdit"
       value="${formAction == 'update'}"/>

<h2 class="page-title">
    ${isEdit ? 'Sửa hồ sơ học sinh' : 'Thêm hồ sơ học sinh'}
</h2>

<p class="page-subtitle">
    <c:choose>
        <c:when test="${isEdit}">
            Cập nhật thông tin học tập của học sinh.
        </c:when>

        <c:otherwise>
            Chọn tài khoản sinh viên đã đăng ký và nhập thông tin học tập.
        </c:otherwise>
    </c:choose>
</p>

<c:if test="${not empty error}">
    <div class="alert alert-danger">
        <c:out value="${error}"/>
    </div>
</c:if>

<div class="card table-card">
    <div class="card-body">

        <form method="post"
              action="${pageContext.request.contextPath}/StudentServlet"
              class="row g-3">

            <%-- Action insert hoặc update --%>
            <input type="hidden"
                   name="action"
                   value="${isEdit ? 'update' : 'insert'}">

            <%-- Khi update phải gửi Student.Id --%>
            <c:if test="${isEdit}">
                <input type="hidden"
                       name="id"
                       value="${student.id}">
            </c:if>

            <%-- ==========================================
                 CREATE: Chọn tài khoản đã đăng ký
                 ========================================== --%>
            <c:if test="${not isEdit}">

                <div class="col-12">
                    <label class="form-label"
                           for="userId">
                        Tài khoản sinh viên
                    </label>

                    <select class="form-select"
                            id="userId"
                            name="userId"
                            required>

                        <option value="">
                            -- Chọn tài khoản đã đăng ký --
                        </option>

                        <c:forEach var="u"
                                   items="${availableUsers}">

                            <option value="${u.id}"
                                    ${student.userId == u.id
                                      ? 'selected' : ''}>

                                <c:out value="${u.fullName}"/>
                                -
                                <c:out value="${u.username}"/>
                                -
                                <c:out value="${u.email}"/>
                            </option>

                        </c:forEach>
                    </select>

                    <div class="form-text">
                        Chỉ hiển thị tài khoản có vai trò Student
                        và chưa có hồ sơ học sinh.
                    </div>
                </div>

                <%-- Không còn tài khoản phù hợp --%>
                <c:if test="${empty availableUsers}">
                    <div class="col-12">
                        <div class="alert alert-warning mb-0">
                            Hiện không có tài khoản Student nào
                            chưa được tạo hồ sơ.

                            Sinh viên cần đăng ký tài khoản trước
                            khi Admin thêm hồ sơ.
                        </div>
                    </div>
                </c:if>

            </c:if>

            <%-- ==========================================
                 UPDATE: Hiển thị tài khoản hiện tại
                 ========================================== --%>
            <c:if test="${isEdit}">

                <div class="col-md-6">
                    <label class="form-label">
                        Tên đăng nhập
                    </label>

                    <input type="text"
                           class="form-control"
                           value="${student.username}"
                           readonly>
                </div>

                <div class="col-md-6">
                    <label class="form-label">
                        Họ và tên
                    </label>

                    <input type="text"
                           class="form-control"
                           value="${student.fullName}"
                           readonly>
                </div>

                <div class="col-md-6">
                    <label class="form-label">
                        Email
                    </label>

                    <input type="email"
                           class="form-control"
                           value="${student.email}"
                           readonly>
                </div>

                <div class="col-md-6">
                    <label class="form-label">
                        Số điện thoại
                    </label>

                    <input type="text"
                           class="form-control"
                           value="${student.phone}"
                           readonly>
                </div>

                <div class="col-12">
                    <div class="form-text">
                        Thông tin tài khoản được quản lý riêng,
                        không thay đổi tại form học sinh.
                    </div>
                </div>

            </c:if>

            <%-- Mã học sinh --%>
            <div class="col-md-6">

                <label class="form-label"
                       for="studentCode">
                    Mã học sinh
                </label>

                <input type="text"
                       class="form-control"
                       id="studentCode"
                       name="studentCode"
                       required
                       value="${student.studentCode}">

            </div>

            <%-- Chuyên ngành --%>
            <div class="col-md-6">

                <label class="form-label"
                       for="majorId">
                    Chuyên ngành
                </label>

                <select class="form-select"
                        id="majorId"
                        name="majorId"
                        required>

                    <option value="">
                        -- Chọn chuyên ngành --
                    </option>

                    <c:forEach var="m"
                               items="${majors}">

                        <option value="${m.id}"
                                ${student.majorId == m.id
                                  ? 'selected' : ''}>

                            <c:out value="${m.majorCode}"/>
                            -
                            <c:out value="${m.majorName}"/>
                        </option>

                    </c:forEach>
                </select>

            </div>

            <%-- Lớp --%>
            <div class="col-md-6">

                <label class="form-label"
                       for="classId">
                    Lớp
                </label>

                <select class="form-select"
                        id="classId"
                        name="classId"
                        required>

                    <option value="">
                        -- Chọn lớp --
                    </option>

                    <c:forEach var="cl"
                               items="${classes}">

                        <option value="${cl.id}"
                                ${student.classId == cl.id
                                  ? 'selected' : ''}>

                            <c:out value="${cl.classCode}"/>
                            -
                            <c:out value="${cl.className}"/>
                        </option>

                    </c:forEach>
                </select>

                <div class="form-text">
                    Lớp phải thuộc chuyên ngành đã chọn.
                </div>

            </div>

            <%-- Trạng thái --%>
            <div class="col-md-6">

                <label class="form-label"
                       for="status">
                    Trạng thái
                </label>

                <select class="form-select"
                        id="status"
                        name="status"
                        required>

                    <option value="">
                        -- Chọn trạng thái --
                    </option>

                    <option value="Studying"
                            ${student.status == 'Studying'
                              ? 'selected' : ''}>
                        Đang học
                    </option>

                    <option value="Graduated"
                            ${student.status == 'Graduated'
                              ? 'selected' : ''}>
                        Đã tốt nghiệp
                    </option>

                    <option value="Dropped"
                            ${student.status == 'Dropped'
                              ? 'selected' : ''}>
                        Đã nghỉ học
                    </option>

                </select>

            </div>

            <%-- Địa chỉ --%>
            <div class="col-12">

                <label class="form-label"
                       for="address">
                    Địa chỉ
                </label>

                <input type="text"
                       class="form-control"
                       id="address"
                       name="address"
                       value="${student.address}">

            </div>

            <%-- Buttons --%>
            <div class="col-12 d-flex gap-2">

                <button type="submit"
                        class="btn btn-teal"
                        <c:if test="${not isEdit and empty availableUsers}">
                            disabled
                        </c:if>>

                    ${isEdit ? 'Cập nhật' : 'Thêm học sinh'}
                </button>

                <a class="btn btn-outline-secondary"
                   href="${pageContext.request.contextPath}/StudentServlet?action=list">
                    Hủy
                </a>

            </div>

        </form>
    </div>
</div>

<jsp:include page="/layout/footer.jsp"/>