package com.library.dao.report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.library.model.report.BookBorrowReport;
import com.library.model.report.IssuedBookReport;
import com.library.util.DBConnectionUtil;

/**
 * DAO for reporting queries (READ-ONLY)
 */
public class ReportDAO {

    /**
     * Fetches most borrowed books (top 10)
     */
    public List<BookBorrowReport> getMostBorrowedBooks() {

        String sql = """
            SELECT b.title, COUNT(bl.id) AS borrow_count
            FROM book_loans bl
            JOIN books b ON bl.book_id = b.id
            GROUP BY b.id, b.title
            ORDER BY borrow_count DESC
            LIMIT 10
        """;

        List<BookBorrowReport> reports = new ArrayList<>();

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BookBorrowReport report = new BookBorrowReport();
                report.setBookTitle(rs.getString("title"));
                report.setBorrowCount(rs.getInt("borrow_count"));
                reports.add(report);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching most borrowed books report", e);
        }

        return reports;
    }
    
    /**
     * Fetches all currently issued books
     */
    public List<IssuedBookReport> getCurrentlyIssuedBooks() {

        String sql = """
            SELECT
                b.title AS book_title,
                u.username,
                l.issue_date
            FROM book_loans l
            JOIN books b ON l.book_id = b.id
            JOIN users u ON l.user_id = u.id
            WHERE l.return_date IS NULL
            ORDER BY l.issue_date DESC
        """;

        List<IssuedBookReport> reports = new ArrayList<>();

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                IssuedBookReport r = new IssuedBookReport();
                r.setBookTitle(rs.getString("book_title"));
                r.setUsername(rs.getString("username"));
                r.setIssueDate(rs.getDate("issue_date"));
                reports.add(r);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching issued books report", e);
        }

        return reports;
    }


}
