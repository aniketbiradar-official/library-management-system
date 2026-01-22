package com.library.dao.loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.library.model.loan.BookLoan;
import com.library.util.DBConnectionUtil;


public class LoanDAO {
	

    // ================= ISSUE BOOK =================
    public void issueBook(int bookId, int userId) {

        String loanSql = """
            INSERT INTO book_loans (book_id, user_id, status)
            VALUES (?, ?, 'ISSUED')
        """;

        String updateBookSql = """
            UPDATE books
            SET available_copies = available_copies - 1
            WHERE id = ? AND available_copies > 0
        """;

        try (Connection conn = DBConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(updateBookSql)) {
                ps1.setInt(1, bookId);
                int updated = ps1.executeUpdate();

                if (updated == 0) {
                    throw new RuntimeException("No available copies");
                }
            }

            try (PreparedStatement ps2 = conn.prepareStatement(loanSql)) {
                ps2.setInt(1, bookId);
                ps2.setInt(2, userId);
                ps2.executeUpdate();
            }

            conn.commit();

        } catch (Exception e) {
            throw new RuntimeException("Error issuing book", e);
        }
    }

    // ================= RETURN BOOK =================
    public void returnBook(int loanId) {

        String returnSql = """
            UPDATE book_loans
            SET status='RETURNED', return_date=NOW()
            WHERE id=? AND status='ISSUED'
        """;

        String updateBookSql = """
            UPDATE books
            SET available_copies = available_copies + 1
            WHERE id = (SELECT book_id FROM book_loans WHERE id=?)
        """;

        try (Connection conn = DBConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(returnSql)) {
                ps1.setInt(1, loanId);
                ps1.executeUpdate();
            }

            try (PreparedStatement ps2 = conn.prepareStatement(updateBookSql)) {
                ps2.setInt(1, loanId);
                ps2.executeUpdate();
            }

            conn.commit();

        } catch (Exception e) {
            throw new RuntimeException("Error returning book", e);
        }
    }

    // ================= USER LOANS =================
    public List<BookLoan> getLoansByUser(int userId) {

        String sql = """
            SELECT bl.id, bl.book_id, bl.user_id,
                   bl.issue_date, bl.return_date, bl.status,
                   b.title AS book_title
            FROM book_loans bl
            JOIN books b ON bl.book_id = b.id
            WHERE bl.user_id = ?
        """;

        List<BookLoan> loans = new ArrayList<>();

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BookLoan loan = new BookLoan();

                loan.setId(rs.getInt("id"));
                loan.setBookId(rs.getInt("book_id"));
                loan.setUserId(rs.getInt("user_id"));
                loan.setBookTitle(rs.getString("book_title"));
                loan.setIssueDate(rs.getTimestamp("issue_date"));
                loan.setReturnDate(rs.getTimestamp("return_date"));
                loan.setStatus(rs.getString("status"));

                loans.add(loan);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching user loans", e);
        }

        return loans;
    }


    // ================= ALL ACTIVE LOANS =================
    public List<BookLoan> getAllActiveLoans() {

        String sql = """
            SELECT bl.id, bl.book_id, bl.user_id,
                   bl.issue_date, bl.return_date, bl.status,
                   b.title AS book_title,
                   u.username AS username
            FROM book_loans bl
            JOIN books b ON bl.book_id = b.id
            JOIN users u ON bl.user_id = u.id
            WHERE bl.status = 'ISSUED'
        """;

        List<BookLoan> loans = new ArrayList<>();

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BookLoan loan = new BookLoan();

                loan.setId(rs.getInt("id"));
                loan.setBookId(rs.getInt("book_id"));
                loan.setUserId(rs.getInt("user_id"));
                loan.setBookTitle(rs.getString("book_title"));
                loan.setUsername(rs.getString("username"));
                loan.setIssueDate(rs.getTimestamp("issue_date"));
                loan.setReturnDate(rs.getTimestamp("return_date"));
                loan.setStatus(rs.getString("status"));

                loans.add(loan);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching active loans", e);
        }

        return loans;
    }

}
