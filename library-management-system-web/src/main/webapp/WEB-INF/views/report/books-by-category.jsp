<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Books by Category</title>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<h2>Books by Category</h2>

<c:if test="${empty reports}">
    <p>No category data available.</p>
</c:if>

<c:if test="${not empty reports}">

    <!-- ================= PIE CHART ================= -->
    <canvas id="categoryChart" width="420" height="220"></canvas>

    <br/><br/>

    <!-- ================= TABLE ================= -->
    <table border="1" cellpadding="8">
        <tr>
            <th>Category</th>
            <th>Total Books</th>
        </tr>

        <c:forEach var="r" items="${reports}">
            <tr>
                <td>${r.category}</td>
                <td>${r.count}</td>
            </tr>
        </c:forEach>
    </table>

</c:if>

<br/>
<a href="${pageContext.request.contextPath}/books">Back to Books</a>

<!-- ================= CHART.JS ================= -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
const labels = [
<c:forEach var="r" items="${reports}">
    "${r.category}",
</c:forEach>
];

const dataValues = [
<c:forEach var="r" items="${reports}">
    ${r.count},
</c:forEach>
];

new Chart(
    document.getElementById('categoryChart'),
    {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                data: dataValues,
                backgroundColor: [
                    '#36A2EB',
                    '#FF6384',
                    '#FF9F40',
                    '#FFCD56',
                    '#4BC0C0',
                    '#9966FF'
                ]
            }]
        },
        options: {
            responsive: false,   // 🔒 fixed size
            plugins: {
                legend: {
                    position: 'top'
                }
            }
        }
    }
);
</script>

</body>
</html>
