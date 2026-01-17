package com.library.dao.loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
