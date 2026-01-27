<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Currently Issued Books</title>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<h2>Currently Issued Books</h2>

<c:if test="${empty reports}">
    <p>No books are currently issued.</p>
</c:if>

<c:if test="${not empty reports}">

    <!-- ================= CHART ================= -->
    <h3>Currently Issued Books (Chart)</h3>
    <canvas id="issuedBooksChart" width="600" height="220"></canvas>

    <br/>

    <!-- ================= TABLE ================= -->
    <table border="1" cellpadding="8">
        <tr>
            <th>Book Title</th>
            <th>Issued To</th>
            <th>Issued On</th>
        </tr>

        <c:forEach var="r" items="${reports}">
            <tr>
                <td>${r.bookTitle}</td>
                <td>${r.username}</td>
                <td>${r.issueDate}</td>
            </tr>
        </c:forEach>
    </table>

</c:if>

<br/>
<a href="${pageContext.request.contextPath}/books">Back to Books</a>

<!-- ================= CHART.JS ================= -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
/*
 Each row in reports = one active issue.
 We visualize count per book by rendering one bar per row.
 Simple, accurate, and no extra SQL required.
*/

const issuedLabels = [
<c:forEach var="r" items="${reports}">
    "${r.bookTitle}",
</c:forEach>
];

const issuedData = [
<c:forEach var="r" items="${reports}">
    1,
</c:forEach>
];

new Chart(
    document.getElementById("issuedBooksChart"),
    {
        type: "bar",
        data: {
            labels: issuedLabels,
            datasets: [{
                label: "Issued Copies",
                data: issuedData,
                backgroundColor: "#FF9F40"
            }]
        },
        options: {
            responsive: false,
            plugins: {
                legend: { display: false }
            }
        }
    }
);
</script>

</body>
</html>
