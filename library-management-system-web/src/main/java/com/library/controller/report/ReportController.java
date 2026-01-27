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
@WebServlet("/reports/books")
public class ReportController extends HttpServlet {

    private final ReportService reportService = new ReportService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null)
                ? (User) session.getAttribute("user")
                : null;

        // Security check
        if (user == null ||
           (!"ADMIN".equals(user.getRole()) && !"LIBRARIAN".equals(user.getRole()))) {

            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        List<BookBorrowReport> reports =
                reportService.getMostBorrowedBooks();

        request.setAttribute("reports", reports);

        request.getRequestDispatcher(
                "/WEB-INF/views/report/most-borrowed-books.jsp")
               .forward(request, response);
    }
}
