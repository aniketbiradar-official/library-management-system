<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<h2>Login</h2>

	<c:if test="${not empty error}">
    	<p style="color:red">${error}</p>
	</c:if>

	<form method="post" action="${pageContext.request.contextPath}/login">
    	<input type="text" name="username" placeholder="Username" required />
    	<br/><br/>
    	<input type="password" name="password" placeholder="Password" required />
    	<br/><br/>
    	<button type="submit">Login</button>
	</form>
	<br/>
<a href="${pageContext.request.contextPath}/register">
    New member? Register here
</a>
	
</body>
</html>