package com.library.dao.book;

import java.sql.*;
import java.util.*;

import com.library.model.Book;
import com.library.util.DBConnectionUtil;

public class BookDAO {

    // Whitelisted columns to prevent SQL injection
    private static final Set<String> ALLOWED_SORT_COLUMNS =
            Set.of("title", "author", "category");

    // ================= CREATE =================
    public void addBook(Book book) {

        String sql = """
            INSERT INTO books
            (title, author, isbn, total_copies, available_copies, category)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getIsbn());
            ps.setInt(4, book.getTotalCopies());
            ps.setInt(5, book.getAvailableCopies());
            ps.setString(6, book.getCategory());

            ps.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException("ISBN_ALREADY_EXISTS");
        } catch (SQLException e) {
            throw new RuntimeException("Error adding book", e);
        }
    }

    // ================= READ (SINGLE) =================
    public Book getBookById(int id) {

        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching book", e);
        }

        return null;
    }

    // ================= UPDATE =================
    public void updateBook(Book book) {

        String sql = """
            UPDATE books
            SET title=?, author=?, isbn=?, total_copies=?, available_copies=?, category=?
            WHERE id=?
        """;

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int issuedCopies = getIssuedCopies(book.getId(), conn);
            int newAvailable = book.getTotalCopies() - issuedCopies;

            if (newAvailable < 0) {
                throw new RuntimeException(
                    "Total copies cannot be less than issued copies"
                );
            }

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getIsbn());
            ps.setInt(4, book.getTotalCopies());
            ps.setInt(5, newAvailable);
            ps.setString(6, book.getCategory());
            ps.setInt(7, book.getId());

            ps.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException("ISBN_ALREADY_EXISTS");
        } catch (SQLException e) {
            throw new RuntimeException("Error updating book", e);
        }
    }

    // ================= DELETE =================
    public void deleteBook(int id) {

        String sql = "DELETE FROM books WHERE id=?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting book", e);
        }
    }

    // ================= SEARCH + SORT + PAGINATION =================
    public List<Book> searchBooks(
            String q,
            String category,
            String availability,
            String sort,
            String order,
            int limit,
            int offset) {

        if (!ALLOWED_SORT_COLUMNS.contains(sort)) sort = "title";
        order = "desc".equalsIgnoreCase(order) ? "desc" : "asc";

        StringBuilder sql = new StringBuilder(
                "SELECT * FROM books WHERE 1=1"
        );

        List<Object> params = new ArrayList<>();

        if (q != null && !q.isBlank()) {
            sql.append(" AND (title LIKE ? OR author LIKE ?)");
            params.add("%" + q + "%");
            params.add("%" + q + "%");
        }

        if (category != null && !category.isBlank()) {
            sql.append(" AND category = ?");
            params.add(category);
        }

        if ("available".equals(availability)) {
            sql.append(" AND available_copies > 0");
        } else if ("unavailable".equals(availability)) {
            sql.append(" AND available_copies = 0");
        }

        sql.append(" ORDER BY ").append(sort).append(" ").append(order);
        sql.append(" LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error searching books", e);
        }

        return books;
    }

    // ================= COUNT =================
    public int countBooks(String q, String category, String availability) {

        StringBuilder sql = new StringBuilder(
            "SELECT COUNT(*) FROM books WHERE 1=1"
        );

        List<Object> params = new ArrayList<>();

        if (q != null && !q.isBlank()) {
            sql.append(" AND (title LIKE ? OR author LIKE ?)");
            params.add("%" + q + "%");
            params.add("%" + q + "%");
        }

        if (category != null && !category.isBlank()) {
            sql.append(" AND category = ?");
            params.add(category);
        }

        if ("available".equals(availability)) {
            sql.append(" AND available_copies > 0");
        } else if ("unavailable".equals(availability)) {
            sql.append(" AND available_copies = 0");
        }

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException("Error counting books", e);
        }
    }

    // ================= HELPERS =================
    public List<String> getAllCategories() {

        List<String> categories = new ArrayList<>();

        String sql = "SELECT DISTINCT category FROM books WHERE category IS NOT NULL";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categories.add(rs.getString("category"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching categories", e);
        }

        return categories;
    }

    private int getIssuedCopies(int bookId, Connection conn) throws SQLException {

        String sql = "SELECT total_copies, available_copies FROM books WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total_copies") - rs.getInt("available_copies");
            }
        }
        return 0;
    }

    private Book mapRow(ResultSet rs) throws SQLException {

        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setIsbn(rs.getString("isbn"));
        book.setTotalCopies(rs.getInt("total_copies"));
        book.setAvailableCopies(rs.getInt("available_copies"));
        book.setCategory(rs.getString("category"));
        return book;
    }
}
