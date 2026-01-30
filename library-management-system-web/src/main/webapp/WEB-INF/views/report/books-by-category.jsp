<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Books by Category</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/layout.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/theme.css">

    <script src="https://kit.fontawesome.com/a2e0e6adcf.js" crossorigin="anonymous"></script>
    <script defer src="${pageContext.request.contextPath}/assets/js/theme.js"></script>
    <script defer src="${pageContext.request.contextPath}/assets/js/ui.js"></script>

    <!-- Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

<div class="app-container">

    <jsp:include page="/WEB-INF/views/common/header.jsp"/>

    <h2>Books by Category</h2>

    <c:if test="${empty reports}">
        <p>No category data available.</p>
    </c:if>

    <c:if test="${not empty reports}">

        <!-- ================= CHART CARD ================= -->
        <div class="section-card">
            <h3>Library Distribution</h3>

            <!-- Medium-size pie chart container -->
            <div style="max-width: 420px; height: 320px; margin: 0 auto;">
                <canvas id="categoryChart"></canvas>
            </div>
        </div>

        <!-- ================= TABLE CARD ================= -->
        <div class="section-card">
            <h3>Category Breakdown</h3>

            <table>
                <thead>
                    <tr>
                        <th>Category</th>
                        <th>Total Books</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="r" items="${reports}">
                        <tr>
                            <td>${r.category}</td>
                            <td>${r.count}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </c:if>

    <div class="page-actions">
        <a href="${pageContext.request.contextPath}/books" class="primary-btn">
            Back to Books
        </a>
    </div>

</div>

<!-- ================= CHART INITIALIZATION ================= -->
<script>
document.addEventListener("DOMContentLoaded", function () {

    <c:if test="${not empty reports}">
    const labels = [
        <c:forEach var="r" items="${reports}" varStatus="s">
            "${r.category}"<c:if test="${!s.last}">,</c:if>
        </c:forEach>
    ];

    const dataValues = [
        <c:forEach var="r" items="${reports}" varStatus="s">
            ${r.count}<c:if test="${!s.last}">,</c:if>
        </c:forEach>
    ];

    new Chart(document.getElementById("categoryChart"), {
        type: "pie",
        data: {
            labels: labels,
            datasets: [{
                data: dataValues,
                backgroundColor: [
                    "#7c83ff",
                    "#5eead4",
                    "#ff6384",
                    "#ffcd56",
                    "#4bc0c0",
                    "#9966ff"
                ],
                hoverOffset: 12
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false, // 🔑 critical
            animation: {
                duration: 700,
                easing: "easeOutQuart"
            },
            plugins: {
                legend: {
                    position: "bottom",
                    labels: {
                        color: "#94a3b8",
                        padding: 16
                    }
                },
                tooltip: {
                    enabled: true,
                    backgroundColor: "#0f172a",
                    titleColor: "#ffffff",
                    bodyColor: "#e5e7eb",
                    padding: 10,
                    cornerRadius: 8
                }
            }
        }
    });
    </c:if>

});
</script>

</body>
</html>
