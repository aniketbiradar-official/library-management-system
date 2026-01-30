<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Book</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/theme.css">

<script src="https://kit.fontawesome.com/a2e0e6adcf.js" crossorigin="anonymous"></script>
<script defer src="${pageContext.request.contextPath}/assets/js/theme.js"></script>
<script defer src="${pageContext.request.contextPath}/assets/js/ui.js"></script>


</head>
<body>
<div class="app-container">

	<h2>Add New Book</h2>
	
	<c:if test="${not empty error}">
    	<p style="color:red">${error}</p>
	</c:if>
	
	
	<form method="post" action="${pageContext.request.contextPath}/books/add">
		
		<label>Title:</label>
		<input type="text" name="title" value="${title}" required /> <br/> <br/>
		
		<label>Author:</label>
		<input type="text" name="author" value="${author}" required /> <br/> <br/>
		
		<label>ISBN:</label>
		<input type="text" name="isbn" value="${isbn}" required /> <br/> <br/>
		
		<label>Total Copies:</label>
		<input type="number" name="totalCopies" value="${totalCopies}" required /> <br/> <br/>
		
		<label>Category:</label>
		<input type="text" name="category" value="${category}" required /> <br/> <br/>
		
		<button type="submit">Add Book</button>
		
	</form>
	
	<br/>
	
	<a href="${pageContext.request.contextPath}/books">Back to Book List</a>
	
	</div>
</body>
</html>