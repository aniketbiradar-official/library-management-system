<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Member Activity Report</title>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<h2>Member Activity — Top Borrowers</h2>

<c:if test="${empty reports}">
    <p>No borrowing activity found.</p>
</c:if>

<c:if test="${not empty reports}">

    <!-- ================= CHART ================= -->
    <canvas id="topBorrowersChart" width="600" height="220"></canvas>

    <br/><br/>

    <!-- ================= TABLE ================= -->
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

<!-- ================= CHART.JS ================= -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
const borrowerLabels = [
<c:forEach var="r" items="${reports}">
    "${r.username}",
</c:forEach>
];

const borrowCounts = [
<c:forEach var="r" items="${reports}">
    ${r.totalBorrowed},
</c:forEach>
];

new Chart(
    document.getElementById('topBorrowersChart'),
    {
        type: 'bar',
        data: {
            labels: borrowerLabels,
            datasets: [{
                label: 'Total Books Borrowed',
                data: borrowCounts,
                backgroundColor: '#36A2EB'
            }]
        },
        options: {
            responsive: false,   // 🔒 prevents resizing/scroll
            plugins: {
                legend: { display: true }
            },
            scales: {
                y: { beginAtZero: true }
            }
        }
    }
);
</script>

</body>
</html>
