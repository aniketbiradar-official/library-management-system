<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Issued Books</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/theme.css">

<script src="https://kit.fontawesome.com/a2e0e6adcf.js" crossorigin="anonymous"></script>
<script defer src="${pageContext.request.contextPath}/assets/js/theme.js"></script>
<script defer src="${pageContext.request.contextPath}/assets/js/ui.js"></script>

</head>
<body>
<div class="app-container">

	<h2>Issued Books</h2>

	<a href="${pageContext.request.contextPath}/books">Back to Books</a>
	<br/><br/>

	<table border="1" cellpadding="8">
    	<tr>
        	<th>Loan ID</th>
        	<th>Book</th>
        	<th>User</th>
        	<th>Issued On</th>
        	<th>Action</th>
    	</tr>

    	<c:forEach var="loan" items="${loans}">
        	<tr>
            	<td>${loan.id}</td>
            	<td>${loan.bookTitle}</td>
            	<td>${loan.username}</td>
            	<td>${loan.issueDate}</td>
            	<td>
                	<form action="${pageContext.request.contextPath}/loans/return" method="post">
					    <input type="hidden" name="loanId" value="${loan.id}" />
					    <button type="submit">Return</button>
					</form>

            	</td>
        	</tr>
    	</c:forEach>

	</table>
	
	</div>
</body>
</html>