package com.library.dao.loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.library.model.loan.Loan;
import com.library.util.DBConnectionUtil;

public class LoanDAO {

    public void issueBook(int bookId, String borrowerName) {
        String loanSql =
            "INSERT INTO book_loans (book_id, borrower_name) VALUES (?, ?)";

        String updateBookSql =
            "UPDATE books SET available_copies = available_copies - 1 WHERE id = ? AND available_copies > 0";

        try (Connection conn = DBConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(updateBookSql);
                 PreparedStatement ps2 = conn.prepareStatement(loanSql)) {

                ps1.setInt(1, bookId);
                int updated = ps1.executeUpdate();

                if (updated == 0) {
                    throw new RuntimeException("No copies available to issue");
                }

                ps2.setInt(1, bookId);
                ps2.setString(2, borrowerName);
                ps2.executeUpdate();

                conn.commit();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error issuing book", e);
        }
    }

    public void returnBook(int loanId, int bookId) {
        String updateLoan =
            "UPDATE book_loans SET status='RETURNED', return_date=NOW() WHERE id=? AND status='ISSUED'";

        String updateBook =
            "UPDATE books SET available_copies = available_copies + 1 WHERE id=?";

        try (Connection conn = DBConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(updateLoan);
                 PreparedStatement ps2 = conn.prepareStatement(updateBook)) {

                ps1.setInt(1, loanId);
                int updated = ps1.executeUpdate();

                if (updated == 0) {
                    throw new RuntimeException("Loan already returned or invalid");
                }

                ps2.setInt(1, bookId);
                ps2.executeUpdate();

                conn.commit();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error returning book", e);
        }
    }
    
    public List<Loan> getActiveLoans() {
        List<Loan> loans = new ArrayList<>();

        String sql = "SELECT l.id, l.book_id, b.title, l.borrower_name, l.issue_date " +
                     "FROM book_loans l " +
                     "JOIN books b ON l.book_id = b.id " +
                     "WHERE l.status = 'ISSUED'";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Loan loan = new Loan();
                loan.setId(rs.getInt("id"));
                loan.setBookId(rs.getInt("book_id"));
                loan.setBookTitle(rs.getString("title"));
                loan.setBorrowerName(rs.getString("borrower_name"));
                loan.setIssueDate(rs.getTimestamp("issue_date").toLocalDateTime());

                loans.add(loan);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching active loans", e);
        }

        return loans;
    }

}
