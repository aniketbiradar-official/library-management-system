<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Reports Dashboard</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<center><h2>Reports Dashboard</h2></center>
<hr/>

<!-- ================================================= -->
<!-- 1️⃣ MOST BORROWED BOOKS -->
<!-- ================================================= -->
<center><h3>Most Borrowed Books</h3></center>
<c:if test="${not empty mostBorrowed}">
<center>
<canvas id="mostBorrowedChart" width="700" height="260"></canvas>
</center>
</c:if>

<!-- ================================================= -->
<!-- 2️⃣ CURRENTLY ISSUED BOOKS -->
<!-- ================================================= -->
<center><h3>Currently Issued Books</h3></center>
<c:if test="${not empty issuedBooks}">
<center>
<canvas id="issuedBooksChart" width="700" height="260"></canvas>
</center>
</c:if>

<!-- ================================================= -->
<!-- 3️⃣ OVERDUE BOOKS -->
<!-- ================================================= -->
<center><h3>Overdue Books</h3></center>
<c:if test="${not empty overdueBooks}">
<center>
<canvas id="overdueBooksChart" width="700" height="260"></canvas>
</center>
</c:if>

<!-- ================================================= -->
<!-- 4️⃣ MEMBER ACTIVITY -->
<!-- ================================================= -->
<center><h3>Top Borrowers</h3></center>
<c:if test="${not empty topBorrowers}">
<center>
<canvas id="topBorrowersChart" width="700" height="260"></canvas>
</center>
</c:if>

<!-- ================================================= -->
<!-- 6️⃣ MONTHLY BORROWING -->
<!-- ================================================= -->
<center><h3>Monthly Borrowing Trends</h3></center>
<c:if test="${not empty monthlyTrends}">
<center>
<canvas id="monthlyTrendChart" width="800" height="300"></canvas>
</center>
</c:if>

<!-- ================================================= -->
<!-- 5️⃣ BOOKS BY CATEGORY -->
<!-- ================================================= -->
<center><h3>Books by Category</h3></center>
<c:if test="${not empty booksByCategory}">
<center>
<canvas id="booksByCategoryChart" width="450" height="300"></canvas>
</center>
</c:if>

<hr/>

<!-- ================================================= -->
<!-- JAVASCRIPT (NO TRAILING COMMAS – VERY IMPORTANT) -->
<!-- ================================================= -->

<script>
/* ========== MOST BORROWED BOOKS ========== */
<c:if test="${not empty mostBorrowed}">
new Chart(document.getElementById("mostBorrowedChart"), {
    type: 'bar',
    data: {
        labels: [
        <c:forEach var="r" items="${mostBorrowed}" varStatus="s">
            "${r.bookTitle}"<c:if test="${!s.last}">,</c:if>
        </c:forEach>
        ],
        datasets: [{
            label: 'Times Borrowed',
            data: [
            <c:forEach var="r" items="${mostBorrowed}" varStatus="s">
                ${r.borrowCount}<c:if test="${!s.last}">,</c:if>
            </c:forEach>
            ],
            backgroundColor: '#36A2EB'
        }]
    },
    options: { responsive: false }
});
</c:if>
</script>

<script>
/* ========== TOP BORROWERS ========== */
<c:if test="${not empty topBorrowers}">
new Chart(document.getElementById("topBorrowersChart"), {
    type: 'bar',
    data: {
        labels: [
        <c:forEach var="r" items="${topBorrowers}" varStatus="s">
            "${r.username}"<c:if test="${!s.last}">,</c:if>
        </c:forEach>
        ],
        datasets: [{
            label: 'Books Borrowed',
            data: [
            <c:forEach var="r" items="${topBorrowers}" varStatus="s">
                ${r.totalBorrowed}<c:if test="${!s.last}">,</c:if>
            </c:forEach>
            ],
            backgroundColor: '#4BC0C0'
        }]
    },
    options: { responsive: false }
});
</c:if>
</script>

<script>
/* ========== BOOKS BY CATEGORY ========== */
<c:if test="${not empty booksByCategory}">
new Chart(document.getElementById("booksByCategoryChart"), {
    type: 'pie',
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
            backgroundColor: ['#36A2EB','#FF6384','#FF9F40','#FFCD56','#4BC0C0']
        }]
    },
    options: { responsive: false }
});
</c:if>
</script>

<script>
/* ========== CURRENTLY ISSUED BOOKS ========== */
<c:if test="${not empty issuedBooks}">
new Chart(document.getElementById("issuedBooksChart"), {
    type: 'bar',
    data: {
        labels: [
        <c:forEach var="r" items="${issuedBooks}" varStatus="s">
            "${r.bookTitle}"<c:if test="${!s.last}">,</c:if>
        </c:forEach>
        ],
        datasets: [{
            label: 'Books Currently Issued',
            data: [
            <c:forEach var="r" items="${issuedBooks}" varStatus="s">
                1<c:if test="${!s.last}">,</c:if>
            </c:forEach>
            ],
            backgroundColor: '#FF9F40'
        }]
    },
    options: {
        responsive: false,
        scales: {
            y: {
                beginAtZero: true,
                ticks: { stepSize: 1 }
            }
        }
    }
});
</c:if>
</script>


<script>
/* ========== MONTHLY BORROWING ========== */
<c:if test="${not empty monthlyTrends}">
new Chart(document.getElementById("monthlyTrendChart"), {
    type: 'line',
    data: {
        labels: [
        <c:forEach var="r" items="${monthlyTrends}" varStatus="s">
            "${r.year}-${r.month}"<c:if test="${!s.last}">,</c:if>
        </c:forEach>
        ],
        datasets: [{
            label: 'Books Borrowed',
            data: [
            <c:forEach var="r" items="${monthlyTrends}" varStatus="s">
                ${r.totalBorrowed}<c:if test="${!s.last}">,</c:if>
            </c:forEach>
            ],
            borderColor: '#9966FF',
            fill: false
        }]
    },
    options: { responsive: false }
});
</c:if>
</script>

</body>
</html>
