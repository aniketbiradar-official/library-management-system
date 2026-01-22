<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav style="margin-bottom:15px;">
    <a href="${pageContext.request.contextPath}/books">Books</a>

    <c:if test="${sessionScope.user.role != 'ADMIN'}">
        | <a href="${pageContext.request.contextPath}/loans">My Loans</a>
    </c:if>

    <c:if test="${sessionScope.user.role == 'ADMIN'}">
        | <span>Admin Panel</span>
    </c:if>

    | <a href="${pageContext.request.contextPath}/logout">Logout</a>
</nav>
<hr/>
    