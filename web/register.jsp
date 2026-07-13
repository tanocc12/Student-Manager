<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký | Student Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/auth.css">
</head>
<body class="auth-page d-flex align-items-center py-4">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-12 col-sm-10 col-md-7 col-lg-5">
                <div class="card auth-card">
                    <div class="auth-header">
                        <div class="logo">S</div>
                        <h1>Student Manager</h1>
                        <p>Tạo tài khoản mới</p>
                    </div>

                    <div class="card-body p-4">
                        <% if (request.getAttribute("error") != null) { %>
                        <div class="alert alert-danger" role="alert">
                            <%= request.getAttribute("error") %>
                        </div>
                        <% } %>

                        <% if (request.getAttribute("success") != null) { %>
                        <div class="alert alert-success" role="status">
                            <%= request.getAttribute("success") %>
                        </div>
                        <% } %>

                        <form id="registerForm"
                              action="${pageContext.request.contextPath}/RegisterServlet" method="post">
                            <div class="mb-3">
                                <label for="fullName" class="form-label">Họ và tên</label>
                                <input type="text" class="form-control" id="fullName" name="fullName"
                                       placeholder="Nguyễn Văn A" required
                                       value="${param.fullName}">
                            </div>

                            <div class="mb-3">
                                <label for="username" class="form-label">Tên đăng nhập</label>
                                <input type="text" class="form-control" id="username" name="username"
                                       placeholder="nguyenvana" required
                                       value="${param.username}">
                            </div>

                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email"
                                       placeholder="email@example.com" required
                                       value="${param.email}">
                            </div>

                            <div class="mb-3">
                                <label for="role" class="form-label">Vai trò</label>
                                <select class="form-select" id="role" name="role" required>
                                    <option value="">-- Chọn vai trò --</option>
                                    <option value="student" ${param.role == 'student' ? 'selected' : ''}>Học sinh</option>
                                    <option value="teacher" ${param.role == 'teacher' ? 'selected' : ''}>Giáo viên</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="password" class="form-label">Mật khẩu</label>
                                <div class="input-group">
                                    <input type="password" class="form-control" id="password" name="password"
                                           placeholder="Ít nhất 6 ký tự" minlength="6" required>
                                    <button type="button" class="btn btn-outline-secondary"
                                            data-password-toggle="password">Hiện</button>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="confirmPassword" class="form-label">Xác nhận mật khẩu</label>
                                <div class="input-group">
                                    <input type="password" class="form-control" id="confirmPassword"
                                           name="confirmPassword" placeholder="Nhập lại mật khẩu"
                                           minlength="6" required>
                                    <button type="button" class="btn btn-outline-secondary"
                                            data-password-toggle="confirmPassword">Hiện</button>
                                </div>
                                <div class="invalid-feedback d-block" data-confirm-hint></div>
                            </div>

                            <button type="submit" class="btn btn-auth text-white w-100 py-2">Đăng ký</button>
                        </form>

                        <p class="text-center text-muted mt-3 mb-0">
                            Đã có tài khoản?
                            <a class="auth-link" href="${pageContext.request.contextPath}/login.jsp">Đăng nhập</a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/auth.js"></script>
</body>
</html>
