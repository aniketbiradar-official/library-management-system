package com.library.service.loan;

import java.util.List;

import com.library.dao.loan.LoanDAO;
import com.library.model.loan.BookLoan;

public class LoanService {

    private final LoanDAO loanDAO = new LoanDAO();

    public void issueBook(int bookId, int userId) {
        loanDAO.issueBook(bookId, userId);
    }

    public void returnBook(int loanId) {
        loanDAO.returnBook(loanId);
    }

    public List<BookLoan> getLoansByUser(int userId) {
        return loanDAO.getLoansByUser(userId);
    }

    public List<BookLoan> getAllActiveLoans() {
        return loanDAO.getAllActiveLoans();
    }
}
