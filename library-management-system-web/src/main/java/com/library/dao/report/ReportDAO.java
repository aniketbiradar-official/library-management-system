package com.library.dao.report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.library.model.report.BookBorrowReport;
import com.library.model.report.IssuedBookReport;
import com.library.model.report.MemberActivityReport;
import com.library.model.report.MonthlyBorrowReport;
import com.library.model.report.OverdueBookReport;
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

    public List<OverdueBookReport> getOverdueBooks() {

        String sql = """
            SELECT
                b.title AS book_title,
                u.username,
                l.issue_date,
                DATEDIFF(CURDATE(), l.issue_date) - 14 AS overdue_days
            FROM book_loans l
            JOIN books b ON l.book_id = b.id
            JOIN users u ON l.user_id = u.id
            WHERE l.return_date IS NULL
              AND DATEDIFF(CURDATE(), l.issue_date) > 14
            ORDER BY overdue_days DESC
        """;

        List<OverdueBookReport> reports = new ArrayList<>();

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                OverdueBookReport r = new OverdueBookReport();
                r.setBookTitle(rs.getString("book_title"));
                r.setUsername(rs.getString("username"));
                r.setIssueDate(rs.getDate("issue_date"));
                r.setOverdueDays(rs.getLong("overdue_days"));
                reports.add(r);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching overdue books report", e);
        }

        return reports;
    }

    public List<MemberActivityReport> getMemberActivityReport() {

        String sql = """
            SELECT
                u.username,
                COUNT(l.id) AS total_borrowed
            FROM book_loans l
            JOIN users u ON l.user_id = u.id
            GROUP BY u.id, u.username
            ORDER BY total_borrowed DESC
        """;

        List<MemberActivityReport> reports = new ArrayList<>();

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MemberActivityReport r = new MemberActivityReport();
                r.setUsername(rs.getString("username"));
                r.setTotalBorrowed(rs.getLong("total_borrowed"));
                reports.add(r);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching member activity report", e);
        }

        return reports;
    }

    public List<MonthlyBorrowReport> getMonthlyBorrowingTrends() {

        String sql = """
            SELECT
                YEAR(issue_date) AS year,
                MONTH(issue_date) AS month,
                COUNT(id) AS total_borrowed
            FROM book_loans
            GROUP BY YEAR(issue_date), MONTH(issue_date)
            ORDER BY year DESC, month DESC
        """;

        List<MonthlyBorrowReport> reports = new ArrayList<>();

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MonthlyBorrowReport r = new MonthlyBorrowReport();
                r.setYear(rs.getInt("year"));
                r.setMonth(rs.getInt("month"));
                r.setTotalBorrowed(rs.getLong("total_borrowed"));
                reports.add(r);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching monthly borrowing trends", e);
        }

        return reports;
    }


}
