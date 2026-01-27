<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Most Borrowed Books</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>

	<h2>Most Borrowed Books</h2>
	
	<c:if test="${empty reports}">
	    <p>No borrowing data available.</p>
	</c:if>
	
	<c:if test="${not empty reports}">
	    <table border="1" cellpadding="8">
	        <tr>
	            <th>Book Title</th>
	            <th>Times Borrowed</th>
	        </tr>

	        <c:forEach var="r" items="${reports}">
	            <tr>
	                <td>${r.bookTitle}</td>
	                <td>${r.borrowCount}</td>
	            </tr>
	        </c:forEach>
	    </table>
	</c:if>
	
	<br/>
	<a href="${pageContext.request.contextPath}/books">Back to Books</a>
</body>
</html>