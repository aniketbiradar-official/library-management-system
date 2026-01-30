<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Reports Dashboard</title>

    <!-- Core CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/layout.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/theme.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">

    <!-- Icons -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
          crossorigin="anonymous"/>

    <!-- Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <!-- UI Scripts -->
    <script defer src="${pageContext.request.contextPath}/assets/js/theme.js"></script>
    <script defer src="${pageContext.request.contextPath}/assets/js/ui.js"></script>
</head>

<body>

<div class="app-container">
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>

    <h2 class="mb-3">Reports Dashboard</h2>

    <!-- ================= MOST BORROWED ================= -->
    <c:if test="${not empty mostBorrowed}">
        <div class="section-card">
            <h3>Most Borrowed Books</h3>
            <div class="chart-wrapper">
                <canvas id="mostBorrowedChart"></canvas>
            </div>
        </div>
    </c:if>

    <!-- ================= CURRENTLY ISSUED ================= -->
    <c:if test="${not empty issuedBooks}">
        <div class="section-card">
            <h3>Currently Issued Books</h3>
            <div class="chart-wrapper">
                <canvas id="issuedBooksChart"></canvas>
            </div>
        </div>
    </c:if>

    <!-- ================= OVERDUE ================= -->
    <c:if test="${not empty overdueBooks}">
        <div class="section-card">
            <h3>Overdue Books</h3>
            <div class="chart-wrapper">
                <canvas id="overdueBooksChart"></canvas>
            </div>
        </div>
    </c:if>

    <!-- ================= TOP BORROWERS ================= -->
    <c:if test="${not empty topBorrowers}">
        <div class="section-card">
            <h3>Top Borrowers</h3>
            <div class="chart-wrapper">
                <canvas id="topBorrowersChart"></canvas>
            </div>
        </div>
    </c:if>

    <!-- ================= MONTHLY TRENDS ================= -->
    <c:if test="${not empty monthlyTrends}">
        <div class="section-card">
            <h3>Monthly Borrowing Trends</h3>
            <div class="chart-wrapper">
                <canvas id="monthlyTrendChart"></canvas>
            </div>
        </div>
    </c:if>

    <!-- ================= CATEGORY ================= -->
    <c:if test="${not empty booksByCategory}">
        <div class="section-card">
            <h3>Books by Category</h3>
            <div class="chart-wrapper">
                <canvas id="booksByCategoryChart"></canvas>
            </div>
        </div>
    </c:if>

</div>

<!-- ================================================= -->
<!-- CHART INITIALIZATION (FINAL & STABLE) -->
<!-- ================================================= -->
<script>
document.addEventListener("DOMContentLoaded", function () {

    const baseOptions = {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: { display: true }
        }
    };

    /* MOST BORROWED */
    <c:if test="${not empty mostBorrowed}">
    new Chart(document.getElementById("mostBorrowedChart"), {
        type: "bar",
        data: {
            labels: [
                <c:forEach var="r" items="${mostBorrowed}" varStatus="s">
                    "${r.bookTitle}"<c:if test="${!s.last}">,</c:if>
                </c:forEach>
            ],
            datasets: [{
                label: "Times Borrowed",
                data: [
                    <c:forEach var="r" items="${mostBorrowed}" varStatus="s">
                        ${r.borrowCount}<c:if test="${!s.last}">,</c:if>
                    </c:forEach>
                ],
                backgroundColor: "#6366f1"
            }]
        },
        options: baseOptions
    });
    </c:if>

    /* ISSUED BOOKS */
    <c:if test="${not empty issuedBooks}">
    new Chart(document.getElementById("issuedBooksChart"), {
        type: "bar",
        data: {
            labels: [
                <c:forEach var="r" items="${issuedBooks}" varStatus="s">
                    "${r.bookTitle}"<c:if test="${!s.last}">,</c:if>
                </c:forEach>
            ],
            datasets: [{
                label: "Issued Copies",
                data: [
                    <c:forEach var="r" items="${issuedBooks}" varStatus="s">
                        1<c:if test="${!s.last}">,</c:if>
                    </c:forEach>
                ],
                backgroundColor: "#22d3ee"
            }]
        },
        options: baseOptions
    });
    </c:if>

    /* OVERDUE */
    <c:if test="${not empty overdueBooks}">
    new Chart(document.getElementById("overdueBooksChart"), {
        type: "bar",
        data: {
            labels: [
                <c:forEach var="r" items="${overdueBooks}" varStatus="s">
                    "${r.bookTitle}"<c:if test="${!s.last}">,</c:if>
                </c:forEach>
            ],
            datasets: [{
                label: "Overdue Days",
                data: [
                    <c:forEach var="r" items="${overdueBooks}" varStatus="s">
                        ${r.overdueDays}<c:if test="${!s.last}">,</c:if>
                    </c:forEach>
                ],
                backgroundColor: "#ff6384"
            }]
        },
        options: baseOptions
    });
    </c:if>

    /* TOP BORROWERS */
    <c:if test="${not empty topBorrowers}">
    new Chart(document.getElementById("topBorrowersChart"), {
        type: "bar",
        data: {
            labels: [
                <c:forEach var="r" items="${topBorrowers}" varStatus="s">
                    "${r.username}"<c:if test="${!s.last}">,</c:if>
                </c:forEach>
            ],
            datasets: [{
                label: "Books Borrowed",
                data: [
                    <c:forEach var="r" items="${topBorrowers}" varStatus="s">
                        ${r.totalBorrowed}<c:if test="${!s.last}">,</c:if>
                    </c:forEach>
                ],
                backgroundColor: "#4bc0c0"
            }]
        },
        options: baseOptions
    });
    </c:if>

    /* MONTHLY TRENDS */
    <c:if test="${not empty monthlyTrends}">
    new Chart(document.getElementById("monthlyTrendChart"), {
        type: "line",
        data: {
            labels: [
                <c:forEach var="r" items="${monthlyTrends}" varStatus="s">
                    "${r.year}-${r.month}"<c:if test="${!s.last}">,</c:if>
                </c:forEach>
            ],
            datasets: [{
                label: "Books Borrowed",
                data: [
                    <c:forEach var="r" items="${monthlyTrends}" varStatus="s">
                        ${r.totalBorrowed}<c:if test="${!s.last}">,</c:if>
                    </c:forEach>
                ],
                borderColor: "#22d3ee",
                tension: 0.35,
                fill: false
            }]
        },
        options: baseOptions
    });
    </c:if>

    /* CATEGORY */
    <c:if test="${not empty booksByCategory}">
    new Chart(document.getElementById("booksByCategoryChart"), {
        type: "pie",
        data: {
            labels: [
                <c:forEach var="r" items="${booksByCategory}" varStatus="s">
                    "${r.category}"<c:if test="${!s.last}">,</c:if>
                </c:forEach>
            ],
            datasets: [{
                data: [
                    <c:forEach var="r" items="${booksByCategory}" varStatus="s">
                        ${r.count}<c:if test="${!s.last}">,</c:if>
                    </c:forEach>
                ],
                backgroundColor: [
                    "#6366f1","#22d3ee","#ff6384","#ffcd56","#4bc0c0"
                ]
            }]
        },
        options: baseOptions
    });
    </c:if>

});
</script>

</body>
</html>
