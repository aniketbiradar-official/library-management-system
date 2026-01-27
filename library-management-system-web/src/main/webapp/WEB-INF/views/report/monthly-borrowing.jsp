<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Monthly Borrowing Trends</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<h2>Monthly Borrowing Trends</h2>

<c:if test="${empty reports}">
    <p>No borrowing data available.</p>
</c:if>

<c:if test="${not empty reports}">
    <canvas id="monthlyChart" width="800" height="400"></canvas>

    <script>
        const labels = [
            <c:forEach var="r" items="${reports}" varStatus="status">
                "${r.year}-${r.month}"
                <c:if test="${!status.last}">,</c:if>
            </c:forEach>
        ];

        const dataValues = [
            <c:forEach var="r" items="${reports}" varStatus="status">
                ${r.totalBorrowed}
                <c:if test="${!status.last}">,</c:if>
            </c:forEach>
        ];

        const ctx = document.getElementById('monthlyChart').getContext('2d');

        new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Books Borrowed Per Month',
                    data: dataValues,
                    borderWidth: 2,
                    tension: 0.3,
                    fill: false
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    </script>
</c:if>

<br/>
<a href="${pageContext.request.contextPath}/books">Back to Books</a>

</body>
</html>
