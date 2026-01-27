package com.library.dao.report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.library.model.report.BookBorrowReport;
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
}
