package com.library.controller.report;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.library.model.report.BookBorrowReport;
import com.library.model.user.User;
import com.library.service.report.ReportService;

/**
 * Controller for Reports
 */
@WebServlet("/reports/*")
public class ReportController extends HttpServlet {

    private final ReportService reportService = new ReportService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null)
                ? (User) session.getAttribute("user")
                : null;

        // 🔐 Role-based security
        if (user == null ||
            (!"ADMIN".equals(user.getRole()) && !"LIBRARIAN".equals(user.getRole()))) {

            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // Path after /reports
        String path = request.getPathInfo();

        // ✅ Default safety
        if (path == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // ================= REPORT ROUTING =================

        if ("/books".equals(path)) {

            request.setAttribute(
                "reports",
                reportService.getMostBorrowedBooks()
            );

            request.getRequestDispatcher(
                "/WEB-INF/views/report/most-borrowed-books.jsp"
            ).forward(request, response);

        } else if ("/issued".equals(path)) {

            request.setAttribute(
                "reports",
                reportService.getCurrentlyIssuedBooks()
            );

            request.getRequestDispatcher(
                "/WEB-INF/views/report/currently-issued-books.jsp"
            ).forward(request, response);

        } else if ("/overdue".equals(path)) {

            request.setAttribute(
                "reports",
                reportService.getOverdueBooks()
            );

            request.getRequestDispatcher(
                "/WEB-INF/views/report/overdue-books.jsp"
            ).forward(request, response);
        }else if ("/members".equals(path)) {

            request.setAttribute(
                "reports",
                reportService.getMemberActivityReport()
            );

            request.getRequestDispatcher(
                "/WEB-INF/views/report/member-activity.jsp"
            ).forward(request, response);
        }

        
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
