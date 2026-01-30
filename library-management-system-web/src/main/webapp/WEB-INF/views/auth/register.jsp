<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Member Registration</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/theme.css">

<script src="https://kit.fontawesome.com/a2e0e6adcf.js" crossorigin="anonymous"></script>
<script defer src="${pageContext.request.contextPath}/assets/js/theme.js"></script>
</head>

<body>

<div class="auth-page">
    <div class="auth-card glass">

        <h2 class="text-center">
            <i class="fa-solid fa-user-plus"></i> Register
        </h2>

        <c:if test="${not empty error}">
            <p class="error-text text-center mb-2">${error}</p>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/register">

            <input type="text"
                   name="username"
                   placeholder="Username"
                   required />

            <input type="password"
                   name="password"
                   placeholder="Password"
                   required />

            <button type="submit" class="primary-btn">
                Register
            </button>
        </form>

        <!-- Secondary Action -->
        <div class="mt-3 text-center">
            <a href="${pageContext.request.contextPath}/login"
               class="primary-btn"
               style="background: transparent; box-shadow:none; color: var(--accent-secondary);">
                Back to Login
            </a>
        </div>

    </div>
</div>

</body>
</html>
