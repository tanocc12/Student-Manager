<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập | Student Manager</title>
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
                        <p>Hệ thống quản lý học sinh</p>
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

                        <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
                            <div class="mb-3">
                                <label for="username" class="form-label">Tên đăng nhập</label>
                                <input type="text" class="form-control" id="username" name="username"
                                       placeholder="Nhập tên đăng nhập" required
                                       value="${param.username}">
                            </div>

                            <div class="mb-3">
                                <label for="password" class="form-label">Mật khẩu</label>
                                <div class="input-group">
                                    <input type="password" class="form-control" id="password" name="password"
                                           placeholder="Nhập mật khẩu" required>
                                    <button type="button" class="btn btn-outline-secondary"
                                            data-password-toggle="password">Hiện</button>
                                </div>
                            </div>

                            <button type="submit" class="btn btn-auth text-white w-100 py-2">Đăng nhập</button>
                        </form>

                        <p class="text-center text-muted mt-3 mb-0">
                            Chưa có tài khoản?
                            <a class="auth-link" href="${pageContext.request.contextPath}/register.jsp">Đăng ký ngay</a>
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
