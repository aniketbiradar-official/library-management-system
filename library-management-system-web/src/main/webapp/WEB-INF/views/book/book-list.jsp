<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book List</title>
</head>
<body>
	<h2>Library Books</h2>
	
	<a href="${pageContext.request.contextPath}/books/add">Add New Book</a>
	<br/><br/>
	
	<table border="1" cellpadding="8">
		<tr>
			<th>ID</th>
        	<th>Title</th>
        	<th>Author</th>
        	<th>ISBN</th>
        	<th>Total</th>
        	<th>Available</th>
        	<th>Category</th>
        </tr>
        
        <c:forEach var="book" items="${books}">
        	<tr>
        		<td>${book.id}</td>
            	<td>${book.title}</td>
            	<td>${book.author}</td>
            	<td>${book.isbn}</td>
            	<td>${book.totalCopies}</td>
            	<td>${book.availableCopies}</td>
            	<td>${book.category}</td>
        	</tr>
        </c:forEach>
        
       </table>
	
</body>
</html>