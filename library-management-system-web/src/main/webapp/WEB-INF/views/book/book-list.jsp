<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book List</title>
<style>
    form { margin: 0; }
</style>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<h2>Library Books</h2>

<form method="get" action="${pageContext.request.contextPath}/books">
    <input type="text" name="q"
           placeholder="Search by title or author"
           value="${param.q}" />

    <select name="category">
    	<option value="">All Categories</option>

    	<c:forEach var="cat" items="${categories}">
	        <option value="${cat}"
	            <c:if test="${param.category == cat}">selected</c:if>>
	            ${cat}
	        </option>
	    </c:forEach>
	</select>


    <select name="availability">
        <option value="">All</option>
        <option value="available">Available</option>
        <option value="unavailable">Unavailable</option>
    </select>

    <button type="submit">Search</button>
</form>

<br/>


<c:if test="${sessionScope.user.role == 'ADMIN'}">
    <a href="${pageContext.request.contextPath}/books/add">Add New Book</a>
</c:if>

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
        <th>Actions</th>
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
            <td>

                <!-- ADMIN -->
                <c:if test="${sessionScope.user.role == 'ADMIN'}">
                    <a href="${pageContext.request.contextPath}/books/edit?id=${book.id}">Edit</a>
                    |
                    <a href="${pageContext.request.contextPath}/books/delete?id=${book.id}"
                       onclick="return confirm('Delete this book?');">Delete</a>
                </c:if>

                <!-- LIBRARIAN -->
                <c:if test="${sessionScope.user.role == 'LIBRARIAN' && book.availableCopies > 0}">
                    <form action="${pageContext.request.contextPath}/loans/issue"
                          method="post" style="display:inline;">
                        <input type="hidden" name="bookId" value="${book.id}" />
                        <input type="number" name="userId"
                               placeholder="Member ID" required />
                        <button type="submit">Issue</button>
                    </form>
                </c:if>

                <!-- MEMBER -->
                <c:if test="${sessionScope.user.role == 'MEMBER' && book.availableCopies > 0}">
                    <form action="${pageContext.request.contextPath}/loans/borrow"
                          method="post" style="display:inline;">
                        <input type="hidden" name="bookId" value="${book.id}" />
                        <button type="submit">Borrow</button>
                    </form>
                </c:if>

                <c:if test="${book.availableCopies == 0}">
                    <span style="color:red;">Not Available</span>
                </c:if>

            </td>
        </tr>
    </c:forEach>
</table>

<br/>

<c:if test="${totalPages > 1}">
    <div>
        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <strong>[${i}]</strong>
                </c:when>
                <c:otherwise>
                    <a href="?page=${i}&q=${param.q}&category=${param.category}&availability=${param.availability}">
                        ${i}
                    </a>
                </c:otherwise>
            </c:choose>
            &nbsp;
        </c:forEach>
    </div>
</c:if>


</body>
</html>
