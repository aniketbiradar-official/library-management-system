<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Member Registration</title>
</head>
<body>
	<h2>Member Registration</h2>

<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/register">

    <label>Username</label><br/>
    <input type="text" name="username" required />
    <br/><br/>

    <label>Password</label><br/>
    <input type="password" name="password" required />
    <br/><br/>

    <button type="submit">Register</button>
</form>

<br/>
<a href="${pageContext.request.contextPath}/login">Back to Login</a>
</body>
</html>