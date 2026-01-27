<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Overdue Books</title>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<h2>Overdue Books</h2>

<c:if test="${empty reports}">
    <p>No overdue books 🎉</p>
</c:if>

<c:if test="${not empty reports}">

    <!-- ================= CHART ================= -->
    <h3>Overdue Duration (Days)</h3>
    <canvas id="overdueBooksChart" width="650" height="240"></canvas>

    <br/>

    <!-- ================= TABLE ================= -->
    <table border="1" cellpadding="8">
        <tr>
            <th>Book Title</th>
            <th>Issued To</th>
            <th>Issued On</th>
            <th>Overdue (Days)</th>
        </tr>

        <c:forEach var="r" items="${reports}">
            <tr>
                <td>${r.bookTitle}</td>
                <td>${r.username}</td>
                <td>${r.issueDate}</td>
                <td style="color:red;font-weight:bold;">
                    ${r.overdueDays}
                </td>
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
 Each bar = one overdue loan
 Height = number of overdue days
*/

const overdueLabels = [
<c:forEach var="r" items="${reports}">
    "${r.bookTitle} (${r.username})",
</c:forEach>
];

const overdueData = [
<c:forEach var="r" items="${reports}">
    ${r.overdueDays},
</c:forEach>
];

new Chart(
    document.getElementById("overdueBooksChart"),
    {
        type: "bar",
        data: {
            labels: overdueLabels,
            datasets: [{
                label: "Overdue Days",
                data: overdueData,
                backgroundColor: "#FF6384"
            }]
        },
        options: {
            responsive: false,
            plugins: {
                legend: { display: false }
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    }
);
</script>

</body>
</html>
