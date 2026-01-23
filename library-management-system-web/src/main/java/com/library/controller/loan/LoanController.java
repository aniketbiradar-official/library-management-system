package com.library.controller.loan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

import com.library.dao.loan.LoanDAO;
import com.library.model.loan.Loan;
import com.library.model.user.User;
import com.library.service.loan.LoanService;

@WebServlet("/loans/*")
public class LoanController extends HttpServlet {

    private final LoanService loanService = new LoanService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        String path = req.getPathInfo();

        if ("/borrow".equals(path) && "MEMBER".equals(user.getRole())) {
            int bookId = Integer.parseInt(req.getParameter("bookId"));
            loanService.issueBook(bookId, user.getId());
        }

        if ("/issue".equals(path) && "LIBRARIAN".equals(user.getRole())) {
            int bookId = Integer.parseInt(req.getParameter("bookId"));
            int memberId = Integer.parseInt(req.getParameter("userId"));
            loanService.issueBook(bookId, memberId);
        }

        if ("/return".equals(path) && "LIBRARIAN".equals(user.getRole())) {
            int loanId = Integer.parseInt(req.getParameter("loanId"));
            loanService.returnBook(loanId);
        }

        resp.sendRedirect(req.getContextPath() + "/books");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        if ("MEMBER".equals(user.getRole())) {
            req.setAttribute("loans", loanService.getLoansByUser(user.getId()));
        } else {
            req.setAttribute("loans", loanService.getAllActiveLoans());
        }

        req.getRequestDispatcher("/WEB-INF/views/loan/loan-list.jsp")
           .forward(req, resp);
    }
}
