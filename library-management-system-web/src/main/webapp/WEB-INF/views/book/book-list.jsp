<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Library Books</title>
    <jsp:include page="/WEB-INF/views/common/head.jsp"/>
    
</head>
<body>

<div class="app-container">


<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<h2>Library Books</h2>

<!-- ================= ADMIN : ADD BOOK ================= -->
<c:if test="${sessionScope.user.role == 'ADMIN'}">
    <a href="${pageContext.request.contextPath}/books/add">
        ➕ Add New Book
    </a>
    <br/><br/>
</c:if>

<!-- ================= SEARCH & FILTER ================= -->
<form method="get" action="${pageContext.request.contextPath}/books">

    <input type="text" name="q"
           placeholder="Search by title or author"
           value="${q}" />

    <select name="category">
        <option value="">All Categories</option>
        <c:forEach var="cat" items="${categories}">
            <option value="${cat}" ${cat == category ? "selected" : ""}>
                ${cat}
            </option>
        </c:forEach>
    </select>

    <select name="availability">
        <option value="">All</option>
        <option value="available" ${availability == 'available' ? 'selected' : ''}>
            Available
        </option>
        <option value="unavailable" ${availability == 'unavailable' ? 'selected' : ''}>
            Unavailable
        </option>
    </select>

    <button type="submit">Search</button>
</form>

<br/>

<!-- ================= BOOK TABLE ================= -->
<table border="1" cellpadding="8">
    <tr>
        <th>ID</th>

        <th>
            Title
            <a href="?sort=title&order=asc&page=1&q=${q}&category=${category}&availability=${availability}">⬆</a>
            <a href="?sort=title&order=desc&page=1&q=${q}&category=${category}&availability=${availability}">⬇</a>
        </th>

        <th>
            Author
            <a href="?sort=author&order=asc&page=1&q=${q}&category=${category}&availability=${availability}">⬆</a>
            <a href="?sort=author&order=desc&page=1&q=${q}&category=${category}&availability=${availability}">⬇</a>
        </th>

        <th>ISBN</th>
        <th>Total</th>
        <th>Available</th>

        <th>
            Category
            <a href="?sort=category&order=asc&page=1&q=${q}&category=${category}&availability=${availability}">⬆</a>
            <a href="?sort=category&order=desc&page=1&q=${q}&category=${category}&availability=${availability}">⬇</a>
        </th>

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
                <!-- ================= ADMIN ================= -->
                <c:if test="${sessionScope.user.role == 'ADMIN'}">
                    <a href="${pageContext.request.contextPath}/books/edit?id=${book.id}">
                        Edit
                    </a>
                    |
                    <a href="${pageContext.request.contextPath}/books/delete?id=${book.id}"
                       onclick="return confirm('Delete this book?');">
                        Delete
                    </a>
                </c:if>

                <!-- ================= LIBRARIAN ================= -->
                <c:if test="${sessionScope.user.role == 'LIBRARIAN' && book.availableCopies > 0}">
                    <form action="${pageContext.request.contextPath}/loans/issue"
                          method="post"
                          style="display:inline;">
                        <input type="hidden" name="bookId" value="${book.id}" />
                        <input type="number" name="userId"
                               placeholder="Member ID"
                               required />
                        <button type="submit">Issue</button>
                    </form>
                </c:if>

                <!-- ================= MEMBER ================= -->
                <c:if test="${sessionScope.user.role == 'MEMBER' && book.availableCopies > 0}">
                    <form action="${pageContext.request.contextPath}/loans/borrow"
                          method="post"
                          style="display:inline;">
                        <input type="hidden" name="bookId" value="${book.id}" />
                        <button type="submit">Borrow</button>
                    </form>
                </c:if>

                <!-- ================= OUT OF STOCK ================= -->
                <c:if test="${book.availableCopies == 0}">
                    <span style="color:red;">Not Available</span>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>

<br/>

<!-- ================= PAGINATION ================= -->
<c:if test="${totalPages > 1}">
    <c:forEach begin="1" end="${totalPages}" var="i">
        <c:choose>
            <c:when test="${i == currentPage}">
                <strong>[${i}]</strong>
            </c:when>
            <c:otherwise>
                <a href="?page=${i}&q=${q}&category=${category}&availability=${availability}&sort=${sort}&order=${order}">
                    ${i}
                </a>
            </c:otherwise>
        </c:choose>
        &nbsp;
    </c:forEach>
</c:if>

</div>
</body>
</html>
