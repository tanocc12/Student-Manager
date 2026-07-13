<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.pageTitle != null ? param.pageTitle : 'Student Manager'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
</head>
<body class="app-body">
<nav class="navbar navbar-expand-lg navbar-dark navbar-sm">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/home.jsp">Student Manager</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav"
                aria-controls="mainNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="mainNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/home.jsp">Trang chủ</a>
                </li>

                <c:if test="${sessionScope.role == 'admin'}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/dashboard.jsp">Dashboard</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/students/list.jsp">Học sinh</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/courses/list.jsp">Khóa học</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/classes/list.jsp">Lớp học</a>
                    </li>
                </c:if>

                <c:if test="${sessionScope.role == 'teacher'}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/teacher/dashboard.jsp">Dashboard</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/teacher/classes.jsp">Lớp của tôi</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/teacher/grades.jsp">Nhập điểm</a>
                    </li>
                </c:if>

                <c:if test="${sessionScope.role == 'student'}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/student/dashboard.jsp">Dashboard</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/student/courses.jsp">Khóa học</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/student/grades.jsp">Điểm của tôi</a>
                    </li>
                </c:if>
            </ul>

            <ul class="navbar-nav ms-auto align-items-lg-center gap-lg-2">
                <c:choose>
                    <c:when test="${not empty sessionScope.username}">
                        <li class="nav-item">
                            <span class="navbar-text text-white-50 me-lg-2">
                                Xin chào, <strong class="text-white">${sessionScope.fullName != null ? sessionScope.fullName : sessionScope.username}</strong>
                                <span class="badge text-bg-light badge-role ms-1">${sessionScope.role}</span>
                            </span>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-sm btn-outline-light"
                               href="${pageContext.request.contextPath}/LogoutServlet">Đăng xuất</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/login.jsp">Đăng nhập</a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-sm btn-teal" href="${pageContext.request.contextPath}/register.jsp">Đăng ký</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
<main class="app-main">
<div class="container">
