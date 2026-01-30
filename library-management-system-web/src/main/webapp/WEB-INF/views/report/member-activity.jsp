<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Member Activity Report</title>

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

    <h2>Member Activity — Top Borrowers</h2>

    <c:if test="${empty reports}">
        <p>No borrowing activity found.</p>
    </c:if>

    <c:if test="${not empty reports}">

        <!-- ================= CHART CARD ================= -->
        <div class="section-card">
            <h3>Top Borrowers (Graph)</h3>

            <!-- IMPORTANT: wrapper controls size -->
            <div style="max-width: 600px; height: 300px; margin: 0 auto;">
                <canvas id="topBorrowersChart"></canvas>
            </div>
        </div>

        <!-- ================= TABLE ================= -->
        <div class="section-card">
            <h3>Borrowing Details</h3>

            <table>
                <thead>
                    <tr>
                        <th>Member</th>
                        <th>Total Books Borrowed</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="r" items="${reports}">
                        <tr>
                            <td>${r.username}</td>
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
            "${r.username}"<c:if test="${!s.last}">,</c:if>
        </c:forEach>
    ];

    const data = [
        <c:forEach var="r" items="${reports}" varStatus="s">
            ${r.totalBorrowed}<c:if test="${!s.last}">,</c:if>
        </c:forEach>
    ];

    new Chart(document.getElementById("topBorrowersChart"), {
        type: "bar",
        data: {
            labels: labels,
            datasets: [{
                label: "Total Books Borrowed",
                data: data,
                backgroundColor: "rgba(124, 131, 255, 0.85)",
                hoverBackgroundColor: "rgba(94, 234, 212, 0.95)",
                borderRadius: 8,
                barThickness: 36
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false, // 🔑 critical fix
            animation: {
                duration: 700,
                easing: "easeOutQuart"
            },
            plugins: {
                legend: {
                    display: false
                },
                tooltip: {
                    enabled: true,
                    backgroundColor: "#0f172a",
                    titleColor: "#fff",
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
