<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Monthly Borrowing Trends</title>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<h2>Monthly Borrowing Trends</h2>

<c:if test="${empty reports}">
    <p>No borrowing data available.</p>
</c:if>

<c:if test="${not empty reports}">
    <table border="1" cellpadding="8">
        <tr>
            <th>Year</th>
            <th>Month</th>
            <th>Total Books Borrowed</th>
        </tr>

        <c:forEach var="r" items="${reports}">
            <tr>
                <td>${r.year}</td>
                <td>${r.month}</td>
                <td>${r.totalBorrowed}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<br/>
<a href="${pageContext.request.contextPath}/books">Back to Books</a>

</body>
</html>
