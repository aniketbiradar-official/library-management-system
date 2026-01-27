<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Member Activity Report</title>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<h2>Member Activity (Top Borrowers)</h2>

<c:if test="${empty reports}">
    <p>No borrowing activity found.</p>
</c:if>

<c:if test="${not empty reports}">
    <table border="1" cellpadding="8">
        <tr>
            <th>Member</th>
            <th>Total Books Borrowed</th>
        </tr>

        <c:forEach var="r" items="${reports}">
            <tr>
                <td>${r.username}</td>
                <td>${r.totalBorrowed}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<br/>
<a href="${pageContext.request.contextPath}/books">Back to Books</a>

</body>
</html>
