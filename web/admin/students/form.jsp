<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/layout/header.jsp">
    <jsp:param name="pageTitle"
               value="Form học sinh | Student Manager"/>
</jsp:include>

<c:set var="isEdit" value="${not empty student}"/>

<h2 class="page-title">
    ${isEdit ? 'Sửa học sinh' : 'Thêm học sinh'}
</h2>

<p class="page-subtitle">
    Nhập đầy đủ thông tin học sinh.
</p>

<c:if test="${not empty error}">
    <div class="alert alert-danger">
        ${error}
    </div>
</c:if>

<div class="card table-card">
    <div class="card-body">

        <form method="post"
              action="${pageContext.request.contextPath}/StudentServlet"
              class="row g-3">

            <input type="hidden"
                   name="action"
                   value="${isEdit ? 'update' : 'insert'}">

            <c:if test="${isEdit}">
                <input type="hidden"
                       name="id"
                       value="${student.id}">
            </c:if>

            <!-- Mã học sinh -->
            <div class="col-md-6">
                <label class="form-label" for="studentCode">
                    Mã học sinh
                </label>

                <input type="text"
                       class="form-control"
                       id="studentCode"
                       name="studentCode"
                       required
                       value="${student.studentCode}">
            </div>

            <!-- Username -->
            <div class="col-md-6">
                <label class="form-label" for="username">
                    Tên đăng nhập
                </label>

                <input type="text"
                       class="form-control"
                       id="username"
                       name="username"
                       required
                       value="${student.username}">
            </div>

            <!-- Họ tên -->
            <div class="col-md-6">
                <label class="form-label" for="fullName">
                    Họ và tên
                </label>

                <input type="text"
                       class="form-control"
                       id="fullName"
                       name="fullName"
                       required
                       value="${student.fullName}">
            </div>

            <!-- Email -->
            <div class="col-md-6">
                <label class="form-label" for="email">
                    Email
                </label>

                <input type="email"
                       class="form-control"
                       id="email"
                       name="email"
                       required
                       value="${student.email}">
            </div>

            <!-- Giới tính -->
            <div class="col-md-6">
                <label class="form-label" for="gender">
                    Giới tính
                </label>

                <select class="form-select"
                        id="gender"
                        name="gender">

                    <option value="">-- Chọn giới tính --</option>

                    <option value="Male"
                            ${student.gender == 'Male' ? 'selected' : ''}>
                        Nam
                    </option>

                    <option value="Female"
                            ${student.gender == 'Female' ? 'selected' : ''}>
                        Nữ
                    </option>
                </select>
            </div>

            <!-- Ngày sinh -->
            <div class="col-md-6">
                <label class="form-label" for="dob">
                    Ngày sinh
                </label>

                <input type="date"
                       class="form-control"
                       id="dob"
                       name="dob"
                       value="${student.dob}">
            </div>

            <!-- Số điện thoại -->
            <div class="col-md-6">
                <label class="form-label" for="phone">
                    Số điện thoại
                </label>

                <input type="text"
                       class="form-control"
                       id="phone"
                       name="phone"
                       value="${student.phone}">
            </div>

            <!-- Lớp -->
            <div class="col-md-6">
                <label class="form-label" for="classId">
                    Lớp
                </label>

                <select class="form-select"
                        id="classId"
                        name="classId"
                        required>

                    <option value="">-- Chọn lớp --</option>

                    <c:forEach var="cl" items="${classes}">
                        <option value="${cl.id}"
                                ${student.classId == cl.id
                                  ? 'selected' : ''}>

                            ${cl.classCode} - ${cl.className}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <!-- Chuyên ngành -->
            <div class="col-md-6">
                <label class="form-label" for="majorId">
                    Chuyên ngành
                </label>

                <select class="form-select"
                        id="majorId"
                        name="majorId"
                        required>

                    <option value="">
                        -- Chọn chuyên ngành --
                    </option>

                    <c:forEach var="m" items="${majors}">
                        <option value="${m.id}"
                                ${student.majorId == m.id
                                  ? 'selected' : ''}>

                            ${m.majorCode} - ${m.majorName}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <!-- Địa chỉ -->
            <div class="col-md-6">
                <label class="form-label" for="address">
                    Địa chỉ
                </label>

                <input type="text"
                       class="form-control"
                       id="address"
                       name="address"
                       value="${student.address}">
            </div>

            <!-- Trạng thái -->
            <div class="col-md-6">
                <label class="form-label" for="status">
                    Trạng thái
                </label>

                <select class="form-select"
                        id="status"
                        name="status"
                        required>

                    <option value="">-- Chọn trạng thái --</option>

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

            <!-- Mật khẩu -->
            <div class="col-md-6">
                <label class="form-label" for="password">
                    ${isEdit ? 'Mật khẩu mới' : 'Mật khẩu'}
                </label>

                <input type="password"
                       class="form-control"
                       id="password"
                       name="password"
                       ${isEdit ? '' : 'required'}>

                <c:if test="${isEdit}">
                    <div class="form-text">
                        Để trống nếu không muốn đổi mật khẩu.
                    </div>
                </c:if>
            </div>

            <!-- Xác nhận mật khẩu -->
            <div class="col-md-6">
                <label class="form-label" for="confirmPassword">
                    Xác nhận mật khẩu
                </label>

                <input type="password"
                       class="form-control"
                       id="confirmPassword"
                       name="confirmPassword"
                       ${isEdit ? '' : 'required'}>
            </div>

            <!-- Buttons -->
            <div class="col-12 d-flex gap-2">

                <button type="submit"
                        class="btn btn-teal">
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