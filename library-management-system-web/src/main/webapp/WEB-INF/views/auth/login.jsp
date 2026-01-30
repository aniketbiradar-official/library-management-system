<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/theme.css">

<script src="https://kit.fontawesome.com/a2e0e6adcf.js" crossorigin="anonymous"></script>
<script defer src="${pageContext.request.contextPath}/assets/js/theme.js"></script>
</head>

<body>

<div class="auth-page">
    <div class="auth-card glass login-card">

        <h2 class="text-center login-title">
            <i class="fa-solid fa-lock"></i>
            Login
        </h2>

        <p class="login-subtitle">
            Sign in to access LibraryMS
        </p>

        <c:if test="${not empty error}">
            <p class="error-text text-center mb-2">${error}</p>
        </c:if>

        <form method="post"
              action="${pageContext.request.contextPath}/login"
              class="login-form">

            <div class="login-field">
                <i class="fa-solid fa-user"></i>
                <input type="text"
                       name="username"
                       placeholder="Username"
                       required />
            </div>

            <div class="login-field">
                <i class="fa-solid fa-lock"></i>
                <input type="password"
                       name="password"
                       placeholder="Password"
                       required />
            </div>

            <button type="submit" class="primary-btn login-btn">
                Login
            </button>
        </form>

        <!-- Secondary Action -->
        <div class="login-footer">
            <a href="${pageContext.request.contextPath}/register">
                New member? Register
            </a>
        </div>

    </div>
</div>

</body>
</html>
