<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav style="margin-bottom:15px;">
    
    <!-- ================= COMMON ================= -->
    <a href="${pageContext.request.contextPath}/books">Books</a>

    <!-- ================= MEMBER ================= -->
    <c:if test="${sessionScope.user.role != 'ADMIN'}">
        | <a href="${pageContext.request.contextPath}/loans">My Loans</a>
    </c:if>

    <!-- ================= ADMIN ================= -->
    <c:if test="${sessionScope.user.role == 'ADMIN'}">
        | <span>Admin Panel</span>
    </c:if>

    <!-- ================= REPORTS (ADMIN + LIBRARIAN) ================= -->
    <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'LIBRARIAN'}">
        | <span>Reports:</span>
        <a href="${pageContext.request.contextPath}/reports/books">
            Most Borrowed Books
        </a>
        |
        <a href="${pageContext.request.contextPath}/reports/issued">
            Currently Issued Books
        </a>
    </c:if>
    
    <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'LIBRARIAN'}">
	    | <a href="${pageContext.request.contextPath}/reports/overdue">
	        Overdue Books
	      </a>
	</c:if>
	
	<c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'LIBRARIAN'}">
	    | <a href="${pageContext.request.contextPath}/reports/members">
	        Member Activity
	      </a>
	</c:if>
	
    <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'LIBRARIAN'}">
	    | <a href="${pageContext.request.contextPath}/reports/monthly">
	        Monthly Borrowing Trends
	      </a>
	</c:if>
    

    <!-- ================= LOGOUT ================= -->
    | <a href="${pageContext.request.contextPath}/logout">Logout</a>

</nav>

<hr/>
