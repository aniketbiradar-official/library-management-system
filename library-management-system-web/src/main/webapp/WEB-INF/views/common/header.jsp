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
        |
        <strong>Reports:</strong>

        <a href="${pageContext.request.contextPath}/reports/dashboard">
            Dashboard
        </a>
        |

        <a href="${pageContext.request.contextPath}/reports/books">
            Most Borrowed
        </a>
        |

        <a href="${pageContext.request.contextPath}/reports/issued">
            Issued Books
        </a>
        |

        <a href="${pageContext.request.contextPath}/reports/overdue">
            Overdue
        </a>
        |

        <a href="${pageContext.request.contextPath}/reports/members">
            Member Activity
        </a>
        |

        <a href="${pageContext.request.contextPath}/reports/monthly">
            Monthly Trends
        </a>
        |

        <a href="${pageContext.request.contextPath}/reports/categories">
            Books by Category
        </a>
    </c:if>

    <!-- ================= LOGOUT ================= -->
    | <a href="${pageContext.request.contextPath}/logout">Logout</a>

</nav>

<hr/>
