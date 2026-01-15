<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Book</title>

</head>
<body>
	<h2>Add New Book</h2>
	
	<c:if test="${not empty error}">
    	<p style="color:red">${error}</p>
	</c:if>
	
	
	<form method="post" action="${pageContext.request.contextPath}/books/add">
		
		<label>Title:</label>
		<input type="text" name="title" required /> <br/> <br/>
		
		<label>Author:</label>
		<input type="text" name="author" required /> <br/> <br/>
		
		<label>ISBN:</label>
		<input type="text" name="isbn" required /> <br/> <br/>
		
		<label>Total Copies:</label>
		<input type="number" name="totalCopies" required /> <br/> <br/>
		
		<label>Category:</label>
		<input type="text" name="category" required /> <br/> <br/>
		
		<button type="submit">Add Book</button>
		
	</form>
	
	<br/>
	
	<a href="${pageContext.request.contextPath}/books">Back to Book List</a>
	
	
</body>
</html>