<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Currently Issued Books</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/layout.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/theme.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">

    <script src="https://kit.fontawesome.com/a2e0e6adcf.js" crossorigin="anonymous"></script>
    <script defer src="${pageContext.request.contextPath}/assets/js/theme.js"></script>
    <script defer src="${pageContext.request.contextPath}/assets/js/ui.js"></script>

    <!-- Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body>
<div class="app-container">

    <jsp:include page="/WEB-INF/views/common/header.jsp"/>

    <h2>Currently Issued Books</h2>

    <c:if test="${empty reports}">
        <div class="empty-state">No books are currently issued.</div>
    </c:if>

    <c:if test="${not empty reports}">

        <!-- ================= CHART CARD ================= -->
        <div class="chart-card">
            <div class="chart-title">Active Issues per Book</div>

            <!-- ❌ NO width / height attributes -->
            <canvas id="issuedBooksChart"></canvas>
        </div>

        <!-- ================= TABLE ================= -->
        <div class="table-card mt-4">
            <table>
                <thead>
                    <tr>
                        <th>Book Title</th>
                        <th>Issued To</th>
                        <th>Issued On</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="r" items="${reports}">
                        <tr>
                            <td>${r.bookTitle}</td>
                            <td>${r.username}</td>
                            <td>${r.issueDate}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </c:if>

    <div class="page-actions">
        <a href="${pageContext.request.contextPath}/books" class="primary-btn">
            ← Back to Books
        </a>
    </div>

</div>

<!-- ================= CHART INITIALIZATION ================= -->
<script>
document.addEventListener("DOMContentLoaded", function () {

    /*
     Each row = one active issue
     We render one bar per issued copy (simple & accurate)
    */

    const labels = [
        <c:forEach var="r" items="${reports}" varStatus="s">
            "${r.bookTitle}"<c:if test="${!s.last}">,</c:if>
        </c:forEach>
    ];

    const values = [
        <c:forEach var="r" items="${reports}" varStatus="s">
            1<c:if test="${!s.last}">,</c:if>
        </c:forEach>
    ];

    new Chart(document.getElementById("issuedBooksChart"), {
        type: "bar",
        data: {
            labels: labels,
            datasets: [{
                label: "Issued Copies",
                data: values,
                backgroundColor: "rgba(255, 159, 64, 0.85)",
                hoverBackgroundColor: "rgba(94, 234, 212, 0.95)",
                borderRadius: 8
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,

            animation: {
                duration: 900,
                easing: "easeOutQuart"
            },

            interaction: {
                mode: "index",
                intersect: false
            },

            plugins: {
                legend: { display: false },
                tooltip: {
                    backgroundColor: "#0f172a",
                    titleColor: "#fff",
                    bodyColor: "#e5e7eb",
                    padding: 12,
                    cornerRadius: 8
                }
            },

            scales: {
                x: {
                    ticks: {
                        color: "#94a3b8",
                        maxRotation: 25,
                        minRotation: 15
                    },
                    grid: { display: false }
                },
                y: {
                    ticks: {
                        color: "#94a3b8",
                        stepSize: 1,
                        precision: 0
                    },
                    grid: {
                        color: "rgba(255,255,255,0.05)"
                    }
                }
            }
        }
    });
});
</script>

</body>
</html>
