<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header class="top-nav">
    <div class="nav-left">
        <i class="fa-solid fa-book-open nav-logo"></i>
        <span class="app-title">LibraryMS</span>
    </div>

    <nav class="nav-links">
        <a href="${pageContext.request.contextPath}/books">
            <i class="fa-solid fa-books"></i> Books
        </a>

        <c:if test="${sessionScope.user.role != 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/loans">
                <i class="fa-solid fa-book-reader"></i> My Loans
            </a>
        </c:if>

        <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <span class="role-badge admin">
                <i class="fa-solid fa-user-shield"></i> Admin
            </span>
        </c:if>

        <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'LIBRARIAN'}">
            <div class="dropdown">
                <span class="dropdown-title">
                    <i class="fa-solid fa-chart-line"></i> Reports
                </span>
                <div class="dropdown-menu">
                    <a href="${pageContext.request.contextPath}/reports/dashboard">Dashboard</a>
                    <a href="${pageContext.request.contextPath}/reports/books">Most Borrowed</a>
                    <a href="${pageContext.request.contextPath}/reports/issued">Issued</a>
                    <a href="${pageContext.request.contextPath}/reports/overdue">Overdue</a>
                    <a href="${pageContext.request.contextPath}/reports/members">Members</a>
                    <a href="${pageContext.request.contextPath}/reports/monthly">Monthly</a>
                    <a href="${pageContext.request.contextPath}/reports/categories">Categories</a>
                </div>
            </div>
        </c:if>
    </nav>

    <div class="nav-right">
        <!-- 🌗 THEME TOGGLE -->
        <button id="themeToggle" class="icon-btn" title="Toggle theme">
            <i class="fa-solid fa-moon"></i>
        </button>

        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">
            <i class="fa-solid fa-right-from-bracket"></i>
        </a>
    </div>
</header>
