<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Monthly Borrowing Trends</title>

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

    <h2>Monthly Borrowing Trends</h2>

    <c:if test="${empty reports}">
        <p>No borrowing data available.</p>
    </c:if>

    <c:if test="${not empty reports}">

        <!-- ================= CHART CARD ================= -->
        <div class="section-card">
            <h3>Borrowing Over Time</h3>

            <!-- Size controlled here -->
            <div style="max-width: 700px; height: 320px; margin: 0 auto;">
                <canvas id="monthlyChart"></canvas>
            </div>
        </div>

        <!-- ================= TABLE CARD ================= -->
        <div class="section-card">
            <h3>Monthly Breakdown</h3>

            <table>
                <thead>
                    <tr>
                        <th>Year</th>
                        <th>Month</th>
                        <th>Total Borrowed</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="r" items="${reports}">
                        <tr>
                            <td>${r.year}</td>
                            <td>${r.month}</td>
                            <td>${r.totalBorrowed}</td>
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
            "${r.year}-${r.month}"<c:if test="${!s.last}">,</c:if>
        </c:forEach>
    ];

    const dataValues = [
        <c:forEach var="r" items="${reports}" varStatus="s">
            ${r.totalBorrowed}<c:if test="${!s.last}">,</c:if>
        </c:forEach>
    ];

    new Chart(document.getElementById("monthlyChart"), {
        type: "line",
        data: {
            labels: labels,
            datasets: [{
                label: "Books Borrowed",
                data: dataValues,
                borderColor: "rgba(124, 131, 255, 0.95)",
                backgroundColor: "rgba(124, 131, 255, 0.15)",
                tension: 0.35,
                fill: true,
                pointRadius: 5,
                pointHoverRadius: 7
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false, // 🔑 critical
            animation: {
                duration: 800,
                easing: "easeOutQuart"
            },
            plugins: {
                legend: {
                    display: true
                },
                tooltip: {
                    enabled: true,
                    backgroundColor: "#0f172a",
                    titleColor: "#ffffff",
                    bodyColor: "#e5e7eb",
                    padding: 10,
                    cornerRadius: 8
                }
            },
            scales: {
                x: {
                    ticks: {
                        color: "#94a3b8"
                    },
                    grid: {
                        display: false
                    }
                },
                y: {
                    beginAtZero: true,
                    ticks: {
                        color: "#94a3b8"
                    },
                    grid: {
                        color: "rgba(255,255,255,0.06)"
                    }
                }
            }
        }
    });
    </c:if>

});
</script>

</body>
</html>
