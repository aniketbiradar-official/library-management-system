package com.library.controller.loan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

import com.library.dao.loan.LoanDAO;
import com.library.model.loan.Loan;

@WebServlet({"/loans", "/loans/list"})
public class LoanController extends HttpServlet {

    private final LoanDAO loanDAO = new LoanDAO();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Loan> loans = loanDAO.getActiveLoans();
        req.setAttribute("loans", loans);
        req.getRequestDispatcher("/WEB-INF/views/loan/loan-list.jsp")
           .forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String action = req.getParameter("action");

        if ("issue".equals(action)) {
            int bookId = Integer.parseInt(req.getParameter("bookId"));
            String borrower = req.getParameter("borrowerName");
            loanDAO.issueBook(bookId, borrower);
        }

        if ("return".equals(action)) {
            int loanId = Integer.parseInt(req.getParameter("loanId"));
            int bookId = Integer.parseInt(req.getParameter("bookId"));
            loanDAO.returnBook(loanId, bookId);
        }

        resp.sendRedirect(req.getContextPath() + "/books");
    }
}
